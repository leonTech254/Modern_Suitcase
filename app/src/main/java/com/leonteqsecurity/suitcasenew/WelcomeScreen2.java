package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen2);
    }

    public void ToLogin(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}