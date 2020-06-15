package com.example.ayhaga;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {
    private AdView mAdView;

    //private Button menuBtn;

//    private DrawerLayout dl;
//    private ActionBarDrawerToggle t;
//    private NavigationView nv;


// TODO: Add adView to your view hierarchy.

    RecyclerView recyclerView;
    List<Category> categories;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/categories";
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkNullSP();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = findViewById(R.id.categoryList);

        categories = new ArrayList<>();
        extractCategories();
        //addBasicCategories();


//        findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // open right drawer
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
//                drawer.openDrawer(GravityCompat.END);
//            }
//        });



//        findViewById(R.id.menuBtnClose).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // open right drawer
//                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
//                drawer.closeDrawer(GravityCompat.END);
//            }
//        });

//        findViewById(R.id.homeBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//
//
//        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });


                findViewById(R.id.reminderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });


    }

    private void extractCategories() {
        System.out.println("start 2 # # # # # #  # ### # # #  # # #  # # # # ## # # ### # #  #####  #");
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println(" } | }|} |}| | |} |} | }| }| | }|  *******");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
//                try {
//                    System.out.println(" } | }|} |}| | |} |} | }| }| | }| " + response.getJSONObject(0).getJSONObject().getJSONObject(0));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                for (int i = 0; i < response.length(); i++) {
                    try {

                        System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + i);
                        JSONObject catsObject = response.getJSONObject(i);

                        Category category = new Category();
                        category.setName(catsObject.getString("name").toString());
                        category.setId(Integer.parseInt(catsObject.getString("id").toString()));

                        //song.setArtists(songObject.getString("artists".toString()));
                        System.out.println(fullurl(catsObject.getString("photo")));
                        category.setImg_url(fullurl(catsObject.getString("photo").toString()));
                        //song.setSongURL(songObject.getString("url"));
                        categories.add(category);
                        System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + i +"   "+category.getName());


                    } catch (JSONException e) {
                        System.out.println("Errooooooooooor ");
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new RecyclerViewAdapter(getApplicationContext(),categories);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

    private String fullurl(String url){

        String newurl = "https://dashboard.ayhaga.app/storage/"+url;

        return newurl;

    }

    public void addBasicCategories()
    {
        Category breakfast=new Category("فطارنا",fullurl("categories/May2020/oO8cKcAdcPjrdTcCjSps.png"),1);
        Category launch=new Category("غدانا إيه",fullurl("categories/May2020/uBDmjOAWOBh81QUUw2wH.png"),2);
        Category dinner=new Category("العشا اللذيد",fullurl("categories/May2020/1Tk8hji2nG4srY61BoL5.png"),3);
        Category dessert=new Category("حلويات خفيفة",fullurl("categories/May2020/164XX8wg3kjZOkxkXjbP.png"),4);
        Category fruits=new Category("عالم الفواكة",fullurl("categories/May2020/OnLifBH5PI3xWgWHZj7Z.png"),5);

        categories.add(breakfast);
        categories.add(launch);
        categories.add(dinner);
        categories.add(dessert);
        categories.add(fruits);
    }

    public void checkNullSP() {
        SharedPreferences preferences = getSharedPreferences("MyPref", 0);
        int breakfast = preferences.getInt("breakfast_hour", 1000);
        int launch = preferences.getInt("launch_hour", 1000);
        int dinner = preferences.getInt("dinner_hour", 1000);
        if (breakfast == 1000 && launch == 1000 && dinner == 1000) {
            // the key does not exist

            saveToSP("breakfast_hour",8);
            saveToSP("breakfast_minute",0);
            saveToSP("launch_hour",17);
            saveToSP("launch_minute",35);
            saveToSP("dinner_hour",17);
            saveToSP("dinner_minute",37);

            saveToSP("default_alarm_bf",1);
            saveToSP("default_alarm_lun",1);
            saveToSP("default_alarm_din",1);

            createNotificationChannelBreakfast();
            createNotificationChannelDinner();
            createNotificationChannelLaunch();

        }

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

}
