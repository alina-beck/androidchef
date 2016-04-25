package ava.androidchef.models.recipe;

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
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void close() {
        dbHelper.close();
    }

    public long insertRecipe(Recipe recipe) {
        open();
        ContentValues values = prepareContentValues(recipe);
        long result = db.insert(DbHelper.TABLE_RECIPES, null, values);

        close();
        //TODO: confirm row id equals object id
        return result;
    }

    public boolean updateRecipe(Recipe recipe) {
        open();
        ContentValues values = prepareContentValues(recipe);
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + recipe.getId();

        int update = db.update(DbHelper.TABLE_RECIPES, values, whereClause, null);
        close();

        return (update == 1);
    }

    public boolean deleteRecipe(int id) {
        open();
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + id;
        int delete = db.delete(DbHelper.TABLE_RECIPES, whereClause, null);
        close();

        return (delete == 1);
    }

    public ArrayList<Recipe> getAllRecipes() {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES;
        return fetchRecipes(sqlQuery);
    }

    public ArrayList<Recipe> getRandomMenu(int days) {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES + " order by random() limit " + days;
        return fetchRecipes(sqlQuery);
    }

    public Recipe getRandomRecipe(ArrayList<Recipe> exceptions) {

        String exclude = "";
        for (int i = 0; i < exceptions.size(); i++) {
            int id = exceptions.get(i).getId();
            exclude = exclude.concat(Integer.toString(id) + ", ");
        }
        exclude = exclude.substring(0, exclude.length()-2);

        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES + " where " +
                DbHelper.COL_RECIPE_ID + " not in ( " + exclude + " ) order by random() limit 1";
        return fetchRecipes(sqlQuery).get(0);
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

    private ContentValues prepareContentValues(Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_RECIPE_TITLE, recipe.getTitle());

        return values;
    }
}
