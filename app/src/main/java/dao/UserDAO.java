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
    public static final String USER_TABLE_CREATE = "CREATE TABLE " +
            USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_PWD + " TEXT);";

    public static final  String USER_TABLE_DROP =  "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public UserDAO (Context context) {
        super(context);
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

    public User select(long id) {
        //Cursor rawQuery(String sql, String[] selectionArgs)
        try {
            Cursor c = mDb.rawQuery("select " + USER_KEY + ", " + USER_NAME + ", " + USER_PWD + " " +
                    "from " + USER_TABLE_NAME + " where " + USER_KEY + " = ?", new String[]{String.valueOf(id)});
            User u = new User(c.getLong(0), c.getString(1), c.getString(2));
            return u;
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("SelectError",chaine);
        }
        return null;
    }

}
