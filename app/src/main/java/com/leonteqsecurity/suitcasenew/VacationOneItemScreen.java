package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.Utills.AmazonProductOpener;
import com.leonteqsecurity.suitcasenew.Utills.EbayProductOpener;

import java.util.List;
import java.util.Optional;

public class VacationOneItemScreen extends AppCompatActivity {
    private TextView vacationProductDescription,product_name;
    private DbHelper dbHelper;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new DbHelper(this);
        setContentView(R.layout.activity_vacation_one_item_screen);
        vacationProductDescription=findViewById(R.id.vacationProductDescription);
        product_name=findViewById(R.id.product_name);
        Intent intent = getIntent();
        productName = intent.getStringExtra("ProductId");
        AllProducts(productName);

    }



    public void AllProducts(String productname)
    {
        Toast.makeText(this, "i got it "+productName, Toast.LENGTH_SHORT).show();
        List<VacationItem> vacationItemsList=dbHelper.getAllVacationItems();


       Optional<VacationItem> vacationItemOptional= vacationItemsList.stream().filter(e->e.getItemName().equals(productname)).findFirst();
       if(vacationItemOptional.isPresent())
       {
           vacationProductDescription.setText(vacationItemOptional.get().getItemDescription());
           product_name.setText(productname);
       }else
       {
           Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
       }

    }

    public void SearcEbay(View view) {
     EbayProductOpener.searchProductByName(this,productName);
    }
    public void SearchAmazon(View view) {
        AmazonProductOpener.searchProductByName(this,productName);
    }
}