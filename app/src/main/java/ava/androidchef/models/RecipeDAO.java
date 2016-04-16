package ava.androidchef.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ava.androidchef.database.DbHelper;

public class RecipeDAO {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public RecipeDAO(Context context) {
        dbHelper = DbHelper.getInstance(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public boolean insertRecipe(Recipe recipe) {
        open();
        ContentValues values = prepareContentValues(recipe);
        long result = db.insert(DbHelper.TABLE_RECIPES, null, values);
        close();

        return (result != -1);
    }

    private ContentValues prepareContentValues (Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COLUMN_TITLE, recipe.getTitle());

        return values;
    }
}
