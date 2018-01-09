package com.example.claire.myrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import dao.IngredientDAO;
import dao.SessionDAO;
import model.Ingredient;
import model.User;

public class ExcludeIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exclude_ingredient);

        final IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        ingredientDAO.dropTableIngredientSelected();
        SessionDAO sessionDAO = new SessionDAO(getApplicationContext());
        final User u = sessionDAO.getUserConnected(getApplicationContext());

        Button buttonProteins = (Button) findViewById(R.id.premier);
        Button buttonVegetables = (Button) findViewById(R.id.second);
        Button buttonFibre = (Button) findViewById(R.id.trois);
        Button buttonFats = (Button) findViewById(R.id.quatre);
        Button buttonFruits = (Button) findViewById(R.id.cinq);
        Button buttonSpices = (Button) findViewById(R.id.six);
        Button buttonSugar = (Button) findViewById(R.id.sept);
        Button buttonDairy = (Button) findViewById(R.id.huit);

        buttonProteins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id_cat = ingredientDAO.getIdCategory("Proteins");
                if (ingredientDAO.isCategoryProhibited(id_cat, u.getId())) {
                    Toast.makeText(getBaseContext(), "This category is already excluded",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), ProteinsProhib.class);
                    getBaseContext().startActivity(intent);
                }
            }
        });

        buttonVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id_cat = ingredientDAO.getIdCategory("Vegetables");
                if (ingredientDAO.isCategoryProhibited(id_cat, u.getId())) {
                    Toast.makeText(getBaseContext(), "This category is already excluded",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), VegetablesProhib.class);
                    getBaseContext().startActivity(intent);
                }
            }
        });

        buttonFats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id_cat = ingredientDAO.getIdCategory("Fats");
                if (ingredientDAO.isCategoryProhibited(id_cat, u.getId())) {
                    Toast.makeText(getBaseContext(), "This category is already excluded",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getBaseContext(), FatsProhib.class);
                    getBaseContext().startActivity(intent);
                }

            }
        });

        buttonFibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FibreProhib.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FruitsProhib.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSpices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SpicesProhib.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SugarProhib.class);
                getBaseContext().startActivity(intent);
            }
        });

        buttonDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DairyProhib.class);
                getBaseContext().startActivity(intent);
            }
        });
    }
}
