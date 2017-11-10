package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Claire on 10/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String USER_KEY = "id";
    public static final String USER_NAME = "username";
    public static final String USER_PWD = "password";

    public static final String USER_TABLE_NAME = "Users";
    public static final String USER_TABLE_CREATE = "CREATE TABLE " +
            USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_PWD + " TEXT);";

    public static final  String USER_TABLE_DROP =  "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(USER_TABLE_DROP);
        onCreate(db);
    }

}

