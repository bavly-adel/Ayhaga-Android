package com.ctg.ayhaga;

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
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BooksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Book> books;
    private static String JSON_URL = "https://dashboard.ayhaga.app/api/books";
    BooksRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        books = new ArrayList<>();
        recyclerView = findViewById(R.id.booksList);

        getBooks();

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
