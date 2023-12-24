package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.leonteqsecurity.suitcasenew.Adaptor.VacationAdaptor;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.Collections;
import java.util.List;

public class MainScreen extends AppCompatActivity {
    private GridView gridView;
    private DbHelper dbHelper;
    private  String imageBackground;
    private  String IsActive;

    private boolean IsAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        GridView gridLayout = findViewById(R.id.gridView);
        dbHelper=new DbHelper(this);
        IsAll=true;

       DisplayCards(true);
    }
    public  void DisplayCards(boolean display2)
    {
        IsActive= getDataFromSharedPreferences(this, "IsActive");
        if(IsActive.equals("true"))
        {
            showAddVacationForm();
            saveDataToSharedPreferences(this,"IsActive","false");

        }
        List<CardItemVacations> cardItemVacationsList=dbHelper.getAllVacations();
        Collections.reverse(cardItemVacationsList);

       List<CardItemVacations> cardItemVacations= FilterLists(cardItemVacationsList,display2);



        gridView = findViewById(R.id.gridView);
//        CardItemVacations[] cardItems = {
//
//                new CardItemVacations("Canada","imageUrl"),
//                new CardItemVacations("Kenya","imageUrl"),
//                new CardItemVacations("Usa","imageUrl"),
//                new CardItemVacations("Nigeria","imageUrl"),
//        };
        VacationAdaptor adapter = new VacationAdaptor(this, cardItemVacations.toArray(new CardItemVacations[0]));
        gridView.setAdapter(adapter);
    }

    public  List<CardItemVacations>  FilterLists(List<CardItemVacations> cardItemVacationsList,boolean display2)
    {
        if(display2)
        {
            if(cardItemVacationsList.size()>2)
            {
                List<CardItemVacations> firstTwoElements = cardItemVacationsList.subList(0, Math.min(cardItemVacationsList.size(), 2));
                return firstTwoElements;
            }else
            {
                return cardItemVacationsList;
            }

        }else {
            return cardItemVacationsList;

        }


    }



    public void AddVactionLocation(View view) {
        Intent intent = new Intent(this,ImageGallary.class);
        startActivity(intent);
//        showAddVacationForm();
    }

    private void showAddVacationForm() {
        imageBackground = getDataFromSharedPreferences(this, "imagebg");
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_add_vacation, null);

        EditText editTextLocation = view.findViewById(R.id.editTextLocation);
        EditText editTextDescription = view.findViewById(R.id.formVacationDescription);
        new MaterialAlertDialogBuilder(this)
                .setTitle("Add Vacation Location")
                .setView(view)
                .setPositiveButton("Add", (dialogInterface, i) -> {
                    // Retrieve data from the form
                    String location = editTextLocation.getText().toString();
                    String vacationDescription = editTextDescription.getText().toString();

                    // Add the new vacation to the database
                    CardItemVacations cardItemVacations=new CardItemVacations();
                    cardItemVacations.setDescription(vacationDescription);
                    cardItemVacations.setImageBackground(imageBackground);
                    cardItemVacations.setVacationLocation(location);
                    dbHelper.insertVacation(cardItemVacations);

                    // Refresh the grid or update UI as needed
                    DisplayCards(true);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String getDataFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, ""); // Default value is an empty string
    }

    private void saveDataToSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void SeeAllVacation(View view) {
        TextView seeAll=(TextView) findViewById(R.id.seeAll);
        if(IsAll)
        {
            DisplayCards(false);
            seeAll.setText("see less");
            IsAll=false;
        }else
        {
            DisplayCards(true);
            seeAll.setText("see All");
            IsAll=true;
        }

    }
}


