package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search");

        Button buttonSelectIngredients = (Button)findViewById(R.id.premier);
        Button buttonSuggestions = (Button)findViewById(R.id.second);

        buttonSelectIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SelectIngredients.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSuggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Suggestions.class);
                getBaseContext().startActivity(intent);
            }
        });
    }
}
