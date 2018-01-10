package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.Recipe;
import model.User;

public class AddIngredient extends AppCompatActivity {
    private EditText name;
    private Spinner category;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        SessionDAO sessionDAO = new SessionDAO(getApplicationContext());
        user  = sessionDAO.getUserConnected(getBaseContext());

        name = (EditText)findViewById(R.id.editText1);
        category = (Spinner) findViewById(R.id.spinner);

        String [] itemArray = {"Proteins", "Fruits", "Vegetables", "Dairy", "Fats", "Fiber", "Sugar", "Spices"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,itemArray);
        category.setAdapter(adapter);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                String cat = category.getSelectedItem().toString();
                long id_cat = ingredientDAO.getIdCategory(cat);
                Ingredient i = new Ingredient(name.getText().toString(), id_cat, user.getId());
                ingredientDAO.add(i);
                Toast.makeText(getBaseContext(), "Ingredient saved",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

    }

}
