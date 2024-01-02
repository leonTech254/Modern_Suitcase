package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final int REQUEST_PICK_IMAGE = 100;
    public static final String REFRESH_ACTION = "com.leonteqsecurity.suitcasenew.REFRESH_ACTION";
    private final BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Handle the broadcast and perform your refresh operations here
            showAddVacationItemForm();
            Toast.makeText(context, "Hello from the receiver", Toast.LENGTH_SHORT).show();

        }
    };

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(REFRESH_ACTION);
        registerReceiver(refreshReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(refreshReceiver);
    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Handle the selected image URI from the gallery
            Uri selectedImageUri = data.getData();
            Toast.makeText(this, "Selected Image URI: " + selectedImageUri, Toast.LENGTH_SHORT).show();
            itemAdapter.onActivityResult(requestCode, resultCode, data);
            ShowVaction(vacationId);
        }

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
