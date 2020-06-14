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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class ReminderActivity extends AppCompatActivity {

    private AdView mAdView;

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    final Calendar myCalendar = Calendar.getInstance();


    Integer breakfastHour;
    Integer breakfastMinute;
    Integer launchHour;
    Integer launchMinute;
    Integer dinnerHour;
    Integer dinnerMinute;

    TextView breakfastText;
    TextView launchText;
    TextView dinnerText;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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
        Button save = (Button) findViewById(R.id.saveBtn);
        Button test = (Button) findViewById(R.id.save2Btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotification();
            }
        });
        breakfastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTimeToTextView(breakfastText,hourOfDay,minute);
                        breakfastHour=hourOfDay;
                        breakfastMinute=minute;
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });

        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTimeToTextView(launchText,hourOfDay,minute);
                        launchHour=hourOfDay;
                        launchMinute=minute;
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });


        dinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       setTimeToTextView(dinnerText,hourOfDay,minute);
                        dinnerHour=hourOfDay;
                        dinnerMinute=minute;
                    }
                }, hour, minute, android.text.format.DateFormat.is24HourFormat(mContext));
                timePickerDialog.show();

            }
        });
    }


    private void setTimeToTextView(TextView textView, int hourOfDay, int minute) {
        if (hourOfDay > 12)
            textView.setText((hourOfDay - 12) + " : " + minute + " PM");
        else if (hourOfDay == 0)
            textView.setText((12) + " : " + minute + " AM");
        else if (hourOfDay < 12)
            textView.setText(hourOfDay + " : " + minute + " AM");
        else
            textView.setText((hourOfDay) + " : " + minute + " PM");
    }

    private void setNotificationForMeal(int hour, int minute, String catNumber, String title, String desc, String channelId) {
        Intent intent = new Intent(ReminderActivity.this, NotificationDinner.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("num", catNumber);
        intent.putExtra("title", title);
        intent.putExtra("desc", desc);
        intent.putExtra("channel_id", channelId);

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

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10*1000, pendingIntent);
       // alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 500, pendingIntent);
    }


    public void saveNotification()
    {
        setNotificationForMeal(breakfastHour, breakfastMinute, "1", "فطارك النهاردة", "شوف فطارك ايه !؟", "notifyBreakfast");
        Log.d("saveNotification", "saveNotification: breakfast ");
        sleep();
        setNotificationForMeal(launchHour, launchMinute, "2", "غداك النهاردة", "شوف غداك ايه !؟", "notifyLaunch");
        Log.d("saveNotification", "saveNotification: launch ");
        sleep();
        setNotificationForMeal(dinnerHour, dinnerMinute, "3", "عشاك النهاردة", "شوف هتتعشي ايه !؟", "notifyDinner");
        Log.d("saveNotification", "saveNotification: dinner ");
    }

    private void createNotificationChannelBreakfast() {
        createChannel("Breakfast", "Reminder Channel Breakfast", "notifyBreakfast");
    }

    private void createNotificationChannelLaunch() {
        createChannel("Launch", "Reminder Channel Launch", "notifyLaunch");
    }

    private void createNotificationChannelDinner() {
        createChannel("Dinner", "Reminder Channel Launch", "notifyDinner");
    }

    private void createChannel(String title, String desc, String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = title;
            String description = desc;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void sleep()
    {
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }*/
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void addTokenToSharedPreference (String token)
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("auth_token", token);
        editor.apply();   // instead of commit
        // Log.d("SP", pref.getString("auth_token", "NoToken") + "");
    }



}
