package ava.androidchef.models.ingredient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.database.DbHelper;

public class IngredientDAO {

    private static IngredientDAO singletonInstance;
    private Context context;

    public static IngredientDAO getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new IngredientDAO(context.getApplicationContext());
        }
        return singletonInstance;
    }

    private IngredientDAO(Context context) {
        this.context = context.getApplicationContext();
    }

    public LinkedHashMap<Ingredient, Integer> insertIngredients(LinkedHashMap<Ingredient, Integer> ingredients) {
        LinkedHashMap<Ingredient, Integer> ingredientsFromDatabase = new LinkedHashMap<>();
        Ingredient ingredientFromDb;

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            if (entry.getKey().getId() == -1) {
                long ingredientId = insertIngredient(entry.getKey());
                ingredientFromDb = selectIngredientById(ingredientId);
            }
            else {
                ingredientFromDb = entry.getKey();
            }
            ingredientsFromDatabase.put(ingredientFromDb, entry.getValue());
        }

        return ingredientsFromDatabase;
    }

    public long insertIngredient(Ingredient ingredient) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(ingredient);

        long ingredientId = db.insertWithOnConflict(DbHelper.TABLE_INGREDIENTS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        close();

        return ingredientId;
    }

    public LinkedHashMap<Ingredient, Integer> updateIngredients(LinkedHashMap<Ingredient, Integer> ingredients) {
        LinkedHashMap<Ingredient, Integer> updatedIngredients = new LinkedHashMap<>();

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            updateIngredient(entry.getKey());
            Ingredient updatedIngredient = selectIngredientById(entry.getKey().getId());
            updatedIngredients.put(updatedIngredient, entry.getValue());
        }

        return updatedIngredients;
    }

    public boolean updateIngredient(Ingredient ingredient) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(ingredient);
        String whereClause = DbHelper.COL_INGREDIENT_ID + "=" + ingredient.getId();

        int update = db.update(DbHelper.TABLE_INGREDIENTS, values, whereClause, null);
        close();
        return (update == 1);
    }

    public ArrayList<Ingredient> selectAllIngredients() {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS;
        return fetchIngredients(sqlQuery);
    }

    public Ingredient selectIngredientById(long ingredientId) {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS + " where " + DbHelper.COL_INGREDIENT_ID + " = " + ingredientId;
        return fetchIngredient(sqlQuery);
    }

    public Ingredient selectIngredientByName(String ingredientName) {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS + " where " + DbHelper.COL_INGREDIENT_NAME + " = \"" + ingredientName + "\"";
        return fetchIngredient(sqlQuery);
    }

    private Ingredient fetchIngredient(String sqlQuery) {
        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        Ingredient ingredient = null;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            ingredient = readIngredientFromCursor(cursor);
        }

        cursor.close();
        close();

        return ingredient;
    }

    private ArrayList<Ingredient> fetchIngredients(String sqlQuery) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ingredients.add(readIngredientFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return ingredients;
    }

    private Ingredient readIngredientFromCursor(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String unit = cursor.getString(2);

        return new Ingredient(id, name, unit);
    }

    private ContentValues prepareContentValues(Ingredient ingredient) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_INGREDIENT_NAME, ingredient.getName());
        values.put(DbHelper.COL_INGREDIENT_UNIT, ingredient.getUnit());

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
