package com.leonteqsecurity.suitcasenew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.User;

public class RegisterScreen extends AppCompatActivity {
    private TextView FirstName,LastName,Email,Password,Username;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        dbHelper=new DbHelper(this);
        IntializeElemenet();

    }

    private  void IntializeElemenet()
    {
        FirstName=findViewById(R.id.firstNameEditText);
        LastName=findViewById(R.id.lastNameEditText);
        Email=findViewById(R.id.emailEditText);
        Password=findViewById(R.id.password);
        Username=findViewById(R.id.usernameEditText);
    }


    public void ToLogin(View view) {
        String firstname=FirstName.getText().toString();
        String lastname=LastName.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();
        String username=Username.getText().toString();
        if(firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty() || username.isEmpty())
        {
            Toast.makeText(this, "All Fields Required", Toast.LENGTH_SHORT).show();

        }else
        {
            User user=new User();
            user.setEmail(email);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassword(password);
            user.setUsername(username);
            user.setEmail(email);
            dbHelper.insertUser(user);
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
    }
}