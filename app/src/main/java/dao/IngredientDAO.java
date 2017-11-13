package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import model.Ingredient;
import model.User;

/**
 * Created by Matthias on 12/11/2017.
 */

public class IngredientDAO extends DAOBase {

    public static final String INGREDIENT_KEY = "id";
    public static final String INGREDIENT_NAME = "name";
    public static final String INGREDIENT_CATEGORY = "category";
    public static final String INGREDIENT_USER = "idUser";

    public static final String CATEGORY_KEY = "id";
    public static final String CATEGORY_NAME = "category";

    public static final String INGREDIENT_TABLE_NAME = "Ingredients";
    public static final String INGREDIENT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENT_TABLE_NAME + " (" +
            INGREDIENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INGREDIENT_USER + " INTEGER " +
            INGREDIENT_NAME + " TEXT, " +
            INGREDIENT_CATEGORY + " INTEGER)";

    public static final String CATEGORY_TABLE_NAME = "Categories";
    public static final String CATEGORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" +
            CATEGORY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY_NAME + " TEXT)";

    public IngredientDAO(Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(CATEGORY_TABLE_CREATE);
            this.mDb.execSQL(INGREDIENT_TABLE_CREATE);
        }
        catch (Exception e) {
            Log.v("init(): ", e.getMessage());
        }
    }

    public void add(Ingredient i) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_NAME, i.getName());
        value.put(INGREDIENT_CATEGORY, i.getCategory());
        try {
            mDb.insert(INGREDIENT_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }
    }

    public void delete(long id) {
        mDb.delete(INGREDIENT_TABLE_NAME, INGREDIENT_KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(Ingredient i) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_NAME, i.getName());
        value.put(INGREDIENT_CATEGORY, i.getCategory());
        mDb.update(INGREDIENT_TABLE_NAME, value, INGREDIENT_KEY + " = ?", new String[] {String.valueOf(i.getId())});
    }

    public void addCategory(String name) {
        ContentValues value = new ContentValues();
        try {
            Cursor c = mDb.rawQuery("select " + CATEGORY_NAME +
                    " from " + CATEGORY_TABLE_NAME +
                    " where " + CATEGORY_NAME + " = ?" , new String[]{name});
            if (c.getCount() == 0) {
                value.put(CATEGORY_NAME, name);
                mDb.insert(CATEGORY_TABLE_NAME, null, value);
            }
        } catch (Exception e) {
            Log.v("Error in addCategory: ", e.getMessage());
        }
    }

    public void modifyCategory(String oldName, String name) {
        ContentValues value = new ContentValues();
        value.put(CATEGORY_NAME, name);
        mDb.update(CATEGORY_TABLE_NAME, value, CATEGORY_NAME + " = ?", new String[]{oldName});
    }

    public void deleteCategory(long id) {
        mDb.delete(CATEGORY_TABLE_NAME, CATEGORY_KEY + " = ?", new String[] {String.valueOf(id)});
    }
}
