package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dao.UserDAO;
import model.User;

public class LogIn extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Button buttonRegister = (Button)findViewById(R.id.button);
        username = (EditText) findViewById (R.id.username);
        password = (EditText) findViewById (R.id.password);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = new UserDAO(getBaseContext());
                try {
                    userDAO.open();
                    Log.v("Open() successful", "");
                }
                catch (Exception e) {
                    Log.v("Error open()", e.getMessage());
                }
                try {
                    if (!userDAO.checkAuthentication(username.getText().toString(), password.getText().toString())) {
                        Toast.makeText(getBaseContext(), "Username or Password incorrect!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Log.v("authentication: ", e.getMessage());
                }
                Intent intentLogIn = new Intent(getBaseContext(), PersonalSpace.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });


    }
}
