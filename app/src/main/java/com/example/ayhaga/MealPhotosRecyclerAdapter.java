package com.example.ayhaga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealPhotosRecyclerAdapter extends RecyclerView.Adapter<MealPhotosRecyclerAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<String> photos;


    public MealPhotosRecyclerAdapter(Context context, List<String> photos) {
        this.inflater = LayoutInflater.from(context);
        this.photos = photos;



    }

    @NonNull
    @Override
    public MealPhotosRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.meal_photo_item,parent,false);
        return new MealPhotosRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Picasso.get().load(photos.get(position)).into(holder.mealPhoto);

        holder.mealPhoto.setTag(photos.get(position));


        Picasso.get().load(photos.get(position)).into(holder.mealPhoto, new Callback() {
            @Override
            public void onSuccess() {
                holder.imgProgress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {


            }
        });




    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mealPhoto;
        ProgressBar imgProgress;

        private final Context context;


        public ViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();


            mealPhoto = itemView.findViewById(R.id.mealphoto);
            imgProgress = itemView.findViewById(R.id.mealPhotosProgress);
            imgProgress.setVisibility(View.VISIBLE);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, ImageFullScreenActivity.class);
                    i.putExtra("imgUrl",mealPhoto.getTag().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(i);

                }
            });
        }
    }
}
