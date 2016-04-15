package ava.androidchef;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RecipeDAO {

    private static SQLiteDatabase db;
    private static DbHelper dbHelper;

    public RecipeDAO(Context context) {
        dbHelper = DbHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public static void close() {
        dbHelper.close();
    }

    public static boolean insertRecipe(Recipe recipe) {
        ContentValues values = prepareContentValues(recipe);
        long result = db.insert(DbHelper.TABLE_RECIPES, null, values);
        close();

        return result != -1;

    }

    private static ContentValues prepareContentValues (Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COLUMN_TITLE, recipe.getTitle());

        return values;
    }
}
