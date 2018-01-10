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
import model.OnlineRecipe;
import model.Recipe;
import model.User;

public class UserHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

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

        listView  = (ListView) findViewById(R.id.listView2);

        ListView listView2;
        final List<String> resultList2 = new ArrayList();
        ArrayAdapter adapter2;

        final List<String> imgsrc = new ArrayList();
        final List<String> imgsrc2 = new ArrayList();

        listView2  = (ListView) findViewById(R.id.listView3);

        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User user = sessionDAO.getUserConnected(getBaseContext());

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        final Vector<Recipe> results = recipeDAO.getRecipesLastViewed(user.getId());
        final Vector<Recipe> results2 = recipeDAO.getRecipesLastCooked(user.getId());

        for (int i = 0; i < results.size(); i++) {
            resultList.add(results.get(i).getTitle());
            imgsrc.add("");
        }

        for (int i = 0; i < results2.size(); i++) {
            resultList2.add(results2.get(i).getTitle());
            imgsrc2.add("");
        }

        final Vector<OnlineRecipe> onlineResults = recipeDAO.getOnlineRecipesLastViewed(user.getId());
        final Vector<OnlineRecipe> onlineResults2 = recipeDAO.getOnlineRecipesLastCooked(user.getId());

        for (int i = 0; i < onlineResults.size(); i++) {
            resultList.add(onlineResults.get(i).getTitle());
            imgsrc.add(onlineResults.get(i).getImgUrl());
        }

        for (int i = 0; i < onlineResults2.size(); i++) {
            resultList2.add(onlineResults2.get(i).getTitle());
            imgsrc2.add(onlineResults2.get(i).getImgUrl());
        }

        adapter = new CustomListAdapter(this, (String[]) resultList.toArray(new String[0]), (String[]) imgsrc.toArray(new String[0]));
        listView.setAdapter(adapter);

        adapter2 = new CustomListAdapter(this, (String[]) resultList2.toArray(new String[0]), (String[]) imgsrc2.toArray(new String[0]));
        listView2.setAdapter(adapter2);

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

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position < results2.size()) {
                        Intent intent = new Intent(getBaseContext(), ShowRecipe.class);
                        intent.putExtra("Recipe", Long.toString( results2.get(position).getId()));
                        startActivity(intent);
                    }
                    else {
                        OnlineRecipe or = onlineResults2.get(position-results2.size());
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
