package com.example.claire.myrecipes;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import dao.SessionDAO;
import dao.UserDAO;
import model.User;

public class PersonalSpace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space);

        ConstraintLayout test = (ConstraintLayout)findViewById(R.id.test);


        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        try {
            sessionDAO.open();
            Log.v("Open() successful", "");
            // we get the username of the user connected
            User user_connected = sessionDAO.getUserConnected(getBaseContext());
            getSupportActionBar().setTitle("Home");

            Button buttonSearchRecipe = (Button)findViewById(R.id.premier);
            Button buttonMyRecipes = (Button)findViewById(R.id.second);
            Button buttonMenus = (Button)findViewById(R.id.trois);
            Button buttonContinueCooking = (Button)findViewById(R.id.quatre);
            Button buttonAddRecipe = (Button)findViewById(R.id.cinq);
            Button buttonAddIngredient = (Button)findViewById(R.id.six);
            Button buttonSharedSpace = (Button)findViewById(R.id.sept);
            Button buttonSettings = (Button)findViewById(R.id.huit);

            
        }
        catch (Exception e) {
            Log.v("Error SessionDAO.open()", e.getMessage());
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_personal_space, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
            /* DO EDIT */
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
