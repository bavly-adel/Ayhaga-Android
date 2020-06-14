package com.example.ayhaga;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {
    private AdView mAdView;


    Meal meal = new Meal();

    private int category_id;
    // Meal meal;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/meal/";
    private String url = "";

    //get the current intent


    TextView likesTxt;
    TextView nameTxt;
    ImageView mealImg;
    ProgressBar imgProgress;

    private InterstitialAd thisInterstitialAd;
    private InterstitialAd nextInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);


        thisInterstitialAd = new InterstitialAd(this);
        thisInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        nextInterstitialAd = new InterstitialAd(this);
        nextInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent intent = getIntent();
        String id = intent.getStringExtra("cat_id");
        url = JSON_URL + id;
        // String x = " % %  ^ ^ ^ $ $ $ % % % ^ ^ ^  ^ ^  %B%% $ $ ## %#$$% $# %#$ %# " + url;

        Toast.makeText(MealActivity.this, id + "", Toast.LENGTH_LONG).show();


        findViewById(R.id.elseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                extractRandomMeal();

            }
        });

        findViewById(R.id.mealLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextInterstitialAd.loadAd(new AdRequest.Builder().build());

                Intent i = new Intent(MealActivity.this, MealDetailsActivity.class);

                i.putExtra("meal", meal);


                if (nextInterstitialAd.isLoaded()) {
                    // Step 1: Display the interstitial
                    nextInterstitialAd.show();
                    // Step 2: Attach an AdListener
                    nextInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(i);
                            // Code to be executed when when the interstitial ad is closed.
                            Log.i("Ads", "onAdClosed");
                        }
                    });
                } else {

                    // save meal logic
                    saveMeal();
                    startActivity(i);
                }


            }
        });


        nameTxt = (TextView) findViewById(R.id.mealName);
        //likesTxt = (TextView) findViewById(R.id.likesTxt);
        mealImg = (ImageView) findViewById(R.id.mealImg);

        // logic retrieve saved meal
        getSavedMeal(id);
    }

    private void extractRandomMeal() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    thisInterstitialAd.loadAd(new AdRequest.Builder().build());


                    JSONObject mealObject = response.getJSONObject(0);
                    meal.setId(Integer.valueOf(mealObject.getString("id").toString()));
                    meal.setName(mealObject.getString("name").toString());
                    meal.setCategory_id(mealObject.getString("category_id").toString());

                    //meal.setDesc(mealObject.getString("description").toString());
                    meal.setIngrediants(mealObject.getString("ingredients").toString());
                    meal.setPreparation(mealObject.getString("preparation").toString());

                    System.out.println(fullurl(mealObject.getString("main_photo")));

                    meal.setImgurl(fullurl(mealObject.getString("main_photo").toString()));
                    meal.setLikes(Integer.parseInt(mealObject.getString("likes")));


                    meal.setPhotos(Arrays.asList(mealObject.getString("photos").toString().replace("\"", "").replace("\\", "").replace("[", "").replace("]", "").split(",")));
                    //System.out.println(mealObject.getString("photos").toString()+mealObject.getString("name")+mealObject.getString("photos"));

                    imgProgress = findViewById(R.id.imgProgress);
                    imgProgress.setVisibility(View.VISIBLE);

                    Picasso.get().load(fullurl(mealObject.getString("main_photo"))).into(mealImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            imgProgress.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


                } catch (JSONException e) {
                    System.out.println("Errooooooooooor ");
                    e.printStackTrace();
                }

                nameTxt.setText(meal.getName());
                //likesTxt.setText(" " + meal.getLikes());

                //Picasso.get().load(fullurl(meal.getImgurl())).into(mealImg);

                if (thisInterstitialAd.isLoaded()) {
                    thisInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

    private String fullurl(String url) {

        String newurl = "https://dashboard.ayhaga.app/storage/" + url;

        return newurl;

    }

    private void extractSpecificMeal(Integer mealId) {
        String requestUrl = "https://dashboard.ayhaga.app/api/mealid/" + mealId;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    thisInterstitialAd.loadAd(new AdRequest.Builder().build());


                    JSONObject mealObject = response.getJSONObject(0);
                    meal.setId(mealId);
                    meal.setName(mealObject.getString("name").toString());
                    meal.setCategory_id(mealObject.getString("category_id").toString());

                    //meal.setDesc(mealObject.getString("description").toString());
                    meal.setIngrediants(mealObject.getString("ingredients").toString());
                    meal.setPreparation(mealObject.getString("preparation").toString());

                    System.out.println(fullurl(mealObject.getString("main_photo")));

                    meal.setImgurl(fullurl(mealObject.getString("main_photo").toString()));
                    meal.setLikes(Integer.parseInt(mealObject.getString("likes")));


                    meal.setPhotos(Arrays.asList(mealObject.getString("photos").toString().replace("\"", "").replace("\\", "").replace("[", "").replace("]", "").split(",")));
                    //System.out.println(mealObject.getString("photos").toString()+mealObject.getString("name")+mealObject.getString("photos"));

                    imgProgress = findViewById(R.id.imgProgress);
                    imgProgress.setVisibility(View.VISIBLE);

                    Picasso.get().load(fullurl(mealObject.getString("main_photo"))).into(mealImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            imgProgress.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });


                } catch (JSONException e) {
                    System.out.println("Errooooooooooor ");
                    e.printStackTrace();
                }

                nameTxt.setText(meal.getName());
                //likesTxt.setText(" " + meal.getLikes());

                //Picasso.get().load(fullurl(meal.getImgurl())).into(mealImg);

                if (thisInterstitialAd.isLoaded()) {
                    thisInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }

    public void saveMeal() {
        Date today = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(today);
        Log.d("saveMeal", "formattedDate: " + formattedDate);

        SavedMeal savedMeal = new SavedMeal(meal.getId(), meal.getCategory_id(), meal.getName(), meal.getImgurl(), formattedDate);

        // convert object to json
        Gson gson = new Gson();
        String savedMealJsonString = gson.toJson(savedMeal);
        Log.d("saveMeal", "savedMealJsonString: " + savedMealJsonString);

        // save to shared preference
        saveToSharedPreference(meal.getCategory_id(), savedMealJsonString);
    }


    public void getSavedMeal(String categoryId) {
        Gson gson = new Gson();
        String data = getStringFromSharedPreference(categoryId);
        Log.d("saveMeal", "getSavedMeal: "+data);
        if (!data.equals("Not Exist")) {
            SavedMeal savedMeal = gson.fromJson(data, SavedMeal.class);
            Log.d("saveMeal", "getSavedMeal: "+savedMeal.id);

            if (savedMeal.valid()) {
                extractSpecificMeal(savedMeal.id);
            } else
                extractRandomMeal();

        }
        else
            extractRandomMeal();
    }

    public void saveToSharedPreference(String key, String value) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();   // instead of commit
        // Log.d("SP", pref.getString("auth_token", "NoToken") + "");
    }


    public String getStringFromSharedPreference(String key) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        return pref.getString(key, "Not Exist");
    }
}
