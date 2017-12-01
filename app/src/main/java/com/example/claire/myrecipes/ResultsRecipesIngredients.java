package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.Recipe;
import model.User;
import model.UserSearch;

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

        ListView listView;
        final List<String> resultList = new ArrayList();
        ArrayAdapter adapter;

        listView  = (ListView) findViewById(R.id.resultList);
        TextView searchView = (TextView) findViewById(R.id.searchView);


        // We get the ingredients selected by the user and create a list
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User user = sessionDAO.getUserConnected(getBaseContext());

        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        Vector<Ingredient> ing_list_wanted =  ingredientDAO.getListIngredientsSelected(user.getId());

        // TO CHANGE when allergies are implemented and settings
        Vector<Ingredient> forbidden = new Vector<Ingredient>();

        String s = "";
        s += "Ingredients: ";
        for (int i = 0; i < ing_list_wanted.size() - 1; i++) {
            s += ing_list_wanted.get(i).getName() + ", ";
        }
        if (ing_list_wanted.size() > 0)
            s += ing_list_wanted.get(ing_list_wanted.size()-1).getName();
        searchView.setText(s);

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        final Vector<Recipe> results = recipeDAO.searchRecipes(ing_list_wanted, forbidden);

        recipeDAO.addSearch(new UserSearch(user.getId(), "", ing_list_wanted));

        for (int i = 0; i < results.size(); i++) {
            resultList.add(results.get(i).toString());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = new Intent(getBaseContext(), ShowRecipe.class);
                    intent.putExtra("Recipe", Long.toString( results.get(position).getId()));
                    startActivity(intent);
                } catch (Exception e) {
                    Log.v("Error1: ", e.getMessage());
                }
            }
        });
    }
}
