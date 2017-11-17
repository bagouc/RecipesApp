package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectIngredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredients);

        getSupportActionBar().setTitle("Ingredients");

        Button buttonProteins = (Button)findViewById(R.id.premier);
        Button buttonVegetables = (Button)findViewById(R.id.second);
        Button buttonFibre = (Button)findViewById(R.id.trois);
        Button buttonFats = (Button)findViewById(R.id.quatre);
        Button buttonFruits = (Button)findViewById(R.id.cinq);
        Button buttonSpices = (Button)findViewById(R.id.six);
        Button buttonSugar = (Button)findViewById(R.id.sept);
        Button buttonDairy = (Button)findViewById(R.id.huit);

        buttonProteins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Proteins.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Vegetables.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonFats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Fats.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonFibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Fibre.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Fruits.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSpices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Spices.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Sugar.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Dairy.class);
                getBaseContext().startActivity(intent);
            }
        });
    }
}
