package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import dao.IngredientDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.User;

public class FatsProhib extends AppCompatActivity {
    private ListView listView;
    private long id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fats_prohib);

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

        listView = (ListView) findViewById(R.id.listView);
        long id_cat = ingredientDAO.getIdCategory("Fats");
        ArrayList<String> list = ingredientDAO.getIngredientFromCategory(id_cat);


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);

        int i = 0;
        while (i < list.size()) {
            Ingredient ingredient = ingredientDAO.getIngredient(list.get(i));
            if (ingredientDAO.isProhibited(ingredient.getId(), u.getId())) {
                listView.setItemChecked(i, true);
            }
            i++;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ing = (String) listView.getItemAtPosition(position);
                if (listView.isItemChecked(position)) {
                    // on enregistre dans les ingredients selected
                    Ingredient ingredient = ingredientDAO.getIngredient(ing);
                    ingredientDAO.addIngredientProhibited(ingredient, id_user);
                } else {
                    Ingredient ingredient = ingredientDAO.getIngredient(ing);
                    ingredientDAO.deleteIngredientProhibited(ingredient.getId());
                }
            }
        });


        Button buttonDone = (Button) findViewById(R.id.second);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Preferences.class);
                getBaseContext().startActivity(intent);
            }
        });

    }
}
