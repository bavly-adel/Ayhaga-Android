package com.example.ayhaga;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

public class MealDetailsActivity extends AppCompatActivity {


    TextView nameTxt;
    TextView descTxt;
    TextView prepTxt;
    TextView ingredTxt;

    TextView ingrediantsTitleTxt;

    LinearLayoutCompat descLinear;
    LinearLayoutCompat prepLinear;
    LinearLayoutCompat ingredLinear;
    LinearLayoutCompat detailsParentLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        Intent intent = getIntent();
        Meal meal = (Meal) intent.getExtras().getSerializable("meal");

        descLinear = findViewById(R.id.descLayout);
        prepLinear = findViewById(R.id.prepLayout);
        ingredLinear = findViewById(R.id.ingrediantsLayout);
        detailsParentLinear = findViewById(R.id.detailsParentLinear);
        //if(meal.get)

//
        System.out.println("- -- -  -- -- ---- - - -- -- --  "+meal.getName());

        nameTxt = findViewById(R.id.meal_details_name);
        descTxt = findViewById(R.id.meal_details_desc);
        prepTxt = findViewById(R.id.meal_details_prep);
        ingredTxt = findViewById(R.id.meal_details_ingred);

        ingrediantsTitleTxt = findViewById(R.id.ingrediantsTitleTxt);


        System.out.println(": : : : :  : : : : : : "+meal.getCategory_id()+": : : : : : :: : :: :   فوائد الفاكهة ");
        ingrediantsTitleTxt.setText("فوائد الفاكهة");

        if (meal.getCategory_id() == "5"){

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
        //}

//        nameTxt.setText(meal.getName());
//        descTxt.setText(meal.getDesc());
//        ingredTxt.setText(meal.getIngrediants());
//        prepTxt.setText(meal.getPreparation());



    }
}




