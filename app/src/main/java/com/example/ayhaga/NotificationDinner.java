package com.example.ayhaga;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationDinner extends BroadcastReceiver {
//    public static String NOTIFICATION_ID = "notification-id" ;
//    public static String NOTIFICATION = "notification" ;
public void onReceive (Context context , Intent intent) {
    Intent i = new Intent(context,MealActivity.class);
    i.putExtra("cat_id","3");
    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

    PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);


    NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyDinner")
            .setSmallIcon(R.drawable.logo )
            .setContentTitle( "عشاك النهاردة" )
            .setContentText("شوف هتتعشي ايه !؟")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent);

    int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
    notificationManagerCompat.notify(m,builder.build());

}
}
