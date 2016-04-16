package ava.androidchef.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

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

    public ArrayList<Recipe> getAllRecipes() {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES;
        return fetchRecipes(sqlQuery);
    }

    public ArrayList<Recipe> getRandomMenu(int days) {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES + " order by random() limit " + days;
        return fetchRecipes(sqlQuery);
    }

    private ArrayList<Recipe> fetchRecipes(String sqlQuery) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        open();
        Cursor cursor = db.rawQuery(sqlQuery, null);

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
        int id = cursor.getInt(0);
        String title = cursor.getString(1);

        return new Recipe(id, title);
    }
}
