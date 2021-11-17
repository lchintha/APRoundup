package com.vrctech.aproundup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.vrctech.aproundup.activities.LaunchActivity;
import com.vrctech.aproundup.services.RestCallback;
import com.vrctech.aproundup.services.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class NotificationReceiver extends BroadcastReceiver {

    public static String REQUEST_CODE = "REQUEST_CODE";

    public static String CHANNEL_GENERAL = "CHANNEL_GENERAL";
    public static String CHANNEL_COVID = "CHANNEL_COVID";
    public static String CHANNEL_NEWS_PAPERS = "CHANNEL_NEWS_PAPERS";


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, LaunchActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int notificationCode = intent.getIntExtra(REQUEST_CODE, 0);

        Log.d("REQUEST_CODE", Integer.toString(notificationCode));

        if(notificationCode == 101) {
            notificationIntent.putExtra(LaunchActivity.PENDING_INTENT, LaunchActivity.COVID_CASES);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notifyCoronaVirusCases(context, intent, manager, pendingIntent);
        }else if(notificationCode == 102){
            notificationIntent.putExtra(LaunchActivity.PENDING_INTENT, LaunchActivity.NEWS_PAPERS);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 102, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            notifyNewsPapersAvailability(context, intent, manager, pendingIntent);
        }else{
            displayNotification(context, intent, manager);
        }

    }

    public void notifyCoronaVirusCases(Context context, Intent intent, NotificationManager manager, PendingIntent pendingIntent) {
        RestClient.getTotalCases(new RestCallback() {
            @Override
            public void result(JSONObject response) {
                StringBuilder stringBuilder = new StringBuilder();
                JSONArray stateWiseCases = JSH.getJSONArray(response, "statewise");
                int count = 0;
                for (int i = 0; i < stateWiseCases.length(); i++) {
                    JSONObject object = JSH.getJSONObject(stateWiseCases, i);
                    switch (JSH.getString(object, "statecode")) {
                        case "TT":
                            count++;
                            stringBuilder.append("India : ")
                                    .append(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")))
                                    .append("\n");
                            break;
                        case "AP":
                            count++;
                            stringBuilder.append("Andhra Pradesh : ")
                                    .append(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")))
                                    .append("\n");
                            break;
                        case "TG":
                            count++;
                            stringBuilder.append("Telangana : ")
                                    .append(GlobalMethods.getCommaSeparatedString(JSH.getString(object, "confirmed")))
                                    .append("\n");
                            break;
                    }
                    if(count == 3)
                        break;
                }

                Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ring_notif);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_COVID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_ap)
                        .setContentTitle("Total Covid-19 Cases as of now")
                        .setContentText(stringBuilder)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(stringBuilder))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setColor(context.getColor(R.color.colorPrimary))
                        .setSound(soundUri);

                //if(intent.getAction().equals("NOTIFICATION"))
                    manager.notify(101, builder.build());
            }
        });
    }

    public void notifyNewsPapersAvailability(Context context, Intent intent, NotificationManager manager, PendingIntent pendingIntent) {
        RestClient.getAvailableDateForEpaper(Globals.CODE_SAKSHI_HYD, new RestCallback() {
            @Override
            public void result(JSONObject response) {
                Iterator<String> keys = response.keys();
                String latestDate = keys.next();

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                Date date = new Date();
                System.out.println(dateFormat.format(date));

                if(!GlobalMethods.getFormattedDate(latestDate).equals(dateFormat.format(date))){
                    return;
                }
                System.out.println(dateFormat.format(date));

                Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.ring_notif);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_NEWS_PAPERS)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_ap)
                        .setContentTitle("News Papers")
                        .setContentText("Good morning! Today's news papers are available to read.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setStyle(new NotificationCompat.BigTextStyle()
                               .bigText("Good morning! Today's news papers are available to read."))
                        .setColor(context.getColor(R.color.colorPrimary))
                        .setAutoCancel(true)
                        .setSound(soundUri);

                //if(intent.getAction().equals("NOTIFICATION"))
                    manager.notify(102, builder.build());
            }
        });
    }

    public void displayNotification(Context context, Intent intent, NotificationManager manager){

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 103, new Intent(context, LaunchActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_GENERAL)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_ap)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setColor(context.getColor(R.color.colorPrimary))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true);

        manager.notify(103, mBuilder.build());
    }
}
