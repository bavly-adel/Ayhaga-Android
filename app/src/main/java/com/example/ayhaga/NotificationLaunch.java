package com.example.ayhaga;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationLaunch extends BroadcastReceiver {
//    public static String NOTIFICATION_ID = "notification-id" ;
//    public static String NOTIFICATION = "notification" ;
    public void onReceive (Context context , Intent intent) {

        System.out.println("Recieveee - - -- - - - - - - -  Launch");


        //String id = intent.getStringExtra("catId");

        Intent i = new Intent(context,MealActivity.class);
        i.putExtra("cat_id","2");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyLaunch")
                .setSmallIcon(R.drawable.logo )
                .setContentTitle( " غداك النهاردة" )
                .setContentText("شوف هتتغدي ايه !؟")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(201,builder.build());

    }
}
