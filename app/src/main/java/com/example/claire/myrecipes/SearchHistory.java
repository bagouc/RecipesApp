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
import android.widget.Toast;

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

public class SearchHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

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

        listView = (ListView) findViewById(R.id.recentSearchesListView);

        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        sessionDAO.open();
        Log.v("Open() successful", "");

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        final Vector<UserSearch> searches = recipeDAO.getLastSearches(sessionDAO.getUserConnected(getBaseContext()).getId());

        for (int i = 0; i < searches.size(); i++) {
            resultList.add(searches.get(i).toString());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Vector<Ingredient> ingredients = searches.get(position).getIngredients();
                    IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                    for (int i = 0; i < ingredients.size(); i++)
                        ingredientDAO.setSelected(ingredients.get(i).getId(), getBaseContext());

                    Intent intent = new Intent(getBaseContext(), ResultsRecipesIngredients.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.v("Error1: ", e.getMessage());
                }
            }
        });
    }
}
