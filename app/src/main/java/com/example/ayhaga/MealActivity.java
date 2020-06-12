package com.example.ayhaga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {
    Meal meal = new Meal();

    private int category_id;
    // Meal meal;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/meal/";
    private String url = "";

    //get the current intent


    TextView likesTxt;
    TextView nameTxt;
    ImageView mealImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        //get the attached extras from the intent
//we should use the same key as we used to attach the data.
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

        findViewById(R.id.detailsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                Intent i = new Intent(MealActivity.this, MealDetailsActivity.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //int id = (int) name.getTag(Integer.parseInt("id"));
                //int id = 2;
                i.putExtra("meal", meal);
                startActivity(i);

            }
        });


        nameTxt = (TextView) findViewById(R.id.mealName);
        likesTxt = (TextView) findViewById(R.id.likesTxt);
        mealImg = (ImageView) findViewById(R.id.mealImg);

        extractRandomMeal();
    }

    private void extractRandomMeal() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject mealObject = response.getJSONObject(0);
                    meal.setName(mealObject.getString("name").toString());
                    meal.setCategory_id(mealObject.getString("category_id").toString());

                    //meal.setDesc(mealObject.getString("description").toString());
                    meal.setIngrediants(mealObject.getString("ingredients").toString());
                    meal.setPreparation(mealObject.getString("preparation").toString());

                    System.out.println(fullurl(mealObject.getString("main_photo")));
                    Picasso.get().load(fullurl(mealObject.getString("main_photo"))).into(mealImg);

                    meal.setImgurl(fullurl(mealObject.getString("main_photo").toString()));
                    meal.setLikes(Integer.parseInt(mealObject.getString("likes")));


                } catch (JSONException e) {
                    System.out.println("Errooooooooooor ");
                    e.printStackTrace();
                }

                nameTxt.setText(meal.getName());
                likesTxt.setText(" " + meal.getLikes());

                //Picasso.get().load(fullurl(meal.getImgurl())).into(mealImg);


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
}
