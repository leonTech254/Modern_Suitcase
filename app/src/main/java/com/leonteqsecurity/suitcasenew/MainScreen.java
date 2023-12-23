package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.leonteqsecurity.suitcasenew.Adaptor.VacationAdaptor;
import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;

public class MainScreen extends AppCompatActivity {
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        GridView gridLayout = findViewById(R.id.gridView);

       DisplayCards();
    }
    public  void DisplayCards()
    {
        gridView = findViewById(R.id.gridView);
        CardItemVacations[] cardItems = {

                new CardItemVacations("Canada","imageUrl"),
                new CardItemVacations("Canada","imageUrl"),
                new CardItemVacations("Canada","imageUrl"),
                new CardItemVacations("Canada","imageUrl"),
        };
        VacationAdaptor adapter = new VacationAdaptor(this, cardItems);
        gridView.setAdapter(adapter);


    }
}


