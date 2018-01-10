package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import model.User;

/**
 * Created by Claire on 10/11/2017.
 */

public class UserDAO extends DAOBase{
    public static final String USER_KEY = "id";
    public static final String USER_NAME = "username";
    public static final String USER_PWD = "password";

    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + " (" +
            USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_PWD + " TEXT)";

    public static final String USER_TABLE_DROP =  "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public UserDAO (Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(USER_TABLE_CREATE);
        }
        catch (Exception e) {
            Log.v("init(): ", e.getMessage());
        }
    }

    public void dropTable() {
        this.mDb.execSQL(USER_TABLE_DROP);
    }

    public void add(User u) {
        ContentValues value = new ContentValues();
        value.put(USER_NAME, u.getUsername());
        value.put(USER_PWD, u.getPassword());
        if (value == null) {
            String chaine2 = "Value = null";
            Log.v("InsertError",chaine2);
        }
        try {
            mDb.insert(USER_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }

    }

    public void delete(long id) {
        mDb.delete(USER_TABLE_NAME, USER_KEY + " = ?", new String[] {String.valueOf(id)});
    }

    public void modify(User u) {
        ContentValues value = new ContentValues();
        value.put(USER_NAME, u.getUsername());
        value.put(USER_PWD, u.getPassword());
        mDb.update(USER_TABLE_NAME, value, USER_KEY  + " = ?", new String[] {String.valueOf(u.getId())});

    }

    public void update(String name, long id) {
        ContentValues value = new ContentValues();
        value.put(USER_NAME, name);
        mDb.update(USER_TABLE_NAME, value, USER_KEY  + " = ?", new String[] {String.valueOf(id)});

    }

    public void update(String name, String password, long id) {
        ContentValues value = new ContentValues();
        value.put(USER_NAME, name);
        value.put(USER_PWD, password);
        mDb.update(USER_TABLE_NAME, value, USER_KEY  + " = ?", new String[] {String.valueOf(id)});

    }

    public User select(long id) {
        //Cursor rawQuery(String sql, String[] selectionArgs)
        try {
            Cursor c = mDb.rawQuery("select " + USER_KEY + ", " + USER_NAME + ", " + USER_PWD + " " +
                    " from " + USER_TABLE_NAME + " where " + USER_KEY + " = ?", new String[]{String.valueOf(id)});
            c.moveToFirst();
            User u = new User(c.getLong(0), c.getString(1), c.getString(2));
            return u;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return null;
    }

    public boolean exsistsUser(String username) {
        try {
            Cursor c = mDb.rawQuery("select " + USER_NAME +
                    " from " + USER_TABLE_NAME + " where " + USER_NAME + " = ?", new String[]{username});
            Log.v("cursor count: ", Integer.toString(c.getCount()));
            return c.getCount() > 0;
        } catch (Exception e) {
            Log.v("Error in existsUser(): ", e.getMessage());
        }
        return false;
    }

    public boolean checkAuthentication(String username, String pass) {
        try {
            Cursor c = mDb.rawQuery("select " + USER_NAME + ", " + USER_PWD +
                    " from " + USER_TABLE_NAME +
                    " where " + USER_NAME + " = ? AND " + USER_PWD + " = ?", new String[]{username, pass});
            Log.v("cursor count: ", Integer.toString(c.getCount()));
            return c.getCount() > 0;
        } catch (Exception e) {
            Log.v("Error in checkAuth(): ", e.getMessage());
        }
        return false;
    }

    public long getId(String username) {
        try {
            Cursor c = mDb.rawQuery("select " + USER_KEY + ", " + USER_NAME + ", " + USER_PWD + " " +
                    " from " + USER_TABLE_NAME + " where " + USER_NAME + " = ?", new String[]{username});
            c.moveToFirst();
            User u = new User(c.getLong(0), c.getString(1), c.getString(2));
            return u.getId();
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return 0;
    }

    public User testQuery() {
        String[] columns = {USER_KEY, USER_NAME, USER_PWD};
        try {

            Cursor c = mDb.query(USER_TABLE_NAME, columns, "", null, null, null, null);
            c.moveToFirst();
            User u = new User(c.getInt(0), c.getString(1), c.getString(2));

            return u;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return null;
    }
}
