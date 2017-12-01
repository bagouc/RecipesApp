package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import dao.IngredientDAO;
import model.Ingredient;

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
                Spinner ingSpinner = (Spinner)findViewById(R.id.spinner);
                String ing = ingSpinner.getSelectedItem().toString();

                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                Ingredient i = ingredientDAO.getIngredient(ing);

                //ingredientDAO.addIngredientProhibited();
                Intent intent = new Intent(getBaseContext(), Preferences.class);
                startActivity(intent);
            }
        });
    }

    private void initSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
