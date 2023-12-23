package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.leonteqsecurity.suitcasenew.Adaptor.VacationItemAdapter;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.List;

public class VacationItemScreen extends AppCompatActivity {

    private DbHelper dbHelper;
    private RecyclerView recyclerViewItems;
    private VacationItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_item_screen);
        dbHelper = new DbHelper(this);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        // Assume you have a dbHelper instance
        List<VacationItem> itemList = dbHelper.getAllVacationItems();

        itemAdapter = new VacationItemAdapter(itemList);
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

                    // Add VacationItem to the database
                    dbHelper.insertVacationItem(vacationItem);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
