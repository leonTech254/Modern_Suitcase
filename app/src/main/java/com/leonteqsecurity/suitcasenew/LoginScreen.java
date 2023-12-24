package com.leonteqsecurity.suitcasenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public class LoginScreen extends AppCompatActivity {
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        dbHelper=new DbHelper(this);
    }

    public void ToRegister(View view) {
        //Check if the user is Already registered
        List<User> userList=dbHelper.getAllUsers();
        if(userList.isEmpty())
        {
            Intent intent = new Intent(this, RegisterScreen.class);
            startActivity(intent);
        }else
        {
            Toast.makeText(this, "The user is Already Registered Successfully. \nKindly Login to the Existing Account", Toast.LENGTH_SHORT).show();
        }



    }

    public void SignIn(View view) {
        Intent intent=new Intent(this, MainScreen.class);
        startActivity(intent);
    }
}