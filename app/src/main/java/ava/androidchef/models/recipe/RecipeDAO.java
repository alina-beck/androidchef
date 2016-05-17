package ava.androidchef.models.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

        long recipeId = db.insert(DbHelper.TABLE_RECIPES, null, values);
        close();

        recipe.setId(recipeId);
        insertRelation(recipe);
        return recipeId;
    }

    public boolean updateRecipe(Recipe recipe) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(recipe);
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + recipe.getId();

        int update = db.update(DbHelper.TABLE_RECIPES, values, whereClause, null);
        close();

        boolean savedRelation = updateRelation(recipe);

        return (update == 1 && savedRelation);
    }

    public boolean deleteRecipe(long id) {
        SQLiteDatabase db = open();
        String whereClause = DbHelper.COL_RECIPE_ID + "=" + id;

        int delete = db.delete(DbHelper.TABLE_RECIPES, whereClause, null);
        close();

        return (delete == 1);
    }

    public ArrayList<Recipe> selectAllRecipes() {
        final String r = DbHelper.TABLE_RECIPES;
        final String ri = DbHelper.TABLE_RECIPES_INGREDIENTS;
        final String i = DbHelper.TABLE_INGREDIENTS;

        String chosenColumns =
                r + "." + DbHelper.COL_RECIPE_ID + ", " +
                        r + "." + DbHelper.COL_RECIPE_TITLE + ", " +
                        r + "." + DbHelper.COL_RECIPE_INSTRUCTIONS + ", " +
                        "group_concat(" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + ") as " + DbHelper.COL_RI_INGREDIENT_ID + ", " +
                        "group_concat(" + ri + "." + DbHelper.COL_RI_AMOUNT + ") as " + DbHelper.COL_RI_AMOUNT + ", " +
                        "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_NAME + ") as " + DbHelper.COL_INGREDIENT_NAME + ", " +
                        "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_UNIT + ") as " + DbHelper.COL_INGREDIENT_UNIT;

        String sqlQuery =
                "select " + chosenColumns + " from " + r +
                        " join " + ri + " on (" + r + "." + DbHelper.COL_RECIPE_ID + " = " + ri + "." + DbHelper.COL_RI_RECIPE_ID +
                        ") join " + i + " on (" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + " = " + i + "." + DbHelper.COL_INGREDIENT_ID +
                        ") group by " + r + "." + DbHelper.COL_RECIPE_ID + ";";
        return fetchRecipes(sqlQuery);
    }

    public ArrayList<Recipe> selectRandomMenu(int days) {
        final String r = DbHelper.TABLE_RECIPES;
        final String ri = DbHelper.TABLE_RECIPES_INGREDIENTS;
        final String i = DbHelper.TABLE_INGREDIENTS;

        String chosenColumns =
                r + "." + DbHelper.COL_RECIPE_ID + ", " +
                r + "." + DbHelper.COL_RECIPE_TITLE + ", " +
                r + "." + DbHelper.COL_RECIPE_INSTRUCTIONS + ", " +
                "group_concat(" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + ") as " + DbHelper.COL_RI_INGREDIENT_ID + ", " +
                "group_concat(" + ri + "." + DbHelper.COL_RI_AMOUNT + ") as " + DbHelper.COL_RI_AMOUNT + ", " +
                "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_NAME + ") as " + DbHelper.COL_INGREDIENT_NAME + ", " +
                "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_UNIT + ") as " + DbHelper.COL_INGREDIENT_UNIT;

        String sqlQuery =
                "select " + chosenColumns + " from " + r +
                " join " + ri + " on (" + r + "." + DbHelper.COL_RECIPE_ID + " = " + ri + "." + DbHelper.COL_RI_RECIPE_ID +
                ") join " + i + " on (" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + " = " + i + "." + DbHelper.COL_INGREDIENT_ID +
                ") group by " + r + "." + DbHelper.COL_RECIPE_ID + " order by random() limit " + days + ";";

        return fetchRecipes(sqlQuery);
    }

    public Recipe selectRandomRecipe(ArrayList<Recipe> exceptions) {

        String exclude = "";
        for (int i = 0; i < exceptions.size(); i++) {
            long id = exceptions.get(i).getId();
            exclude = exclude.concat(Long.toString(id) + ", ");
        }
        exclude = exclude.substring(0, exclude.length()-2);

        final String r = DbHelper.TABLE_RECIPES;
        final String ri = DbHelper.TABLE_RECIPES_INGREDIENTS;
        final String i = DbHelper.TABLE_INGREDIENTS;

        String chosenColumns =
                r + "." + DbHelper.COL_RECIPE_ID + ", " +
                        r + "." + DbHelper.COL_RECIPE_TITLE + ", " +
                        r + "." + DbHelper.COL_RECIPE_INSTRUCTIONS + ", " +
                        "group_concat(" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + ") as " + DbHelper.COL_RI_INGREDIENT_ID + ", " +
                        "group_concat(" + ri + "." + DbHelper.COL_RI_AMOUNT + ") as " + DbHelper.COL_RI_AMOUNT + ", " +
                        "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_NAME + ") as " + DbHelper.COL_INGREDIENT_NAME + ", " +
                        "group_concat(" + i + "." + DbHelper.COL_INGREDIENT_UNIT + ") as " + DbHelper.COL_INGREDIENT_UNIT;

        String sqlQuery = "select " + chosenColumns + " from " + r +
                " join " + ri + " on (" + r + "." + DbHelper.COL_RECIPE_ID + " = " + ri + "." + DbHelper.COL_RI_RECIPE_ID +
                ") join " + i + " on (" + ri + "." + DbHelper.COL_RI_INGREDIENT_ID + " = " + i + "." + DbHelper.COL_INGREDIENT_ID +
                ") where " + DbHelper.COL_RECIPE_ID + " not in ( " + exclude + " ) group by " + r + "." + DbHelper.COL_RECIPE_ID +
                " order by random() limit 1";
        ArrayList<Recipe> randomRecipe = fetchRecipes(sqlQuery);

        if (randomRecipe.size() > 0) {
            return randomRecipe.get(0);
        }

        else return null;
    }

    private void insertRelation(Recipe recipe) {
        SQLiteDatabase db = open();

        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COL_RI_ID, Integer.parseInt(Long.toString(recipe.getId()) + Long.toString(entry.getKey().getId())));
            values.put(DbHelper.COL_RI_RECIPE_ID, recipe.getId());
            values.put(DbHelper.COL_RI_INGREDIENT_ID, entry.getKey().getId());
            values.put(DbHelper.COL_RI_AMOUNT, entry.getValue());
            db.insert(DbHelper.TABLE_RECIPES_INGREDIENTS, null, values);
        }
        close();
    }

    private boolean updateRelation(Recipe recipe) {
        SQLiteDatabase db = open();
        int update = 0;

        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COL_RI_AMOUNT, entry.getValue());

            String whereClause = DbHelper.COL_RI_ID + "=" +
                    Integer.parseInt(Long.toString(recipe.getId()) + Long.toString(entry.getKey().getId()));
            update += db.update(DbHelper.TABLE_RECIPES_INGREDIENTS, values, whereClause, null);
        }
        return (update == recipe.getIngredients().size());
    }

    private ArrayList<Recipe> fetchRecipes(String sqlQuery) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            recipes.add(readRecipeFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return recipes;
    }

    private Recipe readRecipeFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String instructions = cursor.getString(2);
        String[] ingredientIds = cursor.getString(3).split(",");
        String[] ingredientAmounts = cursor.getString(4).split(",");
        String[] ingredientNames = cursor.getString(5).split(",");
        String[] ingredientUnits = cursor.getString(6).split(",");

        LinkedHashMap<Ingredient, Integer> ingredients = new LinkedHashMap<>();
        for (int i = 0; i < ingredientIds.length; i++) {
            long ingredientId = Long.parseLong(ingredientIds[i]);
            String ingredientName = ingredientNames[i];
            String ingredientUnit = ingredientUnits[i];
            Ingredient ingredient = new Ingredient(ingredientId, ingredientName, ingredientUnit);
            ingredients.put(ingredient, Integer.parseInt(ingredientAmounts[i]));
        }

        return new Recipe(id, title, ingredients, instructions);
    }

    private ContentValues prepareContentValues(Recipe recipe) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_RECIPE_TITLE, recipe.getTitle());
        values.put(DbHelper.COL_RECIPE_INSTRUCTIONS, recipe.getInstructions());

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
