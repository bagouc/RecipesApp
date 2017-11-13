package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.UserDAO;
import model.Ingredient;
import model.Recipe;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button buttonSignIn = (Button)findViewById(R.id.signIn);
        Button buttonLogIn = (Button)findViewById(R.id.logIn);

        TextView textView = (TextView) findViewById(R.id.editText);


        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
        recipeDAO.dropTable();
        recipeDAO = new RecipeDAO(getBaseContext());
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());


        Vector<Ingredient> ingList = new Vector<Ingredient>();
        ingList.add(new Ingredient("sugar", 0));
        ingList.add(new Ingredient("eggs", 0));
        ingList.add(new Ingredient("milk", 0));
        ingList.add(new Ingredient("honey", 0));
        ingList.add(new Ingredient("chocolate", 0));

        Vector<Ingredient> wanted = new Vector<Ingredient>();
        wanted.add(new Ingredient("milk",0));
        Vector<Ingredient> forbidden = new Vector<Ingredient>();
        forbidden.add(new Ingredient("eggs", 0));

        recipeDAO.add(new Recipe(0,0,"Recipe1", ingList, ""));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe2", ingList, ""));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe3", ingList, ""));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe3", ingList, ""));
        ingList.remove(0);


        Vector<Recipe> results = recipeDAO.searchRecipes(wanted, forbidden);

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

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogIn = new Intent(getBaseContext(), LogIn.class);
                getBaseContext().startActivity(intentLogIn);
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignIn = new Intent(getBaseContext(), SignIn.class);
                getBaseContext().startActivity(intentSignIn);
            }
        });
    }
}
