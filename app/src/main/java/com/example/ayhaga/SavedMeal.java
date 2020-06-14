package com.example.ayhaga;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavedMeal {

    public Integer id;
    public String cat_id;
    public String name;
    public String imageUrl;
    public String Date;


    public SavedMeal(Integer id, String cat_id, String name, String imageUrl, String date) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.imageUrl = imageUrl;
        Date = date;
    }

    public boolean valid() {
        Date today = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(today);

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = df.parse(Date);
            date2 = df.parse(formattedDate);
            if (date1.compareTo(date2)<0)
            {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }


}
