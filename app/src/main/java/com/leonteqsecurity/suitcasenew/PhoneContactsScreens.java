package com.leonteqsecurity.suitcasenew;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.leonteqsecurity.suitcasenew.Adaptor.PhoneBookAdaptor;
import com.leonteqsecurity.suitcasenew.Models.PhoneBookModel;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhoneContactsScreens extends AppCompatActivity {
    private static final int CONTACTS_PERMISSION_REQUEST = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<PhoneBookModel> phoneBookModelList;
    private TextView nav_bar_title;
    private int dbIdItem;
    private ImageView backButton;
    private  String vacationItemString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts_screens);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        vacationItemString = intent.getStringExtra("vacationItem");
        initializeItems();
        readContacts(); // Call the method to read contacts.
//        backButton= findViewById(R.id.back_button);
//        backButton.setClickable(true);
//        backButton.setOnClickListener(v->goback());
    }

    private void goback() {
        onBackPressed();
    }

    private void initializeItems() {
        dbIdItem = getIntent().getIntExtra("dbId", 2467);
        recyclerView = findViewById(R.id.phone_book_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoneBookModelList = new ArrayList<>();

        String[] permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SEND_SMS
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Request contacts permission
            ActivityCompat.requestPermissions(this, permissions, CONTACTS_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CONTACTS_PERMISSION_REQUEST) {
            boolean allGranted = true;
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                // All permissions granted
                readContacts(); // Read contacts after obtaining necessary permissions.
            } else {
                // Some permissions denied
            }
        }
    }

    @SuppressLint("Range")
    private void readContacts() {
        Set<String> addedPhoneNumbers = new HashSet<>();
        // Query the contacts using the ContactsContract API
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Check if the phone number is not a duplicate and the name is not empty
                if (!addedPhoneNumbers.contains(phoneNumber) && phoneNumber.length() >= 5 && !name.isEmpty()) {
                    PhoneBookModel phoneBookModel = new PhoneBookModel(name, phoneNumber);
                    phoneBookModelList.add(phoneBookModel);
                    addedPhoneNumbers.add(phoneNumber); // Add the phone number to the set
                } else {
                    Log.d("CONTACTS", "Skipped duplicate, invalid, or empty contact");
                }
            }
            cursor.close();

            // Sort the list alphabetically based on names
            Collections.sort(phoneBookModelList, new Comparator<PhoneBookModel>() {
                @Override
                public int compare(PhoneBookModel contact1, PhoneBookModel contact2) {
                    return contact1.getPhone_name().compareToIgnoreCase(contact2.getPhone_name());
                }
            });

            Gson gson= new Gson();
            System.out.println(vacationItemString);
            VacationItem vacationItemConverted=gson.fromJson(vacationItemString,VacationItem.class);
            adapter = new PhoneBookAdaptor(phoneBookModelList, this, vacationItemConverted);
            recyclerView.setAdapter(adapter);
        }
    }
}
