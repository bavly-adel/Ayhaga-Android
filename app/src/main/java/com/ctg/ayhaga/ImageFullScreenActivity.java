package com.ctg.ayhaga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class ImageFullScreenActivity extends AppCompatActivity {

    ImageView fullImage;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);

        Intent intent = getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");

        fullImage = findViewById(R.id.fullImg);
        progressBar = findViewById(R.id.fullImgProgress);
        progressBar.setVisibility(View.VISIBLE);

        Picasso.get().load(imgUrl).into(fullImage, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });


        findViewById(R.id.closeImgBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }


}
