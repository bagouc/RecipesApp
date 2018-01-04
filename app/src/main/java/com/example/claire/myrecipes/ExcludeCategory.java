package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import dao.IngredientDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.User;

public class ExcludeCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exclude_category);
        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });
        initSpinner();

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                SessionDAO sessionDAO = new SessionDAO(getBaseContext());
                User u = sessionDAO.getUserConnected(getBaseContext());
                Spinner ingSpinner = (Spinner)findViewById(R.id.spinner);
                String category = ingSpinner.getSelectedItem().toString();
                long id_categ = ingredientDAO.getIdCategory(category);
                Log.d("NUM CATEG:", id_categ+"");
                ingredientDAO.addCategoryProhibited(id_categ, u.getId());
                Intent intent = new Intent(getBaseContext(), Preferences.class);
                startActivity(intent);
            }
        });
    }

    private void initSpinner() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> options = ingredientDAO.getCategoriesAvailable(u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
