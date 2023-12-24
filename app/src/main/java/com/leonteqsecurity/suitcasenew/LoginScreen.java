package com.leonteqsecurity.suitcasenew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.User;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.IntConsumer;

public class LoginScreen extends AppCompatActivity {
    private DbHelper dbHelper;

    private EditText Username,Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        dbHelper=new DbHelper(this);
        InitializeElement();
    }

    public  void InitializeElement()
    {
        Username=findViewById(R.id.username);
        Password=findViewById(R.id.password);

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
        String username=Username.getText().toString();
        String password =Password.getText().toString();
        if(!username.isEmpty() || !password.isEmpty())
        {

//            check user credientials
            List<User> users=dbHelper.getAllUsers();
            //getting the firstuser from the database
            User firstUser= users.get(0);
            String dbUsername=firstUser.getUsername();
            String dbPassword=firstUser.getPassword();
            if(username.equals(dbUsername) && dbPassword.equals(password))
            {
                Intent intent=new Intent(this, MainScreen.class);
                startActivity(intent);

            }else
            {
                Toast.makeText(this, "Wrong username and password", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(this, "Username and password required", Toast.LENGTH_SHORT).show();
        }
    }
}