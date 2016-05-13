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

        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            long ingredientId = insertIngredient(entry.getKey());
            Ingredient ingredient = selectIngredientById(ingredientId);
            ingredientsFromDatabase.put(ingredient, entry.getValue());
        }

        return ingredientsFromDatabase;
    }

    public long insertIngredient(Ingredient ingredient) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(ingredient);

        long ingredientId = db.insert(DbHelper.TABLE_INGREDIENTS, null, values);
        close();

        return ingredientId;
    }

    public ArrayList<Ingredient> selectAllIngredients() {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS;
        return fetchIngredients(sqlQuery);
    }

    public Ingredient selectIngredientById(long ingredientId) {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS + " where " + DbHelper.COL_INGREDIENT_ID + " = " + ingredientId;
        return fetchIngredient(sqlQuery);
    }

    private Ingredient fetchIngredient(String sqlQuery) {
        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        cursor.moveToFirst();
        Ingredient ingredient = readIngredientFromCursor(cursor);
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
