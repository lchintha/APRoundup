package com.vrctech.aproundup.activities.epaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.vrctech.aproundup.Globals;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class EpaperActivity extends AppCompatActivity implements PaperLoadImpl{

    static String PAPER_CODE = "PAPER_CODE";
    static String PAGES = "PAGES";
    static String ORIGINAL_DATE = "ORIGINAL_DATE";

    private String paperCode;
    private ArrayList<String> pageNumbers;
    private String originalDate;

    private int currentPaper = 0;

    private PDFView paper;
    private RelativeLayout progressLayout;
    private ProgressBar progress;
    private TextView percentage;
    private ImageView previous;
    private TextView pages;
    private ImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epaper);

        setStatusBarColor();
        init();

        Intent intent = getIntent();
        if(intent == null){
            NotifyHelper.toast(this, R.string.unexpected_error);
            return;
        }

        paperCode = intent.getStringExtra(PAPER_CODE);
        pageNumbers = (ArrayList<String>) intent.getSerializableExtra(PAGES);
        originalDate = intent.getStringExtra(ORIGINAL_DATE);

        setPageCount();
        showEPaper();
    }

    private void setStatusBarColor(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }

    private void init(){
        paper = findViewById(R.id.paper);
        progressLayout = findViewById(R.id.progressLayout);
        progress = findViewById(R.id.progress);
        percentage = findViewById(R.id.percentage);
        previous = findViewById(R.id.previous);
        pages = findViewById(R.id.pages);
        next = findViewById(R.id.next);
    }

    private void showEPaper(){
        if(getCurrentFilePath().exists()){
            Log.d("TAG", "DISPLAYING");
            displayPaper();
        }else {
            Log.d("TAG", "DOWNLOADING");
            downloadPaper();
        }
    }

    private void downloadPaper(){
        String key = pageNumbers.get(currentPaper);
        String url = String.format(Globals.EPAPER, originalDate, paperCode, key);
        Log.d("SERVICE_REQUEST", url);
        new PDFPaperDownloader(this).execute(url, getPageName());
    }

    private String getPageName(){
        return originalDate + "_" + paperCode + "_" + pageNumbers.get(currentPaper) + ".pdf";
    }

    @Override
    public void onPaperDownloadStart() {
        paper.setVisibility(View.GONE);
        progressLayout.setVisibility(View.VISIBLE);
        percentage.setText("0%");
        progress.setProgress(0);
    }

    @Override
    public void onPaperDownloadProgress(int percent) {
        progress.setProgress(percent);
        percentage.setText(String.format(Locale.ENGLISH, "%d%%", percent));
    }

    @Override
    public void onPaperDownloadFinish() {
        progressLayout.setVisibility(View.GONE);
        displayPaper();
    }

    private void displayPaper(){
        paper.setMidZoom(3.5f);
        paper.setMaxZoom(7.0f);
        paper.fromFile(getCurrentFilePath())
                .load();
        paper.setVisibility(View.VISIBLE);
    }

    private File getCurrentFilePath(){
        String file = getFilesDir().getAbsolutePath()
                .concat("/" + PDFPaperDownloader.EPAPER + "/")
                .concat(getPageName());
        Log.d("FILE_PATH", file);
        return new File(file);
    }

    private void setPageCount(){
        pages.setText(String.format(Locale.ENGLISH, "%d/%d", currentPaper + 1, pageNumbers.size()));
    }

    public void nextPaper(View view){
        if(progressLayout.getVisibility() != View.VISIBLE) {
            previous.setVisibility(View.VISIBLE);
            currentPaper += 1;
            if (currentPaper == pageNumbers.size() - 1) {
                view.setVisibility(View.GONE);
            }
            setPageCount();
            showEPaper();
        }
    }

    public void previousPaper(View view){
        if(progressLayout.getVisibility() != View.VISIBLE) {
            next.setVisibility(View.VISIBLE);
            currentPaper -= 1;
            if (currentPaper == 0) {
                view.setVisibility(View.GONE);
            }
            setPageCount();
            showEPaper();
        }
    }
}
