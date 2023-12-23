package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.leonteqsecurity.suitcasenew.Models.GalleryAdapter;

import java.util.Arrays;
import java.util.List;

public class ImageGallary extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallary);

        List<Integer> imageList = Arrays.asList(
                R.drawable.bg1,
                R.drawable.bg2,
                R.drawable.bg3,
                R.drawable.bg4

        );
        RecyclerView recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        GalleryAdapter galleryAdapter = new GalleryAdapter(imageList,this);
        recyclerViewGallery.setAdapter(galleryAdapter);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 2));
    }
    }
