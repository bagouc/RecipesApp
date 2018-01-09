package model;

import org.json.JSONArray;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Matthias on 12/11/2017.
 */

public class OnlineRecipe {
    private String url;
    private String title;
    private Vector<Ingredient> ingredients;
    private String imgUrl;
    long lastViewed;
    long lastCooked;

    public OnlineRecipe(String url, String title, int lastViewed) {
        this.url = url;
        this.title = title;
        this.lastViewed = lastViewed;
        lastCooked = 0;
    }

    public OnlineRecipe(String url, String title) {
        this.url = url;
        this.title = title;
        lastViewed = 0;
        lastCooked = 0;
    }

    public OnlineRecipe(String url, String title, Vector<Ingredient> ingredients, String imgUrl) {
        this.url = url;
        this.title = title;
        this.ingredients = ingredients;
        this.imgUrl = imgUrl;
        lastViewed = 0;
        lastCooked = 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

        s += "(online) " + title + "\ningredients: ";
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
