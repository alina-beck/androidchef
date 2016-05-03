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

        LinkedHashMap<Ingredient, Integer> newIngredients = new LinkedHashMap<>();
        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            Ingredient ingredient = insertIngredient(entry.getKey());
            newIngredients.put(ingredient, entry.getValue());
        }
        return newIngredients;
    }

    public Ingredient insertIngredient(Ingredient ingredient) {
        SQLiteDatabase db = open();
        ContentValues values = prepareContentValues(ingredient);
        db.insert(DbHelper.TABLE_INGREDIENTS, null, values);

        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_INGREDIENTS +
                " order by " + DbHelper.COL_INGREDIENT_ID + " desc limit 1", null);
        cursor.moveToLast();
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String unit = cursor.getString(2);

        cursor.close();
        close();

        return new Ingredient(id, name, unit);
    }

    public ArrayList<Ingredient> selectAllIngredients() {
        String sqlQuery = "select * from " + DbHelper.TABLE_INGREDIENTS;
        return fetchIngredients(sqlQuery);
    }

    private ArrayList<Ingredient> fetchIngredients(String sqlQuery) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ingredients.add(getIngredientFromCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        close();

        return ingredients;
    }

    private Ingredient getIngredientFromCursor(Cursor cursor) {
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
