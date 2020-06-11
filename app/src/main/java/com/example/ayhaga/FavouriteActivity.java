package com.example.ayhaga;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Meal> meals;
    //List<String> mealsIds = new ArrayList<String>();
    ArrayList<String> mealsIds = new ArrayList<>();

    Meal meal = new Meal();

    //List<Category> categories;
    //private static String JSON_URL = "https://dashboard.ayhaga.app/api/categories";
    private static String newJSON_URL = "https://dashboard.ayhaga.app/api/mealid/";
    FavouriteRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        meals = new ArrayList<Meal>();
        mealsIds.add("372");
        mealsIds.add("423");
        mealsIds.add("500");
        mealsIds.add("501");
        mealsIds.add("502");
        mealsIds.add("505");
        //meal = new Meal();
        recyclerView = findViewById(R.id.favouriteList);
        //categories = new ArrayList<>();
        for (int i=0;i<mealsIds.size();i++) {
            extractCategories(i);
        }
        Log.d("meals-size", meals.size()+"");

        //for (int i=0;i<6;i++) {
//            System.out.println(meals.get(0).getName()+"\n");
        //}

//        Toast.makeText(FavouriteActivity.this, meals.get(0).getName() + "  " +meals.get(3).getName() , Toast.LENGTH_LONG).show();

//        System.out.println(meals.get(0).getName());
//        System.out.println(meals.get(5).getName());
//        System.out.println(meals.get(4).getName());
//        System.out.println(meals.get(1).getName());
//        System.out.println(meals.get(2).getImgurl());
//        System.out.println(meals.get(3).getPreparation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new FavouriteRecyclerAdapter(getApplicationContext(), meals);
        recyclerView.setAdapter(adapter);
    }

    private void extractCategories(int index) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, newJSON_URL+mealsIds.get(index), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    System.out.println("=- =-= -= -= -= -= -= -=-= -= -=-= -= -= - " + response.getJSONObject(0).toString());
                    JSONObject mealObject = response.getJSONObject(0);
                    System.out.println("nameeee ^^^^^ ^ ^ ^    ^ ^ ^ ^ ^ ^ ^  ^" + mealObject.getString("name").toString());
                    meal.setName(mealObject.getString("name").toString());

                    //meal.setDesc(mealObject.getString("description").toString());
                    meal.setIngrediants(mealObject.getString("ingredients").toString());
                    meal.setPreparation(mealObject.getString("preparation").toString());

                    System.out.println(fullurl(mealObject.getString("main_photo")));
                    //Picasso.get().load(fullurl(mealObject.getString("main_photo"))).into(mealImg);
                    meal.setImgurl(fullurl(mealObject.getString("main_photo").toString()));
                    meal.setLikes(Integer.parseInt(mealObject.getString("likes")));
                    meals.add(meal);

                    Log.d("id", index+"");
                    Log.d("meal", meal.getName());
                } catch (JSONException e) {
                    System.out.println("Errooooooooooor ");
                    e.printStackTrace();
                }

                //meals.add(meal);


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
