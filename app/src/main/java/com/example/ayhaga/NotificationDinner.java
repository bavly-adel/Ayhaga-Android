package com.example.ayhaga;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;
import java.util.Random;

public class NotificationDinner extends BroadcastReceiver {
//    public static String NOTIFICATION_ID = "notification-id" ;
//    public static String NOTIFICATION = "notification" ;
    public void onReceive (Context context , Intent intent) {

        Intent i = new Intent(context,MealActivity.class);
        String num=intent.getStringExtra("num");
        String title=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");
        String channelId=intent.getStringExtra("channel_id");

        Log.d("notExtra", "num: " + num);
        Log.d("notExtra", "title: " + title);
        Log.d("notExtra", "desc: " + desc);
        Log.d("notExtra", "channel: " + channelId);

        i.putExtra("cat_id",num);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.drawable.logo )
                .setContentTitle( title )
                .setContentText(desc)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        int randId= (int) (Math.random() * (5000 - 4 + 1) + 4);
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        Log.d("notExtra", "not id: " + m);
        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(m,builder.build());

    }
}
