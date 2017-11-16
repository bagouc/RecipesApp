package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import model.User;

/**
 * Created by Claire on 16/11/2017.
 */

public class SessionDAO  extends DAOBase {
    public static final String SESSION_KEY = "id_user";

    public static final String SESSION_TABLE_NAME = "Session";
    public static final String SESSION_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + SESSION_TABLE_NAME + " (" +
            SESSION_KEY + " INTEGER PRIMARY KEY)";

    public SessionDAO(Context context) {
        super(context);
        try {
            this.open();
            this.mDb.execSQL(SESSION_TABLE_CREATE);
        }
        catch (Exception e) {
            Log.v("init(): ", e.getMessage());
        }
    }

    public void addUserConnected(long i) {
        ContentValues value = new ContentValues();
        value.put(SESSION_KEY, i);
        try {
            mDb.insert(SESSION_TABLE_NAME, null, value);
        } catch (Exception e) {
            String chaine = e.getMessage();
            Log.v("InsertError",chaine);
        }

    }

    public User getUserConnected(Context context) {
        UserDAO userDAO = new UserDAO(context);
        try {
            Cursor c = mDb.rawQuery("select " + SESSION_KEY +
                    "from " + SESSION_TABLE_NAME , null);
            c.moveToFirst();
            long id = c.getLong(0);
            userDAO.open();
            User u = userDAO.select(id);
            Log.v("User connected : ", u.getUsername());
            return u;
        }
        catch (Exception e) {
            Log.v("Error open()", e.getMessage());
        }
        return null;
    }



    public void delete(long id) {
        mDb.delete(SESSION_TABLE_NAME, SESSION_KEY + " = ?", new String[] {String.valueOf(id)});
    }
}
