package model;

import org.json.JSONArray;

import java.util.Date;
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
    long lastViewed;
    long lastCooked;

    public Recipe(long id, long idUser, String title, Vector<Ingredient> ingredients, String instructions, int lastViewed) {
        this.id = id;
        this.idUser = idUser;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.title = title;
        this.lastViewed = lastViewed;
    }

    public Recipe(long id, long idUser, String title, Vector<Ingredient> ingredients, String instructions) {
        this.id = id;
        this.idUser = idUser;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.title = title;
        lastViewed = 0;
        lastCooked = 0;
    }

    public Recipe(String msg) {
        id = 0;
        idUser = 0;
        title = "Messeage";
        ingredients = new Vector<Ingredient>();
        instructions = msg;
        lastViewed = 0;
        lastCooked = 0;
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

    public long getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(long lastViewed) {
        this.lastViewed = lastViewed;
    }

    public long getLastCooked() {
        return lastCooked;
    }

    public void setLastCooked(long lastCooked) {
        this.lastCooked = lastCooked;
    }

    public String toString() {
        String s = "";

        s += title + "\ningredients: ";
        for (int i = 0; i < ingredients.size(); i++) {
            s += ingredients.get(i).getName() + ", ";
        }
        s += "\n";
        if (lastViewed != 0)
            s += "Last Viewed: " + new Date(lastViewed).toString() + "\n";
        if (lastCooked != 0)
            s += "Last cooked on " + new Date(lastCooked).toString() + "\n";
        return s;
    }

    public String getIngredientsAsString() {
        String s = "";
        for (int i = 0; i < ingredients.size(); i++) {
            s += ingredients.get(i).getName() + "\n";
        }
        return s;
    }

}
