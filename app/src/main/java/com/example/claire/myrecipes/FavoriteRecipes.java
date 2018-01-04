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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.OnlineRecipe;
import model.Recipe;
import model.User;
import model.UserSearch;

public class FavoriteRecipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes);

        TextView log = (TextView) findViewById(R.id.title);
        ListView listView;
        final List<String> resultList = new ArrayList();
        ArrayAdapter adapter;

        listView  = (ListView) findViewById(R.id.favoriteListView);

        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User user = sessionDAO.getUserConnected(getBaseContext());

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        final Vector<Recipe> results = recipeDAO.getFavorites(user.getId());

        for (int i = 0; i < results.size(); i++) {
            resultList.add(results.get(i).toString());
        }

        final Vector<OnlineRecipe> onlineResults = recipeDAO.getOnlineFavorites(user.getId());

        for (int i = 0; i < onlineResults.size(); i++) {
            resultList.add(onlineResults.get(i).toString());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList);
        listView.setAdapter(adapter);
        /*
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
        */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position < results.size()) {
                        Intent intent = new Intent(getBaseContext(), ShowRecipe.class);
                        intent.putExtra("Recipe", Long.toString( results.get(position).getId()));
                        startActivity(intent);
                    }
                    else {
                        OnlineRecipe or = onlineResults.get(position-results.size());
                        Intent intent = new Intent(getBaseContext(), webview.class);
                        intent.putExtra("source", or.getUrl());
                        //mark as viewed
                        final RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
                        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
                        final User user = sessionDAO.getUserConnected(getBaseContext());
                        recipeDAO.setOnlineViewed(or, user.getId());
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Log.v("Error1: ", e.getMessage());
                }
            }
        });

    }
}
