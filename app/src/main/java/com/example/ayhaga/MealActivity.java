package com.example.ayhaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity {

    private int category_id;
   // Meal meal;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/meal/1";


    TextView likesTxt;
    TextView nameTxt;
    ImageView mealImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        findViewById(R.id.menuBtnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer

                extractRandomMeal();

            }
        });

        nameTxt = (TextView) findViewById(R.id.mealName);
        likesTxt = (TextView) findViewById(R.id.likesTxt);
        mealImg = (ImageView) findViewById(R.id.mealImg);

        extractRandomMeal();
    }

    private void extractRandomMeal() {
        System.out.println("start 2 # # # # # #  # ### # # #  # # #  # # # # ## # # ### # #  #####  #");
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println(" } | }|} |}| | |} |} | }| }| | }|  *******");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Meal meal = new Meal();
                try {

                    System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + response.getJSONObject(0).toString());
                    JSONObject mealObject = response.getJSONObject(0);
                    System.out.println("nameeee ^^^^^ ^ ^ ^    ^ ^ ^ ^ ^ ^ ^  ^" + mealObject.getString("name").toString());
                    meal.setName(mealObject.getString("name").toString());

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
                likesTxt.setText(" "+meal.getLikes());

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

    private String fullurl(String url){

        String newurl = "https://dashboard.ayhaga.app/storage/"+url;

        return newurl;

    }
}
