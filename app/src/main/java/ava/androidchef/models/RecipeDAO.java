package ava.androidchef.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Recipe> fetchWeeklyMenu () {
        ArrayList<Recipe> recipes = new ArrayList<>();

        open();
        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_RECIPES + " order by random() limit 7", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            recipes.add(getRecipeFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return recipes;
    }

    private Recipe getRecipeFromCursor(Cursor cursor) {
        String title = cursor.getString(1);

        return new Recipe(title);
    }
}
