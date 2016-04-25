package ava.androidchef.models.ingredient;

import android.content.ContentValues;
import android.content.Context;
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

    public ArrayList<Long> insertIngredients(Recipe recipe) {
        ArrayList<Long> ingredientIds = new ArrayList<>();

        LinkedHashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
        for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
            ingredientIds.add(insertIngredient(entry.getKey()));
        }

        return ingredientIds;
    }

    public long insertIngredient(Ingredient ingredient) {
        open();
        ContentValues values = prepareContentValues(ingredient);
        long result = db.insert(DbHelper.TABLE_INGREDIENTS, null, values);
        close();

        return result;
    }

    private ContentValues prepareContentValues(Ingredient ingredient) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_INGREDIENT_NAME, ingredient.getName());
        values.put(DbHelper.COL_INGREDIENT_UNIT, ingredient.getUnit());

        return values;
    }
}
