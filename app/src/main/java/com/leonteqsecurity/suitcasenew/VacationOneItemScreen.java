package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.Services.ProductService;
import com.leonteqsecurity.suitcasenew.Utills.AmazonProductOpener;
import com.leonteqsecurity.suitcasenew.Utills.EbayProductOpener;

import java.lang.invoke.ConstantCallSite;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class VacationOneItemScreen extends AppCompatActivity {
    private TextView vacationProductDescription,product_name;
    private DbHelper dbHelper;
    private String productName;
    private  VacationItem vacationItem;
    private  ImageView cardImageView;
    private  String productDescription;
    public static final String REFRESH_ACTION = "com.leonteqsecurity.suitcasenew.REFRESH_ACTION";

    private TextView vacationLocation,vacationDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new DbHelper(this);
        setContentView(R.layout.activity_vacation_one_item_screen);
        cardImageView=findViewById(R.id.cardImageView);
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
        System.out.println(vacationItemsList);

       Optional<VacationItem> vacationItemOptional= vacationItemsList.stream().filter(e->e.getItemName().equals(productname)).findFirst();
       if(vacationItemOptional.isPresent())
       {
           vacationItem=vacationItemOptional.get();
           System.out.println(vacationItem);
           vacationProductDescription.setText(vacationItemOptional.get().getItemDescription());
           try {
               cardImageView.setImageURI(Uri.parse(vacationItem.getProductImage()));

           }catch (Exception e)
           {
               System.out.println("cnvbdvj");
           }
           productDescription=vacationItem.getItemDescription();
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

    public void DeleteItem(View view) {
        ProductService productService = new ProductService(dbHelper,this);
        productService.deleteProduct(vacationItem.getItemDescription());
        Toast.makeText(this, "Deleted The Item Successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(REFRESH_ACTION);
        sendBroadcast(intent);
//        finish();

    }
    private void showAddVacationItemForm() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_add_vacation_item, null);

        TextInputEditText editTextItemName = view.findViewById(R.id.editTextItemName);
        TextInputEditText editTextItemDescription = view.findViewById(R.id.editTextItemDescription);
        TextInputEditText editTextItemPrice = view.findViewById(R.id.editTextItemPrice);

        editTextItemName.setText(vacationItem.getItemName());
        editTextItemDescription.setText(vacationItem.getItemDescription());
        editTextItemPrice.setText(vacationItem.getItemPrice());


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
                    dbHelper.updateVacationItem(vacationItem,productDescription);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void IsPurchased(View view) {
        ImageView imageView=(ImageView) findViewById(R.id.IsPuurchaseItem);
        imageView.setImageResource(R.drawable.correct);
    }

    public void EditItem(View view) {
        showAddVacationItemForm();
    }
}