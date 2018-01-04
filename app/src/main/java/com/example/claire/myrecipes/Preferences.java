package com.example.claire.myrecipes;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.IngredientDAO;
import dao.SessionDAO;
import dao.UserDAO;
import model.Ingredient;
import model.User;

public class Preferences extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        Button buttonExcludeIngredient = (Button)findViewById(R.id.button2);
        buttonExcludeIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExcludeIngredient.class);
                startActivity(intent);
            }
        });

        Button buttonExcludeCategory = (Button)findViewById(R.id.button4);
        buttonExcludeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExcludeCategory.class);
                startActivity(intent);
            }
        });


        SessionDAO sessionDAO = new SessionDAO(getApplicationContext());
        User u = sessionDAO.getUserConnected(getApplicationContext());
        IngredientDAO ingredientDAO = new IngredientDAO(getApplicationContext());
        ArrayList<Ingredient> ingredientList = ingredientDAO.getListIngredientsProhibited(u.getId());
        loadList(ingredientList);





    }


    private void loadList(ArrayList<Ingredient> list) {
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(new MyListAdapter(this,R.layout.my_ingredient_excluded_row,list) {

            @Override
            public void input(final Object input, View view, int position) {
                TextView rowTextView = (TextView)view.findViewById(R.id.rowTextTitle);
                rowTextView.setText(((Ingredient) input).getName());

                Button buttonDelete = (Button)view.findViewById(R.id.deleteButton);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                        Ingredient i = (Ingredient)input;
                        if (i.getId() < 0) {
                            // it is a category
                            ingredientDAO.deleteCategoryProhibited(i.getName());
                            Toast.makeText(getBaseContext(), "category deleted with success",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ingredientDAO.deleteIngredientProhibited(((Ingredient) input).getId());
                            Toast.makeText(getBaseContext(), "ingredient deleted with success",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(getBaseContext(), Preferences.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }
}
