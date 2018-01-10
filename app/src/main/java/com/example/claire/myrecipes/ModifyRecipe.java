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
import model.Ingredient;
import model.Recipe;
import model.User;

public class ModifyRecipe extends AppCompatActivity {
    private EditText title;
    private EditText instructions;
    private Vector<Ingredient> ingredients;
    private User user;
    private long id_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);

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

        final RecipeDAO recipeDAO = new RecipeDAO(getApplicationContext());
        id_recipe = getIntent().getExtras().getInt("id_recipe");
        Recipe r = recipeDAO.getRecipeByID(id_recipe);

        title = (EditText)findViewById(R.id.editText1);
        title.setText(r.getTitle());

        instructions = (EditText)findViewById(R.id.editText2);
        instructions.setText(r.getInstructions());

        final IngredientDAO ingredientDAO = new IngredientDAO(getApplicationContext());
        ingredients = new Vector<Ingredient>();


        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeDAO.upDateTitleInstructions(title.getText().toString(), instructions.getText().toString(), id_recipe);
                Intent intent = new Intent(getBaseContext(), ModifyIngredientNewRecipe.class);
                intent.putExtra("id_recipe", (int)id_recipe);
                startActivity(intent);
            }
        });

    }


}
