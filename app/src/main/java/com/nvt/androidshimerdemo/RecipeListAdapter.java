package com.nvt.androidshimerdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.MyViewHolder>{
    Context context;
    List<Recipe> recipeList;

    public RecipeListAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Recipe recipeItem = recipeList.get(position);
        holder.name.setText(recipeItem.getName());
        holder.chef.setText("By " + recipeItem.getChef());
        holder.description.setText(recipeItem.getDescription());
        holder.price.setText("Price: ₹" + recipeItem.getPrice());
        holder.timestamp.setText(recipeItem.getTimestamp());

        Glide.with(context)
                .load(recipeItem.getThumbnail())
                .into(holder.thumbnail);
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price, chef, timestamp;
        public ImageView thumbnail;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            chef = view.findViewById(R.id.chef);
            description = view.findViewById(R.id.description);
            price = view.findViewById(R.id.price);
            thumbnail = view.findViewById(R.id.thumbnail);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }
}
