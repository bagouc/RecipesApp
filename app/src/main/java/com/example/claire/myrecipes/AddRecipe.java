package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import dao.UserDAO;
import model.Ingredient;
import model.Recipe;
import model.User;

public class AddRecipe extends AppCompatActivity {
    private EditText title;
    private EditText instructions;
    private Vector<Ingredient> ingredients;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

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

        title = (EditText)findViewById(R.id.editText1);
        instructions = (EditText)findViewById(R.id.editText2);

        final IngredientDAO ingredientDAO = new IngredientDAO(getApplicationContext());
        ingredients = new Vector<Ingredient>();


        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
                Recipe r = new Recipe(user.getId(), title.getText().toString(), ingredients, instructions.getText().toString());
                long id = recipeDAO.addWithoutIng(r);
                Log.d("IDrecipe apres ajout", id+"");
                Intent intent = new Intent(getBaseContext(), SelectIngredientNewRecipe.class);
                intent.putExtra("id_recipe", (int)id);
                startActivity(intent);
            }
        });

    }
}
