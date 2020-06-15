package com.example.ayhaga;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderActivity extends AppCompatActivity {

    private AdView mAdView;




    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;


    Integer breakfastHour;
    Integer breakfastMinute;
    Integer launchHour;
    Integer launchMinute;
    Integer dinnerHour;
    Integer dinnerMinute;

    Integer breakfastActive;
    Integer launchActive;
    Integer dinnerActive;

    TextView breakfastText;
    TextView launchText;
    TextView dinnerText;
    Context mContext=this;

    Switch brekfastSwitch;
    Switch launchSwitch;
    Switch dinnerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        //saveToSP("breakfast_hour",8);        saveToSP("breakfast_minute",40);

        //System.out.println(getIntFromSP("breakfast_hour")+"       "+getIntFromSP("breakfast_minute"));
        breakfastHour = getIntFromSP("breakfast_hour");
        breakfastMinute = getIntFromSP("breakfast_minute");
        launchHour = getIntFromSP("launch_hour");
        launchMinute = getIntFromSP("launch_minute");
        dinnerHour = getIntFromSP("dinner_hour");
        dinnerMinute = getIntFromSP("dinner_minute");

        breakfastActive = getIntFromSP("breakfastActive");
        launchActive = getIntFromSP("launchActive");
        dinnerActive = getIntFromSP("dinnerActive");




/*
        setTimeToText(launchText,getIntFromSP("launch_hour"),getIntFromSP("launch_minute"));
        setTimeToText(dinnerText,getIntFromSP("dinner_hour"),getIntFromSP("dinner_minute"));
*/

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        brekfastSwitch = (Switch) findViewById(R.id.breakfastSwitch);
        launchSwitch = (Switch) findViewById(R.id.launchSwitch);
        dinnerSwitch = (Switch) findViewById(R.id.dinnerSwitch);

        breakfastText = (TextView) findViewById(R.id.breakfastText);
        launchText = (TextView) findViewById(R.id.launchText);
        dinnerText = (TextView) findViewById(R.id.dinnerText);

        Calendar calender = Calendar.getInstance();

        final int hour = calender.get(Calendar.HOUR_OF_DAY);
        final int minute = calender.get(Calendar.MINUTE);

        Button breakfastBtn = (Button) findViewById(R.id.breakfastBtn);
        Button launchBtn = (Button) findViewById(R.id.launchBtn);
        Button dinnerBtn = (Button) findViewById(R.id.dinnerBtn);

        //Button saveBtn = (Button) findViewById(R.id.saveBtn);


        setTimeToText(breakfastText,breakfastHour,breakfastMinute);
        setTimeToText(launchText,launchHour,launchMinute);
        setTimeToText(dinnerText,dinnerHour,dinnerMinute);


        if(breakfastActive == 1)brekfastSwitch.setChecked(true);
        if(launchActive == 1)launchSwitch.setChecked(true);
        if(dinnerActive == 1)dinnerSwitch.setChecked(true);


        brekfastSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createNotificationChannelBreakfast();

                    saveToSP("breakfastActive",1);
                    setNotificationBreakfast(breakfastHour,breakfastMinute);


                } else {
                    saveToSP("breakfastActive",0);
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//                        deleteNotificationChannel(breakfastHour,breakfastMinute);
//                    }

                }
            }
        });

        launchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    createNotificationChannelLaunch();
                    saveToSP("launchActive",1);
                    setNotificationLaunch(launchHour,launchMinute);

                } else {
                    saveToSP("launchActive",0);
                }
            }
        });

        dinnerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createNotificationChannelDinner();

                    saveToSP("dinnerActive",1);
                    setNotificationDinner(dinnerHour,dinnerMinute);

                } else {
                    saveToSP("dinnerActive",0);
                }
            }
        });

        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        breakfastHour = hourOfDay;
//                        breakfastMinute = minute;

                        setTimeToText(breakfastText,hourOfDay,minute);

                        breakfastHour = hourOfDay;
                        breakfastMinute = minute;

                        saveToSP("breakfast_hour",breakfastHour);
                        saveToSP("breakfast_minute",breakfastMinute);

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

                        setTimeToText(launchText,hourOfDay,minute);

                        launchHour = hourOfDay;
                        launchMinute = minute;

                        saveToSP("launch_hour",launchHour);
                        saveToSP("launch_minute",launchMinute);

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

                        setTimeToText(dinnerText,hourOfDay,minute);


                        dinnerHour = hourOfDay;
                        dinnerMinute = minute;

                        saveToSP("dinner_hour",dinnerHour);
                        saveToSP("dinner_minute",dinnerMinute);

                        //setNotificationDinner(dinnerHour,minute);


                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });

//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                saveToSP("breakfast_hour",breakfastHour);
//                saveToSP("breakfast_minute",breakfastMinute);
//                saveToSP("launch_hour",launchHour);
//                saveToSP("launch_minute",launchMinute);
//                saveToSP("dinner_hour",dinnerHour);
//                saveToSP("dinner_minute",dinnerMinute);
//
//
//
//
//
//                finish();
//
//            }
//
//        });

    }

    private void setTimeToText(TextView tv,int hour,int minute) {
        if(hour > 12 ){
            tv.setText((hour-12) + " : " + minute + " PM");

        }else if(hour == 0){
            tv.setText((12) + " : " + minute + " AM");

        }else if(hour < 12) {
            tv.setText(hour + " : " + minute + " AM");
        }else {
            tv.setText((hour) + " : " + minute + " PM");

        }

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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60, pendingIntent);

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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60, pendingIntent);
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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60, pendingIntent);
    }


    private void createNotificationChannelBreakfast(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            CharSequence name = "Breakfast";
            String description = "Reminder Channel Breakfast";
            int importance = NotificationManager.IMPORTANCE_HIGH;
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
            int importance = NotificationManager.IMPORTANCE_HIGH;
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
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyDinner",name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }
    }

    public void saveToSP(String key, int value) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();   // instead of commit
        // Log.d("SP", pref.getString("auth_token", "NoToken") + "");
    }



    public int getIntFromSP(String key) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getInt(key, 0);
    }

    public void deleteNotificationChannel(int hour,int minute) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.deleteNotificationChannel("notifyBreakfast");


            Intent intent = new Intent(ReminderActivity.this, NotificationBreakfast.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //PendingIntent pendingIntent = PendingIntent.getBroadcast(ReminderActivity.this,0,intent,PendingIntent.FLAG_ONE_SHOT);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(ReminderActivity.this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//            manager.deleteNotificationChannel("notifyBreakfast");
//            manager.deleteNotificationChannel("notifyLaunch");

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

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60, pendingIntent);



        }
    }




}