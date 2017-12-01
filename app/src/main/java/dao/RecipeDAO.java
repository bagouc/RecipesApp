package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import model.Ingredient;
import model.Recipe;
import model.User;
import model.UserSearch;

//import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

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

    public static final String SEARCH_KEY = "id";
    public static final String SEARCH_INGREDIENTS = "ingredients";
    public static final String SEARCH_KEYWORDS = "keywords";
    public static final String SEARCH_USER = "idUser";

    public static final String SEARCH_TABLE_NAME = "Searches";
    public static final String SEARCH_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + SEARCH_TABLE_NAME + " (" +
            SEARCH_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SEARCH_USER + " INTEGER, " +
            SEARCH_KEYWORDS + " TEXT, " +
            SEARCH_INGREDIENTS + " TEXT)";

    public static final String RECIPE_SELECTED_KEY = "id_rec";
    public static final String RECIPE_SELECTED_USER = "id_user";
    public static final String RECIPE_SELECTED_LASTVIEWED = "lastviewed";
    public static final String RECIPE_SELECTED_LASTCOOKED = "lastcooked";
    public static final String RECIPE_SELECTED_FAVORITE = "fav";

    public static final String RECIPE_SELECTED_TABLE_NAME = "Recipes_selected";
    public static final String RECIPE_SELECTED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + RECIPE_SELECTED_TABLE_NAME + "(" +
            RECIPE_SELECTED_KEY + " INTEGER, " +
            RECIPE_SELECTED_USER + " INTEGER, " +
            RECIPE_SELECTED_FAVORITE + " INTEGER, " +
            RECIPE_SELECTED_LASTVIEWED + " INTEGER, " +
            RECIPE_SELECTED_LASTCOOKED + " INTEGER)";


    public RecipeDAO (Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(RECIPE_TABLE_CREATE);
            this.mDb.execSQL(SEARCH_TABLE_CREATE);
            this.mDb.execSQL(RECIPE_SELECTED_TABLE_CREATE);
            Log.v("SUCCESS!", "");
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
    public void dropTable2() {
        mDb.execSQL("DROP TABLE IF EXISTS " + RECIPE_SELECTED_TABLE_NAME);
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

    public Recipe getRecipeByID(long id) {
        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", " + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS + " " +
                    " from " + RECIPE_TABLE_NAME + " where " + RECIPE_KEY + " = ?", new String[]{String.valueOf(id)});
            c.moveToFirst();
            JSONArray jarr = new JSONArray(c.getString(3));
            Vector<String> ingredients = new Vector<String>();
            Vector<Ingredient> ingredientList = new Vector<Ingredient>();
            for (int i = 0; i < jarr.length(); i++) {
                ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
            }
            Recipe r = new Recipe(c.getLong(0), c.getLong(1), c.getString(2),ingredientList, c.getString(4));
            return r;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("getRecipe Error",chaine);
        }
        return null;
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
            Log.v("searchError: ",chaine);
        }
        return results;
    }

    public Vector<Recipe> getRecipesLastViewed(long userId) {

        Vector<Recipe> results = new Vector<Recipe>();

        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", "
                    + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS +
                    ", " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + ", " + RECIPE_SELECTED_LASTVIEWED +
                    " from " + RECIPE_TABLE_NAME + " inner join " + RECIPE_SELECTED_TABLE_NAME +
                    " on " + RECIPE_TABLE_NAME + "." + RECIPE_KEY + " = " + RECIPE_SELECTED_TABLE_NAME + "." + RECIPE_SELECTED_KEY +
                    " where " + RECIPE_SELECTED_USER + " = ?", new String[]{String.valueOf(userId)});

            while (c.moveToNext()) {
                //only add recipe if lastViewed is not default value (0)
                if (c.getLong(7) != 0) {
                    JSONArray jarr = new JSONArray(c.getString(3));
                    Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                    for (int i = 0; i < jarr.length(); i++) {
                        ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                    }
                    Recipe r = new Recipe(c.getInt(0), c.getInt(1), c.getString(2), ingredientList, c.getString(4));
                    r.setLastViewed(c.getLong(7));
                    results.add(r);
                }
            }
        } catch (Exception e) {
            Log.v("searchError: ", e.getMessage());
        }
        return results;
    }

    public Vector<Recipe> getRecipesLastCooked(long userId) {

        Vector<Recipe> results = new Vector<Recipe>();

        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", "
                            + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS +
                            ", " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + ", " + RECIPE_SELECTED_LASTCOOKED +
                            " from " + RECIPE_TABLE_NAME + " inner join " + RECIPE_SELECTED_TABLE_NAME +
                            " on " + RECIPE_TABLE_NAME + "." + RECIPE_KEY + " = " + RECIPE_SELECTED_TABLE_NAME + "." + RECIPE_SELECTED_KEY +
                            " where " + RECIPE_SELECTED_USER + " = ?",
                    new String[]{Long.toString(userId)});

            while (c.moveToNext()) {
                //only add recipe if lastCooked is not default value (0)
                if (c.getLong(7) != 0) {
                    JSONArray jarr = new JSONArray(c.getString(3));
                    Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                    for (int i = 0; i < jarr.length(); i++) {
                        ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                    }
                    Recipe r = new Recipe(c.getInt(0), c.getInt(1), c.getString(2), ingredientList, c.getString(4));
                    r.setLastCooked(c.getLong(7));
                    results.add(r);
                }
            }
        } catch (Exception e) {
            Log.v("searchError: ", e.getMessage());
        }
        return results;
    }

    public Vector<Recipe> getFavorites(long userId) {

        Vector<Recipe> results = new Vector<Recipe>();

        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", "
                            + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS +
                            ", " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + ", " + RECIPE_SELECTED_FAVORITE +
                            " from " + RECIPE_TABLE_NAME + " inner join " + RECIPE_SELECTED_TABLE_NAME +
                            " on " + RECIPE_TABLE_NAME + "." + RECIPE_KEY + " = " + RECIPE_SELECTED_TABLE_NAME + "." + RECIPE_SELECTED_KEY +
                            " where " + RECIPE_SELECTED_USER + " = ? AND " + RECIPE_SELECTED_FAVORITE + " = ?",
                    new String[]{Long.toString(userId), "1"});

            while (c.moveToNext()) {
                    JSONArray jarr = new JSONArray(c.getString(3));
                    Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                    for (int i = 0; i < jarr.length(); i++) {
                        ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                    }
                    Recipe r = new Recipe(c.getInt(0), c.getInt(1), c.getString(2), ingredientList, c.getString(4));
                    r.setLastCooked(c.getLong(7));
                    results.add(r);
            }
        } catch (Exception e) {
            Log.v("searchError: ", e.getMessage());
        }
        return results;
    }

    public Vector<Recipe> getSuggestions(long userId) {

        Vector<Recipe> results = new Vector<Recipe>();

        int numberOfSuggestions = 50;

        try {
            Cursor c = mDb.rawQuery("select " + RECIPE_KEY + ", " + RECIPE_USER + ", "
                            + RECIPE_TITLE + ", " + RECIPE_INGREDIENTS + ", " + RECIPE_INSTRUCTIONS + " from " + RECIPE_TABLE_NAME,
                    new String[]{});

            while (c.moveToNext()) {
                Cursor cc = mDb.rawQuery("select " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + ", " + RECIPE_SELECTED_LASTCOOKED + " FROM " +
                        RECIPE_SELECTED_TABLE_NAME + " WHERE " + RECIPE_SELECTED_USER + " = ? AND " + RECIPE_SELECTED_KEY + " = ?", new String[]{Long.toString(userId), String.valueOf(c.getInt(0))});
                if (cc.moveToNext())
                    if (cc.getLong(2) != 0 && new Date().getTime() - cc.getLong(2) < TimeUnit.DAYS.toMillis(5))
                        continue;
                // add all recipes not recently cooked
                JSONArray jarr = new JSONArray(c.getString(3));
                Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                for (int i = 0; i < jarr.length(); i++) {
                    ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                }
                Recipe r = new Recipe(c.getInt(0), c.getInt(1), c.getString(2), ingredientList, c.getString(4));
                results.add(r);
            }
            //randomly remove recipes until count of results is not over desired count of suggestions
            Random rand = new Random(new Date().getTime());
            while (results.size() > numberOfSuggestions) {
                results.remove(rand.nextInt(results.size()));
            }
        } catch (Exception e) {
            Log.v("searchError: ", e.getMessage());
        }
        return results;
    }

    public void addSearch(UserSearch s) {
        ContentValues value = new ContentValues();
        value.put(SEARCH_KEYWORDS, s.getKeywords());
        value.put(SEARCH_USER, s.getUserId());
        value.put(SEARCH_INGREDIENTS, ingredientListToJSON(s.getIngredients()).toString());
        try {
            mDb.insert(SEARCH_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError search",chaine);
        }
    }

    public Vector<UserSearch> getLastSearches(long userId) {

        Vector<UserSearch> results = new Vector<UserSearch>();

        try {
            Cursor c = mDb.rawQuery("select " + SEARCH_KEYWORDS + ", " + SEARCH_INGREDIENTS + ", "
                    + SEARCH_USER  +
                    " from " + SEARCH_TABLE_NAME + " where " + SEARCH_USER + " = ?", new String[]{Long.toString(userId)});

            //add those recipes to result-list that contain wanted and do not contain forbidden ingredients
            while (c.moveToNext()) {
                JSONArray jarr = new JSONArray(c.getString(1));
                Vector<Ingredient> ingredientList = new Vector<Ingredient>();
                for (int i = 0; i < jarr.length(); i++) {
                    ingredientList.add(new Ingredient(jarr.getJSONObject(i)));
                }
                UserSearch s = new UserSearch(c.getString(0), ingredientList);
                results.add(s);
            }
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("searchError: ",chaine);
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

    public String setViewed(long recId, long userId) {

        String debug = "";
        //check if recipe already in selected recipe database
        Cursor c = mDb.rawQuery("select " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + " FROM " +
                RECIPE_SELECTED_TABLE_NAME + " WHERE " + RECIPE_SELECTED_USER + " = ? AND " + RECIPE_SELECTED_KEY + " = ?", new String[]{Long.toString(userId), Long.toString(recId)});
        ContentValues value = new ContentValues();
        value.put(RECIPE_SELECTED_LASTVIEWED, new Date().getTime());
        if (c.moveToNext()) {
            mDb.update(RECIPE_SELECTED_TABLE_NAME, value, RECIPE_SELECTED_KEY + " = ? AND " + RECIPE_SELECTED_USER + " = ?", new String[] {Long.toString(recId), Long.toString(userId)});
        }
        else {
            value.put(RECIPE_SELECTED_USER, userId);
            value.put(RECIPE_SELECTED_KEY, recId);
            value.put(RECIPE_SELECTED_FAVORITE, 0);
            value.put(RECIPE_SELECTED_LASTCOOKED, 0);
            mDb.insert(RECIPE_SELECTED_TABLE_NAME, null, value);
        }
        return debug;
    }

    public void setCooked(long recId, long userId) {

        Cursor c = mDb.rawQuery("select " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + " FROM " +
                RECIPE_SELECTED_TABLE_NAME + " WHERE " + RECIPE_SELECTED_USER + " = ? AND " + RECIPE_SELECTED_KEY + " = ?", new String[]{Long.toString(userId), Long.toString(recId)});
        ContentValues value = new ContentValues();
        value.put(RECIPE_SELECTED_LASTCOOKED, new Date().getTime());
        if (c.moveToNext()) {
            mDb.update(RECIPE_SELECTED_TABLE_NAME, value, RECIPE_SELECTED_KEY + " = ? AND " + RECIPE_SELECTED_USER + " = ?", new String[] {Long.toString(recId), Long.toString(userId)});
        }
        else {
            value.put(RECIPE_SELECTED_USER, userId);
            value.put(RECIPE_SELECTED_KEY, recId);
            value.put(RECIPE_SELECTED_FAVORITE, 0);
            value.put(RECIPE_SELECTED_LASTVIEWED, 0);
            mDb.insert(RECIPE_SELECTED_TABLE_NAME, null, value);
        }
    }

    public void setFavorite(long recId, long userId, boolean fav) {
        Cursor c = mDb.rawQuery("select " + RECIPE_SELECTED_KEY + ", " + RECIPE_SELECTED_USER + " FROM " +
                RECIPE_SELECTED_TABLE_NAME + " WHERE " + RECIPE_SELECTED_USER + " = ? AND " + RECIPE_SELECTED_KEY + " = ?", new String[]{Long.toString(userId), Long.toString(recId)});
        ContentValues value = new ContentValues();
        int favorite = fav ? 1 : 0;
        value.put(RECIPE_SELECTED_FAVORITE, favorite);
        if (c.moveToNext()) {
            mDb.update(RECIPE_SELECTED_TABLE_NAME, value, RECIPE_SELECTED_KEY + " = ? AND " + RECIPE_SELECTED_USER + " = ?", new String[] {Long.toString(recId), Long.toString(userId)});
        }
        else {
            value.put(RECIPE_SELECTED_USER, userId);
            value.put(RECIPE_SELECTED_KEY, recId);
            value.put(RECIPE_SELECTED_LASTCOOKED, 0);
            value.put(RECIPE_SELECTED_LASTVIEWED, 0);
            mDb.insert(RECIPE_SELECTED_TABLE_NAME, null, value);
        }
    }
}
