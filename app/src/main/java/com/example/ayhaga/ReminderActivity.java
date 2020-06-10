package com.example.ayhaga;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;


    Integer breakfastHour;
    Integer breakfastMinute;
    TextView timetTxt ;
    Button timeBtn;
    Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        createNotificationChannel();

        timetTxt = (TextView) findViewById(R.id.breakfastText);
        Calendar calender = Calendar.getInstance();

        final int hour = calender.get(Calendar.HOUR_OF_DAY);
        final int minute = calender.get(Calendar.MINUTE);

        Button button = (Button) findViewById(R.id.breakfastBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(ReminderActivity.this, RegisterActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//
//                NotificationCompat.Builder builder= new NotificationCompat.Builder(ReminderActivity.this,"channel_id");
//                builder.setContentTitle("title");
//                builder.setSmallIcon(R.drawable.ic_launcher_background);
//                builder.setContentText("content");
//                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                builder.setAutoCancel(true);
//                builder.setContentIntent(pendingIntent);
//
//
//                NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(ReminderActivity.this);
//                notificationManagerCompat.notify(001,builder.build());
//
//                AlarmManager alarmManager = (AlarmManager) getSystemService((ALARM_SERVICE));
//
//                Calendar cur_cal = new GregorianCalendar();
//                cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar
//
//                Calendar cal = new GregorianCalendar();
//                cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
//                cal.set(Calendar.HOUR_OF_DAY, 21);
//                cal.set(Calendar.MINUTE, 2);
//                cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
//                cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
//                cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
//                cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
                //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000, 2*60*1000, pendingIntent);

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        breakfastHour = hourOfDay;
                        breakfastMinute = minute;

                        if(hourOfDay > 12 ){
                            timetTxt.setText((hourOfDay-12) + " : " + minute + " PM");

                        }else if(hourOfDay == 0){
                            timetTxt.setText((12) + " : " + minute + " AM");

                        }else if(hourOfDay < 12) {
                            timetTxt.setText(hourOfDay + " : " + minute + " AM");
                        }else {
                            timetTxt.setText((hourOfDay) + " : " + minute + " PM");

                        }

                        Toast.makeText(ReminderActivity.this,"reminder",Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(ReminderActivity.this, MainActivity.class);
                        // Create an Intent for the activity you want to start
                        //Intent resultIntent = new Intent(ReminderActivity.this, MainActivity.class);
                        // Create the TaskStackBuilder and add the intent, which inflates the back stack
//                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ReminderActivity.this);
//                        stackBuilder.addNextIntentWithParentStack(resultIntent);
                        //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,0);
                        Intent intent = new Intent(ReminderActivity.this, NotificationBreakfast.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);

                        PendingIntent pendingIntent =
                                PendingIntent.getBroadcast(ReminderActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        //PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService((ALARM_SERVICE));

                        Calendar cur_cal = new GregorianCalendar();
                        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

                        Calendar cal = new GregorianCalendar();
                        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
                        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
                        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
                        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pendingIntent);
//                        long timeAtButtonClick = System.currentTimeMillis();
//
//                        long tenSecondsInMills = 1000*10 ;
//
//                        alarmManager.set(alarmManager.RTC_WAKEUP,timeAtButtonClick+tenSecondsInMills,pendingIntent);
//                        alarmManager.setRepeating();
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });


    }

        private void createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                CharSequence name = "ReminderChannel";
                String description = "a notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                //NotificationChannel channel = new NotificationChannel("notify",name,importance);
                NotificationChannel channel = new NotificationChannel("notify",name,importance);
                channel.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                //NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);


            }
        }

//    private void scheduleNotification (Notification notification , long delay) {
//        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
//        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
//        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
//        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
//        assert alarmManager != null;
//        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
//    }
//    private Notification getNotification (String content) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
//        builder.setContentTitle( "Scheduled Notification" ) ;
//        builder.setContentText(content) ;
//        builder.setSmallIcon(R.drawable. logo ) ;
//        builder.setAutoCancel( true ) ;
//        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//        return builder.build() ;
//    } ;
//    public void setDate (View view) {
//        new DatePickerDialog(ReminderActivity. this, date ,
//                myCalendar .get(Calendar. YEAR ) ,
//                myCalendar .get(Calendar. MONTH ) ,
//                myCalendar .get(Calendar. DAY_OF_MONTH )
//        ).show() ;
//    }
//    private void updateLabel () {
//        String myFormat = "dd/MM/yy" ; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
//        Date date = myCalendar .getTime() ;
//        btnDate .setText(sdf.format(date)) ;
//        scheduleNotification(getNotification( btnDate .getText().toString()) , date.getTime()) ;
//    }


}
