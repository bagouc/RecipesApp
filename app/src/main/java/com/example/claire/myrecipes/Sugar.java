package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import dao.IngredientDAO;

public class Sugar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        Button buttonSugar = (Button)findViewById(R.id.premier);
        Button buttonHoney = (Button)findViewById(R.id.second);
        Button buttonChocolate = (Button)findViewById(R.id.trois);
        Button buttonDone = (Button)findViewById(R.id.quatre);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ResultsRecipesIngredients.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                ingredientDAO.setSelected(1, getBaseContext());
            }
        });

        buttonHoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                ingredientDAO.setSelected(4, getBaseContext());
            }
        });

        buttonChocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                ingredientDAO.setSelected(5, getBaseContext());
            }
        });


    }
}
