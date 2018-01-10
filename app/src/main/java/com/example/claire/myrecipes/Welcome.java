package com.example.claire.myrecipes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import dao.IngredientDAO;
import dao.RecipeDAO;
import dao.SessionDAO;
import dao.UserDAO;
import model.Ingredient;
import model.Recipe;
import model.Session;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);
        UserDAO userDAO = new UserDAO(getApplicationContext());
       // userDAO.dropTable();
        final Button buttonSignIn = (Button)findViewById(R.id.signIn);
        Button buttonLogIn = (Button)findViewById(R.id.logIn);

        RecipeDAO recipeDAO = new RecipeDAO(getBaseContext());
       // recipeDAO.dropTable();
        //recipeDAO = new RecipeDAO(getBaseContext());
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        ingredientDAO.dropTableIngredientSelected();
        ingredientDAO.dropTableIngredients();
        ingredientDAO.dropTableCategories();

        //ingredientDAO.dropTableCategoriesProhibited();
        ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        sessionDAO.dropTable();
        sessionDAO = new SessionDAO(getBaseContext());

        ingredientDAO.addCategory("Proteins"); //1
        ingredientDAO.addCategory("Vegetables"); //2
        ingredientDAO.addCategory("Fats"); //3
        ingredientDAO.addCategory("Fibre"); //4
        ingredientDAO.addCategory("Dairy"); //5
        ingredientDAO.addCategory("Fruits"); //6
        ingredientDAO.addCategory("Spices"); //7
        ingredientDAO.addCategory("Sugar"); //8
        long id_proteins = ingredientDAO.getIdCategory("Proteins");
        long id_vegetables = ingredientDAO.getIdCategory("Vegetables");
        long id_fats = ingredientDAO.getIdCategory("Fats");
        long id_fibre = ingredientDAO.getIdCategory("Fibre");
        long id_dairy = ingredientDAO.getIdCategory("Dairy");
        long id_fruits = ingredientDAO.getIdCategory("Fruits");
        long id_sugar = ingredientDAO.getIdCategory("Sugar");
        long id_spices = ingredientDAO.getIdCategory("Spices");

        Ingredient sugar = new Ingredient("Sugar", id_sugar, -1); //1
        ingredientDAO.add(sugar);
        Ingredient eggs = new Ingredient("Eggs", id_proteins, -1); //2
        ingredientDAO.add(eggs);
        Ingredient milk = new Ingredient("Milk", id_dairy,-1); //3
        ingredientDAO.add(milk);
        Ingredient honey = new Ingredient("Honey", id_sugar, -1); //4
        ingredientDAO.add(honey);
        Ingredient chocolate = new Ingredient("Chocolate", id_sugar, -1); //5
        ingredientDAO.add(chocolate);
        Ingredient huile = new Ingredient("Oliv oil", id_fats, -1);
        ingredientDAO.add(huile);
        Ingredient v1 = new Ingredient("Cucumber", id_vegetables, -1); //1
        ingredientDAO.add(v1);
        Ingredient v2 = new Ingredient("Tomato", id_vegetables, -1); //1
        ingredientDAO.add(v2);
        Ingredient v3 = new Ingredient("Broccoli", id_vegetables, -1); //1
        ingredientDAO.add(v3);
        Ingredient v4 = new Ingredient("Celery", id_vegetables, -1); //1
        ingredientDAO.add(v4);
        Ingredient v5 = new Ingredient("Spinach", id_vegetables, -1); //1
        ingredientDAO.add(v5);
        Ingredient v6 = new Ingredient("Carrot", id_vegetables, -1); //1
        ingredientDAO.add(v6);
        Ingredient v7 = new Ingredient("Potato", id_vegetables, -1); //1
        ingredientDAO.add(v7);
        Ingredient v8 = new Ingredient("Eggplant", id_vegetables, -1); //1
        ingredientDAO.add(v8);

        Ingredient f1 = new Ingredient("Watermelon", id_fruits, -1); //1
        ingredientDAO.add(f1);
        Ingredient f2 = new Ingredient("Pineapple", id_fruits, -1); //1
        ingredientDAO.add(f2);
        Ingredient f3 = new Ingredient("Strawberry", id_fruits, -1); //1
        ingredientDAO.add(f3);
        Ingredient f4 = new Ingredient("Raspberry", id_fruits, -1); //1
        ingredientDAO.add(f4);
        Ingredient f5 = new Ingredient("Apple", id_fruits, -1); //1
        ingredientDAO.add(f5);
        Ingredient f6 = new Ingredient("Pear", id_fruits, -1); //1
        ingredientDAO.add(f6);
        Ingredient f7 = new Ingredient("Grapes", id_fruits, -1); //1
        ingredientDAO.add(f7);
        Ingredient f8 = new Ingredient("Cherry", id_fruits, -1); //1
        ingredientDAO.add(f8);
        Ingredient f9 = new Ingredient("Orange", id_fruits, -1); //1
        ingredientDAO.add(f9);
        Ingredient f10 = new Ingredient("Lemon", id_fruits, -1); //1
        ingredientDAO.add(f10);

        Ingredient d1 = new Ingredient("Cheese", id_dairy, -1); //1
        ingredientDAO.add(d1);
        Ingredient d2 = new Ingredient("Yogurt", id_dairy, -1); //1
        ingredientDAO.add(d2);

        Ingredient fa1 = new Ingredient("Butter", id_fats, -1); //1
        ingredientDAO.add(fa1);

        Ingredient p1 = new Ingredient("Chicken", id_proteins, -1); //1
        ingredientDAO.add(p1);
        Ingredient p2 = new Ingredient("Pork", id_proteins, -1); //1
        ingredientDAO.add(p2);
        Ingredient p3 = new Ingredient("Lamb", id_proteins, -1); //1
        ingredientDAO.add(p3);
        Ingredient p4 = new Ingredient("Bacon", id_proteins, -1); //1
        ingredientDAO.add(p4);
        Ingredient p5 = new Ingredient("Peperoni", id_proteins, -1); //1
        ingredientDAO.add(p5);
        Ingredient p6 = new Ingredient("Anchovy", id_proteins, -1); //1
        ingredientDAO.add(p6);
        Ingredient p7 = new Ingredient("See bass", id_proteins, -1); //1
        ingredientDAO.add(p7);
        Ingredient p8 = new Ingredient("Codfish", id_proteins, -1); //1
        ingredientDAO.add(p8);
        Ingredient p9 = new Ingredient("Crab", id_proteins, -1); //1
        ingredientDAO.add(p9);
        Ingredient p10 = new Ingredient("Salmon", id_proteins, -1); //1
        ingredientDAO.add(p10);
        Ingredient p11 = new Ingredient("Shrimp", id_proteins, -1); //1
        ingredientDAO.add(p11);
        Ingredient p12 = new Ingredient("Squid", id_proteins, -1); //1
        ingredientDAO.add(p12);

        Ingredient fi1 = new Ingredient("Rice", id_fibre, -1); //1
        ingredientDAO.add(fi1);
        Ingredient fi2 = new Ingredient("Pasta", id_fibre, -1); //1
        ingredientDAO.add(fi2);
        Ingredient fi3 = new Ingredient("Bread", id_fibre, -1); //1
        ingredientDAO.add(fi3);
        Ingredient fi4 = new Ingredient("Quinoa", id_fibre, -1); //1
        ingredientDAO.add(fi4);
        Ingredient fi5 = new Ingredient("Oats", id_fibre, -1); //1
        ingredientDAO.add(fi5);
        Ingredient fi6 = new Ingredient("Wheat", id_fibre, -1); //1
        ingredientDAO.add(fi6);

        Ingredient su1 = new Ingredient("Caramel", id_sugar, -1); //1
        ingredientDAO.add(su1);
        Ingredient su2 = new Ingredient("Vanilla", id_sugar, -1); //1
        ingredientDAO.add(su2);

        Ingredient s1 = new Ingredient("Pepper", id_spices, -1); //1
        ingredientDAO.add(s1);
        Ingredient s2 = new Ingredient("Basil", id_spices, -1); //1
        ingredientDAO.add(s2);
        Ingredient s3 = new Ingredient("Cilantro", id_spices, -1); //1
        ingredientDAO.add(s3);
        Ingredient s4 = new Ingredient("Cinnamon", id_spices, -1); //1
        ingredientDAO.add(s4);
        Ingredient s5 = new Ingredient("Cumin", id_spices, -1); //1
        ingredientDAO.add(s5);
        Ingredient s6 = new Ingredient("Curry", id_spices, -1); //1
        ingredientDAO.add(s6);
        Ingredient s7 = new Ingredient("Thyme", id_spices, -1); //1
        ingredientDAO.add(s7);


        Vector<Ingredient> ingList = new Vector<Ingredient>();
        ingList.add(new Ingredient("sugar", 8));
        ingList.add(new Ingredient("eggs", 1));
        ingList.add(new Ingredient("milk", 5));
        ingList.add(new Ingredient("honey", 8));
        ingList.add(new Ingredient("chocolate", 8));

        Vector<Ingredient> wanted = new Vector<Ingredient>();
        wanted.add(new Ingredient("milk",0));
        Vector<Ingredient> forbidden = new Vector<Ingredient>();
        forbidden.add(new Ingredient("eggs", 0));

        String s = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
       /* recipeDAO.add(new Recipe(0,0,"Recipe1", ingList, s));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe2", ingList, s));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe3", ingList, s));
        ingList.remove(0);
        recipeDAO.add(new Recipe(0,0,"Recipe4", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe5", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe6", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe7", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe8", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe9", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe10", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe11", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe12", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe13", ingList, s));
        recipeDAO.add(new Recipe(0,0,"Recipe14", ingList, s)); */


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
