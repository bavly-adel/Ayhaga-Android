package com.ctg.ayhaga;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BooksActivity extends AppCompatActivity {

    private AdView mAdView;

    AppCompatEditText childNameTxt;
    ProgressBar progressBar;

    LinearLayoutCompat latestBokks;
    LinearLayoutCompat booksParentLayout;

    TextView latestTxt;


    RecyclerView recyclerView;
    List<Book> books;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/books/";
    BooksRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.bookAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        latestBokks = findViewById(R.id.latestBooks);
        booksParentLayout = findViewById(R.id.booksParentLayout);

        latestTxt = findViewById(R.id.latestTxt);
        progressBar = findViewById(R.id.bookProgress);

        progressBar.setVisibility(View.INVISIBLE);

        books = new ArrayList<>();
        recyclerView = findViewById(R.id.booksList);
        childNameTxt = findViewById(R.id.childNameTxt);

        findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open right drawer
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }
                books.clear();
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }

                }, 2000);
                getBookByName(childNameTxt.getText().toString());


            }
        });

        getBooks();

    }

    private void getBookByName(String name){

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL+name, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    if(response.length() >0) {

                        JSONObject booksObject = response.getJSONObject(0);
                        Book book = new Book();
                        book.setName(booksObject.getString("name").toString());
                        book.setFile_path(booksObject.getString("file_path").toString());
                        book.setId(Integer.parseInt(booksObject.getString("id").toString()));

                        //System.out.println("=- =-= -="+book.getFile_path()+" -= -= -= -"+book.getName()+"= -=-="+book.getId()+" -= -=-= -= -= - " + i);

                        books.add(book);

                        latestTxt.setText("");
                    }else {

                        latestTxt.setText("الإسم غير متاح حالياً، تابعنا على مدار اليوم وهيتم اضافته فى أقرب وقت ");
//                        builder.setMessage("الإسم غير متاح حالياً، تابعنا على مدار اليوم وهيتم اضافته فى أقرب وقت ")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        finish();
//                                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        //  Action for 'NO' Button
//                                        dialog.cancel();
//                                        Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                        //Creating dialog box
//                        AlertDialog alert = builder.create();
//                        //Setting the title manually
//                        alert.setTitle("AlertDialogExample");
//                        alert.show();
                    }

                } catch (JSONException e) {
                    System.out.println("Errooooooooooor ");
                    e.printStackTrace();
                }


                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new BooksRecyclerAdapter(getApplicationContext(),books);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);
        //progressBar.setVisibility(View.INVISIBLE);

    }


    private void getBooks() {
        System.out.println("start 2 # # # # # #  # ### # # #  # # #  # # # # ## # # ### # #  #####  #");
        RequestQueue queue = Volley.newRequestQueue(this);
        System.out.println(" } | }|} |}| | |} |} | }| }| | }|  *******");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
//
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject booksObject = response.getJSONObject(i);

                        Book book = new Book();
                        book.setName(booksObject.getString("name").toString());
                        book.setFile_path(booksObject.getString("file_path").toString());
                        book.setId(Integer.parseInt(booksObject.getString("id").toString()));

                        System.out.println("=- =-= -="+book.getFile_path()+" -= -= -= -"+book.getName()+"= -=-="+book.getId()+" -= -=-= -= -= - " + i);

                        books.add(book);

                    } catch (JSONException e) {
                        System.out.println("Errooooooooooor ");
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new BooksRecyclerAdapter(getApplicationContext(),books);
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
}
