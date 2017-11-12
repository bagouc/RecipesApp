package dao;

import database.DatabaseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Claire on 10/11/2017.
 */

public abstract class DAOBase extends SQLiteOpenHelper{

    protected final static int VERSION = 1;

    protected final static String NAME = "databaseMyRecipes.db";

    protected SQLiteDatabase mDb = null;

    public DAOBase(Context context) {
        super(context, NAME, null, VERSION);
    }

    public SQLiteDatabase open() {
        // not necessary to close the database, the function getWrtiableDatabase does it
        try {
            mDb = this.getWritableDatabase();
        } catch (Exception e) {
            Log.v("getWritableDatabase(): ", e.getMessage());
        }
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
