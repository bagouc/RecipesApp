package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
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

    public static final String INGREDIENT_NEW_RECIPE_TABLE_NAME = "Ingredients_new_recipe";
    public static final String INGREDIENT_NEW_RECIPE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENT_NEW_RECIPE_TABLE_NAME + " (" +
            INGREDIENT_KEY + " INTEGER PRIMARY KEY )";

    public static final String INGREDIENT_PROHIBITED_TABLE_NAME = "Ingredients_Prohibited";
    public static final String INGREDIENT_PROHIBITED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + INGREDIENT_PROHIBITED_TABLE_NAME + " (" +
            INGREDIENT_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INGREDIENT_USER + " INTEGER)";

    public static final String CATEGORY_PROHIBITED_TABLE_NAME = "Categories_Prohibited";
    public static final String CATEGORY_PROHIBITED_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + CATEGORY_PROHIBITED_TABLE_NAME + " (" +
            CATEGORY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
            this.mDb.execSQL(CATEGORY_PROHIBITED_TABLE_CREATE);
            this.mDb.execSQL(INGREDIENT_NEW_RECIPE_TABLE_CREATE);
        } catch (Exception e) {
            Log.v("init(): ", e.getMessage());
        }
    }

    public void add(Ingredient i) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_NAME, i.getName());
        value.put(INGREDIENT_CATEGORY, i.getCategory());
        value.put(INGREDIENT_USER, i.getIdUser());
        try {
            mDb.insert(INGREDIENT_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public void addIngredientNewRecipe(long id) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_KEY, id);
        try {
            mDb.insert(INGREDIENT_NEW_RECIPE_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public Vector<Ingredient> getListIngredientForNewRecipe() {
        Vector<Ingredient> ingredients = new Vector<Ingredient>();
        try {
            Cursor c = mDb.rawQuery("select * " +
                    " from " + INGREDIENT_NEW_RECIPE_TABLE_NAME, new String[]{});
            while (c.moveToNext()) {
                long id = c.getLong(0);
                Ingredient i = getIngredient(id);
                ingredients.add(i);
            }
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return ingredients;
    }

    public void addIngredientProhibited(Ingredient i, long id_user) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_KEY, i.getId());
        value.put(INGREDIENT_USER, id_user);
        try {
            mDb.insert(INGREDIENT_PROHIBITED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public void addCategoryProhibited(long cat, long id_user) {
        ContentValues value = new ContentValues();
        value.put(CATEGORY_KEY, cat);
        value.put(INGREDIENT_USER, id_user);
        try {
            mDb.insert(CATEGORY_PROHIBITED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public ArrayList<String> getCategories() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + CATEGORY_TABLE_NAME, new String[]{});

            while (c.moveToNext()) {
                list.add(c.getString(1));
            }
            return list;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public ArrayList<String> getCategoriesAvailable(long id) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + CATEGORY_TABLE_NAME, new String[]{});

            while (c.moveToNext()) {
                Cursor d = mDb.rawQuery("select *" +
                        " from " + CATEGORY_PROHIBITED_TABLE_NAME + " where " + CATEGORY_KEY + " = ? ", new String[]{c.getLong(0) + ""});
                if (!d.moveToNext()) {
                    list.add(c.getString(1));
                }
            }
            return list;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public ArrayList<String> getIngredientsAvailable(long id_cat, long id) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("");
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_CATEGORY + " = ?", new String[]{id_cat + ""});

            while (c.moveToNext()) {
                if (c.getLong(1) < 0 || c.getLong(1) == id) {
                    Cursor d = mDb.rawQuery("select *" +
                            " from " + CATEGORY_PROHIBITED_TABLE_NAME + " where " + CATEGORY_KEY + " = ? ", new String[]{c.getLong(3) + ""});
                    if (!d.moveToNext()) {
                        Cursor e = mDb.rawQuery("select *" +
                                " from " + INGREDIENT_PROHIBITED_TABLE_NAME + " where " + INGREDIENT_KEY + " = ? ", new String[]{c.getLong(0) + ""});
                        if (!e.moveToNext()) {
                            list.add(c.getString(2));
                        }
                    }
                }
            }
            return list;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public ArrayList<String> getListIngredients() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + INGREDIENT_TABLE_NAME, new String[]{});

            while (c.moveToNext()) {
                list.add(c.getString(2));
            }
            return list;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public ArrayList<String> getIngredientFromCategory(long id) {
        ArrayList<String> ingredients = new ArrayList<>();
        try {
            Cursor c = mDb.rawQuery("select * " + " " +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_CATEGORY + " = ?", new String[]{id + ""});
            while (c.moveToNext()) {
                Ingredient i = new Ingredient(c.getLong(0), c.getLong(1), c.getString(2), c.getLong(3));
                ingredients.add(i.getName());
            }
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return ingredients;
    }

    public boolean isSelected(long id, long id_user) {
        try {
            Cursor c = mDb.rawQuery("select * " +
                    " from " + INGREDIENTS_SELECTED_TABLE_NAME + " where " + INGREDIENTS_SELECTED_USER + " = ?", new String[]{id_user + ""});
            while (c.moveToNext()) {
                if (c.getLong(0) == id) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return false;
    }

    public boolean isProhibited(long id, long id_user) {
        try {
            Cursor c = mDb.rawQuery("select * " +
                    " from " + INGREDIENT_PROHIBITED_TABLE_NAME + " where " + INGREDIENT_USER + " = ?", new String[]{id_user + ""});
            while (c.moveToNext()) {
                if (c.getLong(0) == id) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return false;
    }

    public boolean isCategoryProhibited(long id, long id_user) {
        try {
            Cursor c = mDb.rawQuery("select * " +
                    " from " + CATEGORY_PROHIBITED_TABLE_NAME + " where " + INGREDIENT_USER + " = ?", new String[]{id_user + ""});
            while (c.moveToNext()) {
                if (c.getLong(0) == id) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return false;
    }

    public void addIngredientSelected(long id_ing, long id_user) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENTS_SELECTED_KEY, id_ing);
        value.put(INGREDIENTS_SELECTED_USER, id_user);
        try {
            mDb.insert(INGREDIENTS_SELECTED_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public void deleteIngredientSelected(long id_ing, long id_user) {
        try {
            mDb.delete(INGREDIENTS_SELECTED_TABLE_NAME, INGREDIENTS_SELECTED_KEY + " = ?  and " + INGREDIENTS_SELECTED_USER + " = ?", new String[]{id_ing + "", id_user + ""});
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError", chaine);
        }
    }

    public void delete(long id) {
        mDb.delete(INGREDIENT_TABLE_NAME, INGREDIENT_KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteIngredientProhibited(long id) {
        mDb.delete(INGREDIENT_PROHIBITED_TABLE_NAME, INGREDIENT_KEY + "= ?", new String[]{String.valueOf(id)});
    }

    public void deleteCategoryProhibited(String name, long id_user) {
        long id = getIdCategory(name);
        mDb.delete(CATEGORY_PROHIBITED_TABLE_NAME, CATEGORY_KEY + "= ?", new String[]{String.valueOf(id)});
    }

    public void modify(Ingredient i) {
        ContentValues value = new ContentValues();
        value.put(INGREDIENT_NAME, i.getName());
        value.put(INGREDIENT_CATEGORY, i.getCategory());
        mDb.update(INGREDIENT_TABLE_NAME, value, INGREDIENT_KEY + " = ?", new String[]{String.valueOf(i.getId())});
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
            Log.v("errorUpdate", chaine);
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
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public long getIdCategory(String name) {
        //Cursor rawQuery(String sql, String[] selectionArgs)
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + CATEGORY_TABLE_NAME + " where " + CATEGORY_NAME + " = ?", new String[]{String.valueOf(name)});
            c.moveToFirst();
            return c.getLong(0);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return -1;
    }

    public String getNameCategory(long i) {
        //Cursor rawQuery(String sql, String[] selectionArgs)
        try {
            Cursor c = mDb.rawQuery("select *" +
                    " from " + CATEGORY_TABLE_NAME + " where " + CATEGORY_KEY + " = ?", new String[]{String.valueOf(i + "")});
            c.moveToFirst();
            return c.getString(1);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError0", chaine);
        }
        return null;
    }

    public void addCategory(String name) {
        ContentValues value = new ContentValues();
        try {
            Cursor c = mDb.rawQuery("select " + CATEGORY_NAME +
                    " from " + CATEGORY_TABLE_NAME +
                    " where " + CATEGORY_NAME + " = ?", new String[]{name});
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
        mDb.delete(CATEGORY_TABLE_NAME, CATEGORY_KEY + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteIngregientNewRecipe() {
        mDb.delete(INGREDIENT_NEW_RECIPE_TABLE_NAME, "", new String[]{});
    }

    public void deleteIngregientNewRecipe(long id) {
        mDb.delete(INGREDIENT_NEW_RECIPE_TABLE_NAME, ""+INGREDIENT_KEY + " = ? ", new String[]{String.valueOf(id)});
    }


    public Vector<Ingredient> getListIngredientsSelected(long id) {
        try {
            Cursor d = mDb.rawQuery("select " + INGREDIENTS_SELECTED_KEY + ", " + INGREDIENTS_SELECTED_USER + ", " +
                    INGREDIENT_NAME + ", " + INGREDIENT_CATEGORY +
                    " from " + INGREDIENTS_SELECTED_TABLE_NAME + " inner join " + INGREDIENT_TABLE_NAME + " on " +
                    INGREDIENTS_SELECTED_TABLE_NAME + "." + INGREDIENTS_SELECTED_KEY + " = " + INGREDIENT_TABLE_NAME + "." + INGREDIENT_KEY +
                    " where " + INGREDIENTS_SELECTED_USER + " = ?", new String[]{String.valueOf(id)});

            Vector<Ingredient> list = new Vector<Ingredient>();
            while (d.moveToNext()) {
                list.add(new Ingredient(d.getLong(0), d.getString(2), d.getInt(3)));
            }
            return list;

        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError1", chaine);
        }
        return null;
    }

    public Ingredient getIngredient(long i) {
        try {
            Cursor c = mDb.rawQuery("select * " + " " +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_KEY + " = ?", new String[]{(i + "")});
            c.moveToFirst();
            Ingredient ing = new Ingredient(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
            return ing;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return null;
    }

    public Ingredient getIngredient(String i) {
        try {
            Cursor c = mDb.rawQuery("select * " + " " +
                    " from " + INGREDIENT_TABLE_NAME + " where " + INGREDIENT_NAME + " = ?", new String[]{i});
            if (c.moveToFirst()) {
                Ingredient ing = new Ingredient(c.getLong(0), c.getLong(1), c.getString(2), c.getInt(3));
                return ing;
            } else {
                return null;
            }
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError", chaine);
        }
        return null;
    }

    public Vector<Ingredient> getVectorListIngredientProhibited(long id) {
        Vector<Ingredient> ing = new Vector<>();

        // first: category
        Cursor c = mDb.rawQuery("select " + CATEGORY_KEY + ", " + INGREDIENT_USER +
                " from " + CATEGORY_PROHIBITED_TABLE_NAME +
                " where " + INGREDIENT_USER + " = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            long id_cat = c.getLong(0);
            Cursor d = mDb.rawQuery("select * " +
                    " from " + INGREDIENT_TABLE_NAME +
                    " where " + INGREDIENT_CATEGORY + " = ?", new String[]{String.valueOf(id_cat)});
            while (d.moveToNext()) {
                Ingredient ingredient = getIngredient(d.getLong(0));
                ing.add(ingredient);
            }
        }

        // specific ingredient
        Cursor e = mDb.rawQuery("select " + INGREDIENT_KEY + ", " + INGREDIENT_USER +
                " from " + INGREDIENT_PROHIBITED_TABLE_NAME +
                " where " + INGREDIENT_USER + " = ?", new String[]{String.valueOf(id)});
        while (e.moveToNext()) {
            Ingredient i = getIngredient(e.getLong(0));
            if (!ing.contains(i)) {
                ing.add(i);
            }
        }
        return ing;

    }

    public ArrayList<Ingredient> getListIngredientsProhibited(long id) {
        try {
            Cursor d = mDb.rawQuery("select " + INGREDIENT_KEY + ", " + INGREDIENT_USER +
                    " from " + INGREDIENT_PROHIBITED_TABLE_NAME +
                    " where " + INGREDIENT_USER + " = ?", new String[]{String.valueOf(id)});
            Cursor c = mDb.rawQuery("select " + CATEGORY_KEY + ", " + INGREDIENT_USER +
                    " from " + CATEGORY_PROHIBITED_TABLE_NAME +
                    " where " + INGREDIENT_USER + " = ?", new String[]{String.valueOf(id)});


            ArrayList<Ingredient> list = new ArrayList<>();
            while (d.moveToNext()) {
                Ingredient i = getIngredient(d.getLong(0));
                list.add(i);
            }
            while (c.moveToNext()) {
                String name = getNameCategory(c.getLong(0));
                Ingredient j = new Ingredient(-1, name);
                list.add(j);
            }
            return list;

        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError1", chaine);
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

    public void dropTableCategoriesProhibited() {
        mDb.execSQL("DROP TABLE IF EXISTS " + CATEGORY_PROHIBITED_TABLE_NAME);
    }
}