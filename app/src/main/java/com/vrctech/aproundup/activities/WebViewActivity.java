package com.vrctech.aproundup.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.NotifyHelper;
import com.vrctech.aproundup.R;

import java.util.Objects;

public class WebViewActivity extends BaseActivity {

    public static String TITLE = "TITLE";
    public static String URL ="URL";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        setTitle(intent.getIntExtra(TITLE, R.string.app_name));

        init();
        GlobalMethods.setLocale(GlobalMethods.getPreferredLanguage(this), this, getBaseContext());

        webView.loadUrl(intent.getStringExtra(URL));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                NotifyHelper.showLoading(WebViewActivity.this);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                NotifyHelper.hideLoading();
                super.onPageFinished(view, url);
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
    }

    public void init(){
        webView = findViewById(R.id.webView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
