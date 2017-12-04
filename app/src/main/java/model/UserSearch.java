package model;

import java.util.Vector;

/**
 * Created by Matthias on 19/11/2017.
 */

public class UserSearch {

    private long id;
    private long userId;
    private String keywords;
    private Vector<Ingredient> ingredients;

    public UserSearch(long userId, String keywords, Vector<Ingredient> ingredients) {
        this.userId = userId;
        this.keywords = keywords;
        this.ingredients = ingredients;
    }

    public UserSearch(String keywords, Vector<Ingredient> ingredients) {
        this.keywords = keywords;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Vector<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Vector<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String toString() {
        String s = "Keywords: " + keywords + "\nIngredients: ";

        for (int i = 0; i < ingredients.size() - 1; i++) {
            s += ingredients.get(i).getName() + ", ";
        }
        if (ingredients.size() > 0)
            s += ingredients.get(ingredients.size()-1).getName();
        return s;
    }
}
