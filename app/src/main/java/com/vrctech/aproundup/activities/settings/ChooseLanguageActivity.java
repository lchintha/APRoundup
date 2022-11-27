package com.vrctech.aproundup.activities.settings;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vrctech.aproundup.GlobalMethods;
import com.vrctech.aproundup.R;
import com.vrctech.aproundup.activities.BaseActivity;
import com.vrctech.aproundup.activities.LaunchActivity;

import java.util.Locale;
import java.util.Objects;

public class ChooseLanguageActivity extends BaseActivity {

    RadioGroup languageGroup;
    RadioButton englishLanguage;
    RadioButton teluguLanguage;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        init();

        languageGroupSelectionListener();
        if(GlobalMethods.isLanguageSelected()) {
            prepopulateTheSelection();
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init(){
        languageGroup = findViewById(R.id.languageGroup);
        englishLanguage = findViewById(R.id.englishLanguage);
        teluguLanguage = findViewById(R.id.teluguLanguage);
        continueBtn = findViewById(R.id.continueBtn);
    }

    public void languageGroupSelectionListener(){
        languageGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            continueBtn.setEnabled(true);
            if(radioGroup.getCheckedRadioButtonId() == R.id.englishLanguage) {
                englishLanguage.setTextColor(getResources().getColor(R.color.white, null));
                teluguLanguage.setTextColor(getResources().getColor(R.color.black, null));
            }else if(radioGroup.getCheckedRadioButtonId() == R.id.teluguLanguage){
                englishLanguage.setTextColor(getResources().getColor(R.color.black, null));
                teluguLanguage.setTextColor(getResources().getColor(R.color.white, null));
            }
        });
    }


    public void goToLaunchScreen(View view) {
        goToLaunchScreen();
    }

    public void goToLaunchScreen(){
        finish();
        Intent intent = new Intent(this, LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void prepopulateTheSelection(){
        if(GlobalMethods.getPreferredLanguage().equals(getResources().getString(R.string.lang_english))){
            englishLanguage.setChecked(true);
        }else{
            teluguLanguage.setChecked(true);
        }
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
