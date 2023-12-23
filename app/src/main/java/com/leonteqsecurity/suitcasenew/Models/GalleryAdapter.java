package com.leonteqsecurity.suitcasenew.Models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.google.android.material.card.MaterialCardView;
import com.leonteqsecurity.suitcasenew.MainScreen;
import com.leonteqsecurity.suitcasenew.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<Integer> imageList;
    Context context;

    public GalleryAdapter(List<Integer> imageList, Context con) {
        this.imageList = imageList;
        context=con;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int imageResId = imageList.get(position);
        holder.imageViewGallery.setImageResource(imageResId);

        holder.cardViewGalleryItem.setOnClickListener(v -> {
            Intent intent=new Intent(context, MainScreen.class);
            context.startActivity(intent);
            ((Activity) context).finish();
            saveDataToSharedPreferences(context,"imagebg",String.valueOf(imageResId));
            saveDataToSharedPreferences(context,"IsActive","true");

        });
    }
    private void saveDataToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardViewGalleryItem;
        ImageView imageViewGallery;

        ViewHolder(View itemView) {
            super(itemView);
            cardViewGalleryItem = itemView.findViewById(R.id.cardViewGalleryItem);
            imageViewGallery = itemView.findViewById(R.id.imageViewGallery);
        }
    }
}
