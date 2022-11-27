package com.vrctech.aproundup;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.vrctech.aproundup.activities.WebViewActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GlobalMethods {

    public static String getPercentage(String total, String part){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(getIntegerFromString(part) * 100 / getIntegerFromString(total));
    }

    private static Float getIntegerFromString(String string){
        return Float.parseFloat(string.replaceAll("[^0-9]+", ""));
    }

    public static String getPreferredLanguage(){
        return "en";
    }

    public static boolean isLanguageSelected(){
        String result = getPreferredLanguage();
        return !result.isEmpty();
    }

    public static String getCommaSeparatedString(String normalString){
        return normalString.replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static String getFormattedDate(String date){
        try {
            DateFormat fromFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
            DateFormat toFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date toDate = fromFormat.parse(date);
            assert toDate != null;
            return toFormat.format(toDate);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return "";
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
