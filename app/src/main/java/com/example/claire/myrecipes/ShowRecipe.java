package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import dao.RecipeDAO;
import dao.SessionDAO;
import model.Recipe;
import model.User;

public class ShowRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recipe);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        TextView recipeTitle = (TextView) findViewById(R.id.recipeTitle);
        TextView instructions = (TextView) findViewById(R.id.instructionsTextView);
        TextView ingredients = (TextView) findViewById(R.id.ingredientsTextView);

        Button addToFav = (Button) findViewById(R.id.buttonAddToFav);
        Button buttonCook = (Button) findViewById(R.id.buttonCook);
        Button buttonMod = (Button) findViewById(R.id.buttonModRec);

        final int recipeId = Integer.parseInt(intent.getStringExtra("Recipe"));
        final RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        Recipe r = recipeDAO.getRecipeByID(recipeId);
        recipeTitle.setText(r.getTitle());
        instructions.setText(r.getInstructions());
        ingredients.setText(r.getIngredientsAsString());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        final User user = sessionDAO.getUserConnected(getBaseContext());
        recipeDAO.setViewed(recipeId, user.getId());

        buttonCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDAO.setCooked(recipeId, user.getId());
                Intent intentLogIn = new Intent(getBaseContext(), ContinueCooking.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeDAO.setFavorite(recipeId, user.getId(), true);
                Toast.makeText(getBaseContext(), "Recipe added to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        buttonMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogIn = new Intent(getBaseContext(), AddRecipe.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });
    }
}
