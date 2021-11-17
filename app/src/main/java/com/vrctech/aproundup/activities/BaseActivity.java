package com.vrctech.aproundup.activities;

import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import com.vrctech.aproundup.GlobalMethods;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        GlobalMethods.setLocale(GlobalMethods.getPreferredLanguage(this), this, getApplicationContext());
    }
}
