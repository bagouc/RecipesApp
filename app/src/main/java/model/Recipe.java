package model;

import org.json.JSONArray;

import java.util.Vector;

/**
 * Created by Matthias on 12/11/2017.
 */

public class Recipe {
    private long id;
    private long idUser;
    private String title;
    private Vector<Ingredient> ingredients;
    private String instructions;

    public Recipe(long id, long idUser, String title, Vector<Ingredient> ingredients, String instructions) {
        this.id = id;
        this.idUser = idUser;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Vector<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Vector<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    //only for testing purpose
    public String toString() {
        String s = "";

        s += "title: " + title + "\ningredients: ";
        for (int i = 0; i < ingredients.size(); i++) {
            s += ingredients.get(i).getName() + ", ";
        }
        s += "\n";

        return s;
    }

}
