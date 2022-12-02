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

    private int currentPage = 0;

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
        showPaper();
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

    private void showPaper(){
        if(getPagePath(currentPage).exists()){
            Log.d("TAG", "DISPLAYING");
            displayPage();
            downloadNextPage();
        }else {
            Log.d("TAG", "DOWNLOADING");
            downloadPage(currentPage);
        }
    }

    private void downloadPage(int pageNumber){
        String key = pageNumbers.get(pageNumber);
        String url = String.format(Globals.EPAPER, originalDate, paperCode, key);
        Log.d("SERVICE_REQUEST", url);
        new PDFPaperDownloader(this, pageNumber).execute(url, getPageName(pageNumber));
    }

    private void downloadNextPage(){
        if(currentPage + 1 < pageNumbers.size()) {
            if(!getPagePath(currentPage + 1).exists()){
                downloadPage(currentPage + 1);
            }
        }
    }

    private String getPageName(int pageNumber){
        return originalDate + "_" + paperCode + "_" + pageNumbers.get(pageNumber) + ".pdf";
    }

    @Override
    public void onPageDownloadStart(int pageNumber) {
        if(pageNumber == currentPage) {
            paper.setVisibility(View.GONE);
            progressLayout.setVisibility(View.VISIBLE);
            percentage.setText("0%");
            progress.setProgress(0);
        }
    }

    @Override
    public void onPageDownloadProgress(int percent) {
        progress.setProgress(percent);
        percentage.setText(String.format(Locale.ENGLISH, "%d%%", percent));
    }

    @Override
    public void onPageDownloadFinish(int pageNumber) {
        if(pageNumber == currentPage) {
            progressLayout.setVisibility(View.GONE);
            showPaper();
        }
    }

    private void displayPage(){
        paper.setMidZoom(3.5f);
        paper.setMaxZoom(7.0f);
        paper.fromFile(getPagePath(currentPage))
                .load();
        paper.setVisibility(View.VISIBLE);
    }

    private File getPagePath(int pageNumber){
        String file = getFilesDir().getAbsolutePath()
                .concat("/" + PDFPaperDownloader.EPAPER + "/")
                .concat(getPageName(pageNumber));
        Log.d("FILE_PATH", file);
        return new File(file);
    }

    private void setPageCount(){
        pages.setText(String.format(Locale.ENGLISH, "%d/%d", currentPage + 1, pageNumbers.size()));
    }

    public void nextPaper(View view){
        if(progressLayout.getVisibility() != View.VISIBLE) {
            previous.setVisibility(View.VISIBLE);
            currentPage += 1;
            if (currentPage == pageNumbers.size() - 1) {
                view.setVisibility(View.GONE);
            }
            setPageCount();
            showPaper();
        }
    }

    public void previousPaper(View view){
        if(progressLayout.getVisibility() != View.VISIBLE) {
            next.setVisibility(View.VISIBLE);
            currentPage -= 1;
            if (currentPage == 0) {
                view.setVisibility(View.GONE);
            }
            setPageCount();
            showPaper();
        }
    }
}
