package ava.androidchef.models.ingredient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import ava.androidchef.database.DbHelper;
import ava.androidchef.models.recipe.Recipe;

public class IngredientDAO {

    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public IngredientDAO(Context context) {
        dbHelper = DbHelper.getInstance(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void close() {
        dbHelper.close();
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
        open();
        ContentValues values = prepareContentValues(ingredient);
        db.insert(DbHelper.TABLE_INGREDIENTS, null, values);

        Cursor cursor = db.rawQuery("select * from " + DbHelper.TABLE_INGREDIENTS + " order by " + DbHelper.COL_INGREDIENT_ID + " desc limit 1", null);
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String unit = cursor.getString(2);

        cursor.close();
        close();

        return new Ingredient(id, name, unit);
    }

    private ContentValues prepareContentValues(Ingredient ingredient) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_INGREDIENT_NAME, ingredient.getName());
        values.put(DbHelper.COL_INGREDIENT_UNIT, ingredient.getUnit());

        return values;
    }
}
