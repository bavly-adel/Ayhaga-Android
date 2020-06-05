package com.example.ayhaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    //private Button menuBtn;

//    private DrawerLayout dl;
//    private ActionBarDrawerToggle t;
//    private NavigationView nv;

    RecyclerView recyclerView;
    List<Category> categories;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/categories";
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.categoryList);
        categories = new ArrayList<>();
        extractCategories();

        findViewById(R.id.menuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
                drawer.openDrawer(GravityCompat.END);
            }
        });

        findViewById(R.id.menuBtnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_home);
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        findViewById(R.id.homeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
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

}
