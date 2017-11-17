package com.example.claire.myrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        getSupportActionBar().setTitle("Results");

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

        String s = "Search 1: ";
        for (int i = 0; i < results.size(); i++) {
            s += "\n\n" + results.get(i).toString();
        }

        s += "\n\nSearch 2: ";
        results = recipeDAO.searchRecipes(new Vector<Ingredient>(), new Vector<Ingredient>());
        for (int i = 0; i < results.size(); i++) {
            s += "\n\n" + results.get(i).toString();
        }
        textView.setText(s);

    }
}
