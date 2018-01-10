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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

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
import model.UserSearch;

public class ResultsRecipesIngredients extends AppCompatActivity {

    ListView listView;
    ArrayList<String> resultList;
    ArrayList<String> imgurl;

    CustomListAdapter adapter;
    Vector<OnlineRecipe> resultsAPI;
    Vector<Ingredient> ing_list_wanted;
    Vector<Ingredient> forbidden;
    Vector<Recipe> results;
    TextView searchView;

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

        String APP_ID = "fbb04b83";
        String APP_KEY = "cace497a10f7a920e8b80d86713c907c";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String searchTerm = "";

        resultList = new ArrayList<String>();
        imgurl = new ArrayList<String>();

        listView  = (ListView) findViewById(R.id.resultList);
        searchView = (TextView) findViewById(R.id.searchView);


        // We get the ingredients selected by the user and create a list
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User user = sessionDAO.getUserConnected(getBaseContext());

        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        ing_list_wanted =  ingredientDAO.getListIngredientsSelected(user.getId());

        if(ing_list_wanted.size() > 0)
            searchTerm = ing_list_wanted.get(0).getName();
        String url = "https://api.edamam.com/search?q=" + searchTerm + "&app_id=" + APP_ID + "&app_key=" + APP_KEY + "&from=0&to=20&calories=gte%20591,%20lte%20722&health=alcohol-free";


        resultsAPI = new Vector<>();

        forbidden = ingredientDAO.getVectorListIngredientProhibited(user.getId());

        //Display search criteria
        String s = "";
        s += "Ingredients: ";
        for (int i = 0; i < ing_list_wanted.size() - 1; i++) {
            s += ing_list_wanted.get(i).getName() + ", ";
        }
        if (ing_list_wanted.size() > 0)
            s += ing_list_wanted.get(ing_list_wanted.size()-1).getName();
        searchView.setText(s);

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        results = recipeDAO.searchRecipes(ing_list_wanted, forbidden);

        recipeDAO.addSearch(new UserSearch(user.getId(), "", ing_list_wanted));

        for (int i = 0; i < results.size(); i++) {
            resultList.add(results.get(i).getTitle());
            imgurl.add("");
        }

        try {

            String[] resultListArray = (String[]) resultList.toArray(new String[0]);
            String[] imgurlArray = (String[]) imgurl.toArray(new String[0]);

            adapter = new CustomListAdapter(this, resultListArray, imgurlArray);
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
    } catch (Exception e) {
        searchView.setText("Err0: " + e.getMessage());
    }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipes = response.getJSONArray("hits");
                            JSONObject recipeJSON;
                            for (int i = 0; i < recipes.length(); i++) {
                                recipeJSON = recipes.getJSONObject(i).getJSONObject("recipe");
                                Vector<Ingredient> ing = new Vector<>();
                                JSONArray ings = recipeJSON.getJSONArray("ingredientLines");
                                for (int j = 0; j < ings.length(); j++) {
                                    ing.add(new Ingredient(ings.getString(j), 0));
                                }
                                if (checkConditions(ing, ing_list_wanted, forbidden)) {
                                    resultsAPI.add(new OnlineRecipe(recipeJSON.getString("url"), recipeJSON.getString("label"), ing, recipeJSON.getString("image")));
                                    imgurl.add(recipeJSON.getString("image"));
                                }
                            }
                            fillList();
                        } catch (Exception e) {
                            Log.v("ERROR: ", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    public boolean checkConditions(Vector<Ingredient> ingredients, Vector<Ingredient> wanted, Vector<Ingredient> forbidden) {

        Boolean b = true;

        for (int j = 0; j < ingredients.size(); j++) {
            String s = ingredients.get(j).getName();
            for (int k = 0; k < wanted.size(); k++) {
                b |= s.contains(wanted.get(k).getName());
            }
            for (int k = 0; k < forbidden.size(); k++) {
                b &= !(s.contains(forbidden.get(k).getName()));
            }
        }
        return b;
    }

    public void fillList() {
        for (int i = 0; i < resultsAPI.size(); i++) {
            resultList.add(resultsAPI.get(i).getTitle());
        }
        //imgurl.addAll(imgurl2);
        String[] resultListArray = (String[]) resultList.toArray(new String[0]);
        String[] imgurlArray = (String[]) imgurl.toArray(new String[0]);
        try {
            adapter = new CustomListAdapter(this, resultListArray, imgurlArray);
            listView.setAdapter(adapter);

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
                            OnlineRecipe or = resultsAPI.get(position-results.size());
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

        } catch (Exception e){ searchView.setText("Err: " + e.getMessage());}
    }
}
