package ava.androidchef;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RecipeDAO {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public RecipeDAO(Context context) {
        dbHelper = DbHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
}
