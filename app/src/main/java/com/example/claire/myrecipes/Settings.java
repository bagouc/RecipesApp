package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dao.SessionDAO;
import dao.UserDAO;
import model.User;

public class Settings extends AppCompatActivity {
    private ViewPager mViewPager;
    private EditText username;
    private EditText new_password;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        //setContentView(R.layout.tag_tabs);
        SessionDAO sessionDAO = new SessionDAO(getApplicationContext());
        user  = sessionDAO.getUserConnected(getBaseContext());

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        username = (EditText)findViewById(R.id.editText3);
        username.setText(user.getUsername());
        new_password = (EditText)findViewById(R.id.editText4);

        Button buttonSave = (Button) findViewById(R.id.button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDAO userDAO = new UserDAO(getBaseContext());
                if (new_password.getText().toString().equals("")) {
                    User u = userDAO.select(user.getId());
                    userDAO.update(username.getText().toString(), user.getId());
                } else {
                    userDAO.update(username.getText().toString(), new_password.getText().toString(), user.getId());
                }
                Toast.makeText(getBaseContext(), "Modifications saved",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), Settings.class);
                startActivity(intent);
            }
        });

        Button buttonLogOut = (Button) findViewById(R.id.buttonSave);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Welcome.class);
                startActivity(intent);
            }
        });


        Button buttonModify = (Button) findViewById(R.id.button3);
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Preferences.class);
                startActivity(intent);
            }
        });



    }

}
