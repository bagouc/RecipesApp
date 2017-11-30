package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Button buttonExcludeIngredient = (Button)findViewById(R.id.button2);
        buttonExcludeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExcludeIngredient.class);
                startActivity(intent);
            }
        });

        Button buttonExcludeCategory = (Button)findViewById(R.id.button4);
        buttonExcludeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExcludeCategory.class);
                startActivity(intent);
            }
        });
    }
}
