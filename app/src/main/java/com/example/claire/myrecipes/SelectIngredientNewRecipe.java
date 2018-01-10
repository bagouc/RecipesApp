package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.Recipe;
import model.User;

public class SelectIngredientNewRecipe extends AppCompatActivity {
    private ListView listView;
    private long id_user;
    private Vector<Ingredient> ingredients;
    private long id_recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ingredient_new_recipe);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        final IngredientDAO ingredientDAO = new IngredientDAO(getApplicationContext());

        SessionDAO sessionDAO = new SessionDAO(getApplicationContext());
        User u = sessionDAO.getUserConnected(getApplicationContext());
        id_user = u.getId();

        Intent intent = getIntent();
        id_recipe = intent.getExtras().getInt("id_recipe");

        listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> list = ingredientDAO.getListIngredients();

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);

        int i = 0;
        Vector<Ingredient> ingSelectedNewRecipe = ingredientDAO.getListIngredientForNewRecipe();
        while (i < list.size()) {
            Ingredient ingredient = ingredientDAO.getIngredient(list.get(i));
            if (ingSelectedNewRecipe.contains(ingredient)) {
                listView.setItemChecked(i, true);
            }
            i++;
        }

        final RecipeDAO recipeDAO = new RecipeDAO(getApplicationContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ing = (String) listView.getItemAtPosition(position);
                Recipe r = recipeDAO.getRecipeByID(id_recipe);
                Vector<Ingredient> ingred;
                if (r == null) {
                    Log.d("++++++", "r est null (censé arrivé une fois seulement)");
                    ingred = new Vector<Ingredient>();
                    r = recipeDAO.getRecipeSimplifyFromId(id_recipe);
                } else {
                    ingred = r.getIngredients();
                }

                Ingredient ingredient = ingredientDAO.getIngredient(ing);
                if (listView.isItemChecked(position)) {
                    Log.d("+++++", "on est passé dans il faut cocher");
                    ingred.add(ingredient);
                } else {

                    boolean trouve = false;
                    Enumeration<Ingredient> enum_ing = ingred.elements();
                    Ingredient ingredient1;
                    Log.d("+++nom_ing", ingredient.getName());
                    while (!trouve && enum_ing.hasMoreElements()) {
                        Log.d("++++++++", "passage boucle");
                        ingredient1 = enum_ing.nextElement();
                        Log.d("+++nom_ing1", ingredient1.getName());
                        if (ingredient1.getName().equals(ingredient.getName())) {
                            Log.d("++++++++", "On trouve bien l'ingredient a priori");
                            trouve = true;
                            ingred.remove(ingredient1);
                        }
                    }


                        /*String nom_ing = ingred.get()
                        boolean b = ingred.remove(ingredient);
                        String mess = "";
                        if (!b) {
                            mess = "la  suppresion ds la liste d'ing ne s'est pas faite";
                        }
                        Log.d("+++++++Bool", mess);*/

                }
                recipeDAO.upDateIngredientsRecipe(id_recipe, ingred);
            }
        });


        Button buttonDone = (Button) findViewById(R.id.buttonSave);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyRecipes.class);
                getBaseContext().startActivity(intent);
            }
        });
    }
}
