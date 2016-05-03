package ava.androidchef.models.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Map;

import ava.androidchef.database.DbHelper;
import ava.androidchef.models.ingredient.Ingredient;

public class RecipeDAO {

    private static RecipeDAO singletonInstance;
    private Context context;

    public static RecipeDAO getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new RecipeDAO(context.getApplicationContext());
        }
        return singletonInstance;
    }

    private RecipeDAO(Context context) {
        this.context = context.getApplicationContext();
    }

    public long insertRecipe(Recipe recipe) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(recipe);
        long result = db.insert(DbHelper.TABLE_RECIPES, null, values);
        insertRelation(recipe);

        close();
        return result;
    }

    public boolean updateRecipe(Recipe recipe) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(recipe);
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + recipe.getId();

        int update = db.update(DbHelper.TABLE_RECIPES, values, whereClause, null);
        close();

        return (update == 1);
    }

    public boolean deleteRecipe(int id) {
        SQLiteDatabase db = open();
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + id;
        int delete = db.delete(DbHelper.TABLE_RECIPES, whereClause, null);
        close();

        return (delete == 1);
    }

    public ArrayList<Recipe> selectAllRecipes() {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES;
        return fetchRecipes(sqlQuery);
    }

    public ArrayList<Recipe> selectRandomMenu(int days) {
        String sqlQuery = "select * from " + DbHelper.TABLE_RECIPES + " order by random() limit " + days;
        return fetchRecipes(sqlQuery);
    }

    public Recipe selectRandomRecipe(ArrayList<Recipe> exceptions) {

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

    private void insertRelation(Recipe recipe) {
        SQLiteDatabase db = open();
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COL_RI_ID, Integer.parseInt(Integer.toString(recipe.getId()) + Integer.toString(entry.getKey().getId())));
            values.put(DbHelper.COL_RI_RECIPE_ID, recipe.getId());
            values.put(DbHelper.COL_RI_INGREDIENT_ID, entry.getKey().getId());
            values.put(DbHelper.COL_RI_AMOUNT, entry.getValue());
        }
        close();
    }

    private ArrayList<Recipe> fetchRecipes(String sqlQuery) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = open();
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

    private SQLiteDatabase open() {
        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(true);
        return db;
    }

    private void close() {
        DbHelper dbHelper = DbHelper.getInstance(context);
        dbHelper.close();
    }
}
