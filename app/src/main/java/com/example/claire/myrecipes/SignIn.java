package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dao.UserDAO;
import model.User;

public class SignIn extends AppCompatActivity {
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button buttonRegister = (Button)findViewById(R.id.register);
        username = (EditText) findViewById (R.id.username);
        password = (EditText) findViewById (R.id.password);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO userDAO = new UserDAO(getBaseContext());
                User u = new User(username.getText().toString(), password.getText().toString());
                userDAO.add(u);
                User result = userDAO.select(1);
                if (result != null) {
                    String chaine = result.getUsername();
                    Log.v("Essai",chaine);
                } else {
                    String chaine = "problem";
                    Log.v("Essai",chaine);
                }

                Intent intentLogIn = new Intent(getBaseContext(), PersonalSpace.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });


    }
}
