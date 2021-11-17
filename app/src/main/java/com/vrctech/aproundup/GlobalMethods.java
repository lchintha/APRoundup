package com.vrctech.aproundup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;

import com.vrctech.aproundup.activities.WebViewActivity;

import java.text.DecimalFormat;
import java.util.Locale;

public class GlobalMethods {

    private static String PREFERRED_LANGUAGE = "PREFERRED_LANGUAGE";
    private static String LANGUAGE = "LANGUAGE";

    public static String getPercentage(String total, String part){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(getIntegerFromString(part) * 100 / getIntegerFromString(total));
    }

    private static Float getIntegerFromString(String string){
        return Float.parseFloat(string.replaceAll("[^0-9]+", ""));
    }

    public static void savePreferredLanguage(Activity activity, String language){
        SharedPreferences sharedPref = activity.getSharedPreferences(PREFERRED_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LANGUAGE, language);
        editor.commit();
    }

    public static String getPreferredLanguage(Activity activity){
        SharedPreferences sharedPref = activity.getSharedPreferences(PREFERRED_LANGUAGE, Context.MODE_PRIVATE);
        return sharedPref.getString(LANGUAGE, "");
    }

    public static boolean isLanguageSelected(Activity activity){
        String result = getPreferredLanguage(activity);
        return !result.isEmpty();
    }

    public static String getCommaSeparatedString(String normalString){
        return normalString.replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void setLocale(String language, Context baseContext, Context appContext) {
        Resources activityRes = baseContext.getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(language);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = appContext.getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
    }

    public static String getFormattedDate(String date){
        if(date == null)
            return "";
        if(date.length() < 10)
            return "";
        return date.substring(8, 10) +
                "/" +
                date.subSequence(5, 7) +
                "/" +
                date.substring(0, 4);
    }

    public static String getPaperPassword(String key){
        int index = key.indexOf("-");
        return key.substring(0, index+2);
    }

    public static void startWebViewActivity(Context context, int title, String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WebViewActivity.TITLE, title);
        intent.putExtra(WebViewActivity.URL, url);
        context.startActivity(intent);
    }
}
