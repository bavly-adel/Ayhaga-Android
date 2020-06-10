package com.example.ayhaga;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationLaunch extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public void onReceive (Context context , Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notifyl")
                .setSmallIcon(R.drawable.logo )
                .setContentTitle( "غداك النهاردة" )
                .setContentText("شوف هتتغدي ايييه !؟")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());

//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
//        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
//        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//            int importance = NotificationManager. IMPORTANCE_HIGH ;
//            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(notificationChannel) ;
//        }
//        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
//        assert notificationManager != null;
//        notificationManager.notify(id , notification) ;
    }
}
