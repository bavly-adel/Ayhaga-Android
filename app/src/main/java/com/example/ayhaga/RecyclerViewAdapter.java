package com.example.ayhaga;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Category> categories;

    public RecyclerViewAdapter(Context context, List<Category> categories) {
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_row_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.name.setText(categories.get(position).getName());
        Picasso.get().load(categories.get(position).getImg_url()).into(holder.catImg);
        //Picasso.get().load("https://dashboard.ayhaga.app/storage/meals/June2020/5pkdJV60A88KThH7MeaJ.jpg").into(holder.catImg);



    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView catImg;


        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.catName);
            catImg = itemView.findViewById(R.id.catImg);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
