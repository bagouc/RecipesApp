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

        ImageButton button = (ImageButton) findViewById(R.id.homeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PersonalSpace.class);
                startActivity(intent);
            }
        });

        initSpinners();

        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
                SessionDAO sessionDAO = new SessionDAO(getBaseContext());
                User u = sessionDAO.getUserConnected(getBaseContext());

                Spinner proteinSpinner = (Spinner) findViewById(R.id.spinner);
                String protein = proteinSpinner.getSelectedItem().toString();
                Spinner fatsSpinner = (Spinner) findViewById(R.id.spinner2);
                String fats = fatsSpinner.getSelectedItem().toString();
                Spinner fibreSpinner = (Spinner) findViewById(R.id.spinner3);
                String fibre = fibreSpinner.getSelectedItem().toString();
                Spinner dairySpinner = (Spinner) findViewById(R.id.spinner1);
                String dairy = dairySpinner.getSelectedItem().toString();
                Spinner fruitsSpinner = (Spinner) findViewById(R.id.spinner6);
                String fruits = fruitsSpinner.getSelectedItem().toString();
                Spinner vegetablesSpinner = (Spinner) findViewById(R.id.spinner4);
                String vegetables = vegetablesSpinner.getSelectedItem().toString();
                Spinner spicesSpinner = (Spinner) findViewById(R.id.spinner5);
                String spices = spicesSpinner.getSelectedItem().toString();
                Spinner sugarSpinner = (Spinner) findViewById(R.id.spinner7);
                String sugar = sugarSpinner.getSelectedItem().toString();

                Ingredient i = ingredientDAO.getIngredient(protein);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(fats);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(fibre);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(fruits);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(vegetables);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(sugar);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(spices);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }
                i = null;
                i = ingredientDAO.getIngredient(dairy);
                if (i != null) {
                    ingredientDAO.addIngredientProhibited(i, u.getId());
                }

                Intent intent = new Intent(getBaseContext(), Preferences.class);
                startActivity(intent);
            }
        });
    }

    private void initSpinners() {
        initSpinnerProteins();
        initSpinnerSugar();
        initSpinnerFats();
        initSpinnerFibre();
        initSpinnerFruits();
        initSpinnerVegetables();
        initSpinnerSpices();
        initSpinnerDairy();
    }

    private void initSpinnerDairy() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Dairy"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerSpices() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner5);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Spices"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerVegetables() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner4);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Vegetables"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerFruits() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner6);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Fruits"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerFibre() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner3);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Fibre"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerFats() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Fats"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerProteins() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Proteins"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initSpinnerSugar() {
        IngredientDAO ingredientDAO = new IngredientDAO(getBaseContext());
        SessionDAO sessionDAO = new SessionDAO(getBaseContext());
        User u = sessionDAO.getUserConnected(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner7);
        ArrayList<String> options = ingredientDAO.getIngredientsAvailable(ingredientDAO.getIdCategory("Sugar"), u.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
