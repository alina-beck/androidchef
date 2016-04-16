package ava.androidchef.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static DbHelper singletonInstance;

    public static final String DATABASE_NAME = "chef.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECIPES = "recipes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";

    // database creation SQL syntax
    private static final String CREATE_DATABASE = "create table " + TABLE_RECIPES + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TITLE + " text not null);";

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new DbHelper(context.getApplicationContext());
        }
        return singletonInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_RECIPES);
        onCreate(db);
    }
}
