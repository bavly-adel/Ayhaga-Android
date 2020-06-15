package com.ctg.ayhaga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteRecyclerAdapter extends RecyclerView.Adapter<FavouriteRecyclerAdapter.ViewHolder> {

    LayoutInflater inflater;
    //List<Category> categories;
    List<Meal> meals;

    public FavouriteRecyclerAdapter(Context context, List<Meal> meals) {
        this.inflater = LayoutInflater.from(context);
        this.meals = meals;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favourite_row_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        System.out.println("# # #  # # # ## # # # # # #   -- -- - - -   "+position +"   - -- - - ---  -- -  ## # 33 #  # 3 33  # ");
        holder.name.setText(meals.get(position).getName());
        Picasso.get().load(meals.get(position).getImgurl()).into(holder.mealImg);

        //holder.name.setTag(categories.get(position).getId());
        //Picasso.get().load("https://dashboard.ayhaga.app/storage/meals/June2020/5pkdJV60A88KThH7MeaJ.jpg").into(holder.catImg);



    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView mealImg;
        private final Context context;


        public ViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();


            name = itemView.findViewById(R.id.mealName);
            mealImg = itemView.findViewById(R.id.mealImg);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, MealDetailsActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //int id = (int) name.getTag(Integer.parseInt("id"));
                    //int id = 2;
                    //i.putExtra("meal",itemView);
                    context.startActivity(i);

                    //Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
