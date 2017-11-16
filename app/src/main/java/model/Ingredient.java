package model;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Matthias on 12/11/2017.
 */

public class Ingredient {
    private long id;
    private long idUser;
    private String name;
    private int category;

    public Ingredient(long id, long idUser, String name, int category) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.category = category;
    }

    public Ingredient(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public Ingredient(JSONObject j) {
        try {
            name = j.getString("name");
            category = j.getInt("category");
        } catch (Exception e) {
            Log.v("IngredientConstr: ", e.getMessage());
        }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public JSONObject getJSONObject() {
        JSONObject jo = new JSONObject();

        try {
            jo.put("name", name);
            jo.put("category", category);
        } catch (Exception e) {
            Log.v("getJSONObject: ", e.getMessage());
        }
        return jo;
    }
}
