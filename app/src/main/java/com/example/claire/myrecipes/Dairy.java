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
import android.widget.Toast;

import java.util.ArrayList;

import dao.IngredientDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.User;

public class Dairy extends AppCompatActivity {
    private ListView listView;
    private long id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

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
        long id_cat = ingredientDAO.getIdCategory("Dairy");
        ArrayList<String> list = ingredientDAO.getIngredientFromCategory(id_cat);


        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(adapter);

        int i = 0;
        while (i < list.size()) {
            Ingredient ingredient = ingredientDAO.getIngredient(list.get(i));
            if (ingredientDAO.isSelected(ingredient.getId(), u.getId())) {
                listView.setItemChecked(i, true);
            }
            i++;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ing = (String)listView.getItemAtPosition(position);
                if (listView.isItemChecked(position)) {
                    // on enregistre dans les ingredients selected
                    Ingredient ingredient = ingredientDAO.getIngredient(ing);
                    ingredientDAO.addIngredientSelected(ingredient.getId(), id_user);

                    // verication this is not a forbiden ingredient
                    ArrayList<String> nameIng = ingredientDAO.transferToArrayList(ingredientDAO.getListIngredientsProhibited(id_user));
                    if (nameIng.contains(ingredient.getName())) {
                        Toast.makeText(getBaseContext(), "Be careful! You have selected an ingredient you had previously excluded in the Settings. The ingredient is kept for this research",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Ingredient ingredient = ingredientDAO.getIngredient(ing);
                    ingredientDAO.deleteIngredientSelected(ingredient.getId(), id_user);
                }
            }
        });


        Button buttonDone = (Button)findViewById(R.id.second);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ResultsRecipesIngredients.class);
                getBaseContext().startActivity(intent);
            }
        });

    }
}
