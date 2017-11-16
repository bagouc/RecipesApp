package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

import model.Ingredient;
import model.Recipe;

/**
 * Created by Matthias on 12/11/2017.
 */

public class RecipeDAO extends DAOBase {
    public static final String RECIPE_KEY = "id";
    public static final String RECIPE_USER = "idUser";
    public static final String RECIPE_TITLE = "title";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_INSTRUCTIONS = "instructions";

    public static final String RECIPE_TABLE_NAME = "Recipes";
    public static final String RECIPE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + RECIPE_TABLE_NAME + " (" +
            RECIPE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            RECIPE_TITLE + " TEXT, " +
            RECIPE_USER + " INTEGER, " +
            RECIPE_INGREDIENTS + " TEXT, " +
            RECIPE_INSTRUCTIONS + " TEXT)";

    public RecipeDAO (Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(RECIPE_TABLE_CREATE);
        }
        catch (Exception e) {
            Log.v("init(): ", e.getMessage());
        }
    }

    public void add(Recipe r) {
        ContentValues value = new ContentValues();
        value.put(RECIPE_TITLE, r.getTitle());
        value.put(RECIPE_USER, r.getIdUser());
        value.put(RECIPE_INGREDIENTS, ingredientListToJSON(r.getIngredients()).toString());
        value.put(RECIPE_INSTRUCTIONS, r.getInstructions());
        try {
            mDb.insert(RECIPE_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }
    }

    public void dropTable() {
        mDb.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE_NAME);
    }

    public void delete(long id) {
        mDb.delete(RECIPE_TABLE_NAME, RECIPE_KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(Recipe r) {
        ContentValues value = new ContentValues();
        value.put(RECIPE_TITLE, r.getTitle());
        value.put(RECIPE_USER, r.getIdUser());
        value.put(RECIPE_INGREDIENTS, ingredientListToJSON(r.getIngredients()).toString());
        value.put(RECIPE_INSTRUCTIONS, r.getInstructions());
        mDb.update(RECIPE_TABLE_NAME, value, RECIPE_KEY + " = ?", new String[] {String.valueOf(r.getId())});
    }

    public Vector<Recipe> searchRecipes(Vector<Ingredient> wanted, Vector<Ingredient> forbidden) {

        Vector<Recipe> results = new Vector<Recipe>();

        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", "
                    + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS +
                    " from " + RECIPE_TABLE_NAME, new String[]{});

            //add those recipes to result-list that contain wanted and do not contain forbidden ingredients
            while (c.moveToNext()) {
                JSONArray jarr = new JSONArray(c.getString(3));
                Vector<String> ingredients = new Vector<String>();
                Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                boolean b = true;
                for (int i = 0; i < jarr.length(); i++) {
                    ingredients.add(jarr.getJSONObject(i).getString("name"));
                    ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                }
                for (int i = 0; i < wanted.size(); i++) {
                    b &= ingredients.contains(wanted.get(i).getName());
                }
                for (int i = 0; i < forbidden.size(); i++) {
                    b &= !ingredients.contains(forbidden.get(i).getName());
                }
                if (b) {
                    Recipe r = new Recipe(c.getInt(0), c.getInt(1), c.getString(2), ingredientList, c.getString(4));
                    results.add(r);
                }
            }
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return results;
    }

    public JSONArray ingredientListToJSON(Vector<Ingredient> ingredients) {
        JSONArray JSONList = new JSONArray();
        for(int i = 0; i < ingredients.size(); i++) {
            JSONList.put(ingredients.get(i).getJSONObject());
        }
        return JSONList;
    }
}
