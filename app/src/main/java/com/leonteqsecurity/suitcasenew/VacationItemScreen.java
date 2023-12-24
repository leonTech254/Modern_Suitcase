package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.leonteqsecurity.suitcasenew.Adaptor.VacationItemAdapter;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.CardItemVacations;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VacationItemScreen extends AppCompatActivity {

    private DbHelper dbHelper;
    private RecyclerView recyclerViewItems;
    private VacationItemAdapter itemAdapter;

    private  String vacationId;

    private  CardItemVacations Vacation;
    private ImageView cardImageView;
    private TextView vacationLocation,vacationDescription;

    private String itemImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_item_screen);
        dbHelper = new DbHelper(this);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new GridLayoutManager(this,2));

        // Assume you have a dbHelper instance
        Intent intent = getIntent();
        vacationId = intent.getStringExtra("vacationId");
        ShowVaction(vacationId);
    }


    private  void  ShowVaction(String vacationId)
    {
        List<VacationItem> itemList = dbHelper.getAllVacationItems();
        Collections.reverse(itemList);
        List<CardItemVacations> vacations=dbHelper.getAllVacations();
        Optional<CardItemVacations> vacationsOptional=vacations.stream().filter(e->e.getId()==Integer.parseInt(vacationId)).findFirst();
        if(vacationsOptional.isPresent())
        {
            CardItemVacations Vacation=vacationsOptional.get();
            vacationDescription=findViewById(R.id.vacationDescription);
            vacationDescription.setText(Vacation.getDescription());
            cardImageView=findViewById(R.id.cardImageView);
            cardImageView.setImageResource(Integer.parseInt(Vacation.getImageBackground()));
            vacationLocation=findViewById(R.id.vacationLocation);
            vacationLocation.setText(Vacation.getVacationLocation());
        }
        itemAdapter = new VacationItemAdapter(this,itemList);
        recyclerViewItems.setAdapter(itemAdapter);
    }

    private void showAddVacationItemForm() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_add_vacation_item, null);

        TextInputEditText editTextItemName = view.findViewById(R.id.editTextItemName);
        TextInputEditText editTextItemDescription = view.findViewById(R.id.editTextItemDescription);
        TextInputEditText editTextItemPrice = view.findViewById(R.id.editTextItemPrice);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Add Vacation Item")
                .setView(view)
                .setPositiveButton("Add", (dialogInterface, i) -> {
                    // Retrieve data from the form
                    String itemName = editTextItemName.getText().toString().trim();
                    String itemDescription = editTextItemDescription.getText().toString().trim();
                    String itemPrice = editTextItemPrice.getText().toString().trim();

                    // Validate input (add more validation as needed)

                    // Create VacationItem object
                    VacationItem vacationItem = new VacationItem();
                    vacationItem.setItemName(itemName);
                    vacationItem.setItemDescription(itemDescription);
                    vacationItem.setItemPrice(itemPrice);
                    vacationItem.setContryname(vacationLocation.getText().toString());
                    dbHelper.insertVacationItem(vacationItem);
                    ShowVaction(vacationId);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void AddItem(View view) {
        showAddVacationItemForm();
    }
}
