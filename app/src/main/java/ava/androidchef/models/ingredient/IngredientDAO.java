package ava.androidchef.models.ingredient;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ava.androidchef.database.DbHelper;

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

    public boolean insertIngredient(Ingredient ingredient) {
        open();
        ContentValues values = prepareContentValues(ingredient);
        long result = db.insert(DbHelper.TABLE_INGREDIENTS, null, values);

        return (result != -1);
    }

    private ContentValues prepareContentValues(Ingredient ingredient) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.COL_INGREDIENT_NAME, ingredient.getName());
        values.put(DbHelper.COL_INGREDIENT_UNIT, ingredient.getUnit());

        return values;
    }
}
