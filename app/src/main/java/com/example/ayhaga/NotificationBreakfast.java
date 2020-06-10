package com.example.ayhaga;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBreakfast extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    public void onReceive (Context context , Intent intent) {
        Intent i = new Intent(context,ForgetPasswordActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notify")
                .setSmallIcon(R.drawable.logo )
                .setContentTitle( "فطارك النهاردة" )
                .setContentText("شوف هتفطر ايه !؟")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        //.addAction(R.drawable.ic_launcher_background,"شوف فطار النهاردة",openMeal);




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
