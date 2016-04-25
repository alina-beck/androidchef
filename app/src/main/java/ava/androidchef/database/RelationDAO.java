package ava.androidchef.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class RelationDAO {

    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private Context context;

    public RelationDAO(Context context) {
        this.dbHelper = DbHelper.getInstance(context);
        this.context = context;
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void close() {
        dbHelper.close();
    }

    public boolean insertNewRelation(Recipe recipe) {
        long recipeId = insertRecipe(recipe);
        ArrayList<Long> ingredientIds = insertIngredients(recipe);
        return insertRelation(recipeId, ingredientIds);
    }

    private long insertRecipe(Recipe recipe) {
        RecipeDAO recipeDAO = new RecipeDAO(context);
        return recipeDAO.insertRecipe(recipe);
    }

    private ArrayList<Long> insertIngredients(Recipe recipe) {
        IngredientDAO ingredientDAO = new IngredientDAO(context);
        return ingredientDAO.insertIngredients(recipe);
    }

    private boolean insertRelation(long recipeId, ArrayList<Long> ingredientIds) {
        open();
        boolean result = false;
        for (int i = 0; i < ingredientIds.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DbHelper.COL_RI_ID, recipeId + ingredientIds.get(i));
            values.put(DbHelper.COL_RI_RECIPE_ID, recipeId);
            values.put(DbHelper.COL_RI_INGREDIENT_ID, ingredientIds.get(i));
            if (db.insert(DbHelper.TABLE_RECIPES_INGREDIENTS, null, values) != -1) {
                result = true;
            }
        }
        close();

        return result;
    }
}
