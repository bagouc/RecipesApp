package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.Recipe;
import model.User;

public class ResultsRecipesIngredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_recipes_ingredients);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        TextView textView = (TextView) findViewById(R.id.asdf);

        // We get the ingredients selected by the user and create a list
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User user = sessionDAO.getUserConnected(getBaseContext());

        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        Vector<Ingredient> ing_list_wanted =  ingredientDAO.getListIngredientsSelected(user.getId());

        // TO CHANGE when allergies are implemented and settings
        Vector<Ingredient> forbidden = new Vector<Ingredient>();

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        Vector<Recipe> results = recipeDAO.searchRecipes(ing_list_wanted, forbidden);
        String s = "";
        /*
        try {
            s += "ingwanted: ";
            for (int i = 0; i < ing_list_wanted.size(); i++) {
                s += "\n" + ing_list_wanted.get(i).getName() + "\n";
            }
        } catch (Exception e) {
            Log.v("err: ", e.getMessage());
        }
        */
        s += "\nResult(s) : ";
        for (int i = 0; i < results.size(); i++) {
            s += "\n\n" + results.get(i).toString();
        }

        textView.setText(s);

    }
}
