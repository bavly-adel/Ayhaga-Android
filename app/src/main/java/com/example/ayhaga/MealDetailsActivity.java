package com.example.ayhaga;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MealDetailsActivity extends AppCompatActivity {

    private AdView mAdView;


    TextView nameTxt;
    TextView descTxt;
    TextView prepTxt;
    TextView ingredTxt;

    TextView ingrediantsTitleTxt;

    LinearLayoutCompat descLinear;
    LinearLayoutCompat prepLinear;
    LinearLayoutCompat ingredLinear;
    LinearLayoutCompat detailsParentLinear;


    RecyclerView photosList;
    List<String> photos;
    MealPhotosRecyclerAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        Intent intent = getIntent();
        Meal meal = (Meal) intent.getExtras().getSerializable("meal");

        descLinear = findViewById(R.id.descLayout);
        prepLinear = findViewById(R.id.prepLayout);
        ingredLinear = findViewById(R.id.ingrediantsLayout);
        detailsParentLinear = findViewById(R.id.detailsParentLinear);
        photosList = findViewById(R.id.photosList);

//

        nameTxt = findViewById(R.id.meal_details_name);
        descTxt = findViewById(R.id.meal_details_desc);
        prepTxt = findViewById(R.id.meal_details_prep);
        ingredTxt = findViewById(R.id.meal_details_ingred);

        ingrediantsTitleTxt = findViewById(R.id.ingrediantsTitleTxt);


        System.out.println(": : : : :  : : : : : : "+meal.getCategory_id()+": : : : : : :: : :: :   فوائد الفاكهة ");
      //  ingrediantsTitleTxt.setText("فوائد الفاكهة");

        if (meal.getCategory_id().equals("5")){

            System.out.println(": : : : :  : : : : : : "+meal.getCategory_id()+": : : : : : :: : :: :   فوائد الفاكهة ");
            ingrediantsTitleTxt.setText("فوائد الفاكهة");
        }else {
            System.out.println(": : : : :  : : : : : :  > > > > "+meal.getCategory_id()+": : : : : : :: : :: :   فوائد الفاكهة< < < << < ");


        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            nameTxt.setText(Html.fromHtml(meal.getName(), Html.FROM_HTML_MODE_LEGACY));
//            descTxt.setText(Html.fromHtml(meal.getDesc(), Html.FROM_HTML_MODE_LEGACY));
//            ingredTxt.setText(Html.fromHtml(meal.getIngrediants(), Html.FROM_HTML_MODE_LEGACY));
//            prepTxt.setText(Html.fromHtml(meal.getPreparation(), Html.FROM_HTML_MODE_COMPACT));
//            //nameTxt.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>", Html.FROM_HTML_MODE_COMPACT));
//        }
//        else {

        if (meal.getName() != null) {
            nameTxt.setText(meal.getName());
        }
        if (meal.getDesc() != null) {
            descTxt.setText(Html.fromHtml(meal.getDesc()));
        }else {
            detailsParentLinear.removeView(descLinear);

        }
        if (meal.getPreparation() != null) {
            prepTxt.setText(Html.fromHtml(meal.getPreparation()));
        }else {
            detailsParentLinear.removeView(prepLinear);

        }
        if (meal.getIngrediants() != null) {
            ingredTxt.setText(Html.fromHtml(meal.getIngrediants()));
        }else {
            detailsParentLinear.removeView(ingredLinear);


        }


        // add main photo and meal_photos to tje list
        photos = new ArrayList<String>();

        photos.add(meal.getImgurl());

        if (!(meal.getPhotos().size() ==1 && meal.getPhotos().get(0).equals("null")))
        {
            for (int i = 0; i < meal.getPhotos().size(); i++) {
                photos.add(fullurl(meal.getPhotos().get(i)));
            }
        }



        for (int i = 0; i < meal.getPhotos().size(); i++) {
            System.out.println(meal.getPhotos().get(i)+"\n");
        }
        System.out.println(photos.size()+"\n"+ meal.getPhotos().size() + "  " + meal.getPhotos().get(0));



        photosList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new MealPhotosRecyclerAdapter(getApplicationContext(),photos);
        photosList.setAdapter(adapter);

    }

    private String fullurl(String url){

        String newurl = "https://dashboard.ayhaga.app/storage/"+url;

        return newurl;

    }
}




