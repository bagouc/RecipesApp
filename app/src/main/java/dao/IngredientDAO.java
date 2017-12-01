package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

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

    public static final String INGREDIENTS_SELECTED_KEY = "id_ing";
    public static final String INGREDIENTS_SELECTED_USER = "id_user";

    public static final String CATEGORY_KEY = "id";
    public static final String CATEGORY_NAME = "category";

    public static final String INGREDIENT_TABLE_NAME = "Ingredients";
    public static final String INGREDIENT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENT_TABLE_NAME + " (" +
            INGREDIENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INGREDIENT_USER + " INTEGER,  " +
            INGREDIENT_NAME + " TEXT, " +
            INGREDIENT_CATEGORY + " INTEGER)";

    public static final String INGREDIENT_PROHIBITED_TABLE_NAME = "Ingredients";
    public static final String INGREDIENT_PROHIBITED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENT_PROHIBITED_TABLE_NAME + " (" +
            INGREDIENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INGREDIENT_USER + " INTEGER)";

    public static final String CATEGORY_TABLE_NAME = "Categories";
    public static final String CATEGORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" +
            CATEGORY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY_NAME + " TEXT)";

    public static final String INGREDIENTS_SELECTED_TABLE_NAME = "Ingredients_Selected";
    public static final String INGREDIENTS_SELECTED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENTS_SELECTED_TABLE_NAME + " (" +
            INGREDIENTS_SELECTED_KEY + " INTEGER, " +
            INGREDIENTS_SELECTED_USER + " INTEGER)";

    public IngredientDAO(Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(CATEGORY_TABLE_CREATE);
            this.mDb.execSQL(INGREDIENT_TABLE_CREATE);
            this.mDb.execSQL(INGREDIENTS_SELECTED_TABLE_CREATE);
            this.mDb.execSQL(INGREDIENT_PROHIBITED_TABLE_CREATE);
            Ingredient i = getIngredient(1);
            addIngredientProhibited(i, 1);
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

    public void addIngredientProhibited(Ingredient i, long id_user) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_KEY, i.getId());
        value.put(INGREDIENT_USER, id_user);
        try {
            mDb.insert(INGREDIENT_PROHIBITED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }
    }



    public void addIngredientSelected(long id_ing, long id_user) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENTS_SELECTED_KEY, id_ing);
        value.put(INGREDIENTS_SELECTED_USER, id_user);
        try {
            mDb.insert(INGREDIENTS_SELECTED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }
    }

    public void delete(long id) {
        mDb.delete(INGREDIENT_TABLE_NAME, INGREDIENT_KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void deleteIngredientProhibited(long id) {
        mDb.delete(INGREDIENT_PROHIBITED_TABLE_NAME, INGREDIENT_KEY + "= ?", new String[] {String.valueOf(id)});
    }

    public void modify(Ingredient i) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_NAME, i.getName());
        value.put(INGREDIENT_CATEGORY, i.getCategory());
        mDb.update(INGREDIENT_TABLE_NAME, value, INGREDIENT_KEY + " = ?", new String[] {String.valueOf(i.getId())});
    }

    public void setSelected(long i, Context context) {

        SessionDAO sessionDAO = new SessionDAO(context);
        Ingredient ingredient = select(i);
        try {
            long id_user = sessionDAO.getUserConnected(context).getId();
            ContentValues value = new ContentValues();
            value.put(INGREDIENTS_SELECTED_KEY, i);
            value.put(INGREDIENTS_SELECTED_USER, id_user);
            mDb.insert(INGREDIENTS_SELECTED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("errorUpdate",chaine);
        }
    }

    public Ingredient select(long id) {
        //Cursor rawQuery(String sql, String[] selectionArgs)
        try {
            Cursor c = mDb.rawQuery("select " + INGREDIENT_KEY + ", " + INGREDIENT_NAME + ", " + INGREDIENT_CATEGORY +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_KEY + " = ?", new String[]{String.valueOf(id)});
            c.moveToFirst();
           Ingredient u = new Ingredient(c.getLong(0), c.getString(1), c.getInt(2));
            return u;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0",chaine);
        }
        return null;
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

    public Vector<Ingredient> getListIngredientsSelected(long id) {
        try {
            Cursor d = mDb.rawQuery("select " + INGREDIENTS_SELECTED_KEY + ", " + INGREDIENTS_SELECTED_USER + ", " +
                    INGREDIENT_NAME + ", " + INGREDIENT_CATEGORY +
                    " from " + INGREDIENTS_SELECTED_TABLE_NAME + " inner join " +  INGREDIENT_TABLE_NAME + " on " +
                    INGREDIENTS_SELECTED_TABLE_NAME +  "." + INGREDIENTS_SELECTED_KEY + " = " + INGREDIENT_TABLE_NAME + "." + INGREDIENT_KEY +
                    " where " + INGREDIENTS_SELECTED_USER + " = ?", new String[]{String.valueOf(id)});

            Vector<Ingredient> list = new Vector<Ingredient>();
            while (d.moveToNext()) {
                list.add(new Ingredient(d.getLong(0), d.getString(2), d.getInt(3)));
            }
            return list;

        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError1",chaine);
        }
        return null;
    }

    public Ingredient getIngredient(long i ) {
        try {
            Cursor c = mDb.rawQuery("select * " + " " +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_KEY + " = ?", new String[]{(i+"")});
            c.moveToFirst();
            Ingredient ing  = new Ingredient(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
            return ing;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return null;
    }

    public Ingredient getIngredient(String i ) {
        try {
            Cursor c = mDb.rawQuery("select * " + " " +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_NAME + " = ?", new String[]{i});
            c.moveToFirst();
            Ingredient ing  = new Ingredient(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
            return ing;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return null;
    }


    public ArrayList<Ingredient> getListIngredientsProhibited(long id) {
        try {
            Cursor d = mDb.rawQuery("select " + INGREDIENT_KEY + ", " + INGREDIENT_USER +
                    " from " + INGREDIENT_PROHIBITED_TABLE_NAME +
                    " where " + INGREDIENT_USER + " = ?", new String[]{String.valueOf(id)});

            ArrayList<Ingredient> list = new ArrayList<>();
            while (d.moveToNext()) {
                Ingredient i = getIngredient(d.getLong(0));
                list.add(i);
            }
            return list;

        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError1",chaine);
        }
        return null;
    }

    public void dropTableIngredientSelected() {
        mDb.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS_SELECTED_TABLE_NAME);
    }

    public void dropTableIngredients() {
        mDb.execSQL("DROP TABLE IF EXISTS " + INGREDIENT_TABLE_NAME);
    }

    public void dropTableCategories() {
        mDb.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
    }
}
