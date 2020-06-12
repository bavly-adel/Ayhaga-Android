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

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;


    Integer breakfastHour;
    Integer breakfastMinute;
    Integer launchHour;
    Integer launchMinute;
    Integer dinnerHour;
    Integer dinnerMinute;

    TextView breakfastText;
    TextView launchText;
    TextView dinnerText;
    Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        createNotificationChannelBreakfast();
        createNotificationChannelDinner();
        createNotificationChannelLaunch();

        breakfastText = (TextView) findViewById(R.id.breakfastText);
        launchText = (TextView) findViewById(R.id.launchText);
        dinnerText = (TextView) findViewById(R.id.dinnerText);

        Calendar calender = Calendar.getInstance();

        final int hour = calender.get(Calendar.HOUR_OF_DAY);
        final int minute = calender.get(Calendar.MINUTE);

        Button breakfastBtn = (Button) findViewById(R.id.breakfastBtn);
        Button launchBtn = (Button) findViewById(R.id.launchBtn);
        Button dinnerBtn = (Button) findViewById(R.id.dinnerBtn);

        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        breakfastHour = hourOfDay;
//                        breakfastMinute = minute;

                        if(hourOfDay > 12 ){
                            breakfastText.setText((hourOfDay-12) + " : " + minute + " PM");

                        }else if(hourOfDay == 0){
                            breakfastText.setText((12) + " : " + minute + " AM");

                        }else if(hourOfDay < 12) {
                            breakfastText.setText(hourOfDay + " : " + minute + " AM");
                        }else {
                            breakfastText.setText((hourOfDay) + " : " + minute + " PM");
                        }

                        setNotificationBreakfast(hourOfDay,minute);

                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });


        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        breakfastHour = hourOfDay;
//                        breakfastMinute = minute;

                        if(hourOfDay > 12 ){
                            launchText.setText((hourOfDay-12) + " : " + minute + " PM");

                        }else if(hourOfDay == 0){
                            launchText.setText((12) + " : " + minute + " AM");

                        }else if(hourOfDay < 12) {
                            launchText.setText(hourOfDay + " : " + minute + " AM");
                        }else {
                            launchText.setText((hourOfDay) + " : " + minute + " PM");

                        }

                        setNotificationLaunch(hourOfDay,minute);

                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });


        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        breakfastHour = hourOfDay;
//                        breakfastMinute = minute;

                        if(hourOfDay > 12 ){
                            dinnerText.setText((hourOfDay-12) + " : " + minute + " PM");

                        }else if(hourOfDay == 0){
                            dinnerText.setText((12) + " : " + minute + " AM");

                        }else if(hourOfDay < 12) {
                            dinnerText.setText(hourOfDay + " : " + minute + " AM");
                        }else {
                            dinnerText.setText((hourOfDay) + " : " + minute + " PM");

                        }

                        setNotificationDinner(hourOfDay,minute);

                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });


    }


    private void setNotificationBreakfast(int hour,int minute){
        //Toast.makeText(ReminderActivity.this,"reminder",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ReminderActivity.this, NotificationBreakfast.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(ReminderActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService((ALARM_SERVICE));

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pendingIntent);
    }

    private void setNotificationLaunch(int hour,int minute){
        //Toast.makeText(ReminderActivity.this,"reminder",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ReminderActivity.this, NotificationLaunch.class);
        //intent.putExtra("cat_id","2");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(ReminderActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService((ALARM_SERVICE));

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pendingIntent);
    }

    private void setNotificationDinner(int hour,int minute){
        //Toast.makeText(ReminderActivity.this,"reminder",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ReminderActivity.this, NotificationDinner.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(ReminderActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService((ALARM_SERVICE));

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pendingIntent);
    }


    private void createNotificationChannelBreakfast(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "Breakfast";
            String description = "Reminder Channel Breakfast";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyBreakfast",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    private void createNotificationChannelLaunch(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "Launch";
            String description = "Reminder Channel Launch";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLaunch",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    private void createNotificationChannelDinner(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "Dinner";
            String description = "Reminder Channel Dinner";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyDinner",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }



}
