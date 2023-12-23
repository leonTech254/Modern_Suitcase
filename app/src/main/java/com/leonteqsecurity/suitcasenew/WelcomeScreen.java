package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void ToWelcome2(View view) {
        Intent intent = new Intent(this,WelcomeScreen2.class);
        startActivity(intent);
    }
}