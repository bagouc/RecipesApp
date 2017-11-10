package dao;

import database.DatabaseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Claire on 10/11/2017.
 */

public abstract class DAOBase {

    protected final static int VERSION = 1;

    protected final static String NAME = "databaseMyRecipes.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NAME, null, VERSION);
    }

    public SQLiteDatabase open() {
        // not necessary to close the database, the function getWrtiableDatabase does it
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}
