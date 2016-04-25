package ava.androidchef.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static DbHelper singletonInstance;

    public static final String DATABASE_NAME = "chef.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECIPES = "recipes";

    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_TITLE = "recipe_title";

    public static final String TABLE_INGREDIENTS = "ingredients";

    public static final String COL_INGREDIENT_ID = "ingredient_id";
    public static final String COL_INGREDIENT_NAME = "ingredient_name";
    public static final String COL_INGREDIENT_UNIT = "ingredient_unit";

    public static final String TABLE_RECIPES_INGREDIENTS = "recipes_ingredients";

    public static final String COL_RI_ID = "recipes_ingredients_id";
    public static final String COL_RI_RECIPE_ID = "ri_recipe_id";
    public static final String COL_RI_INGREDIENT_ID = "ri_ingredient_id";
    public static final String COL_RI_AMOUNT = "ri_amount";

    // database creation SQL syntax
    public static final String CREATE_DATABASE =
            "create table " + TABLE_RECIPES + "(" +
            COL_RECIPE_ID + " integer primary key autoincrement, " +
            COL_RECIPE_TITLE + " text not null); " +
            "create table " + TABLE_INGREDIENTS + "(" +
            COL_INGREDIENT_ID + " integer primary key autoincrement, " +
            COL_INGREDIENT_NAME + " text not null, " +
            COL_INGREDIENT_UNIT + " text not null); " +
            "create table " + TABLE_RECIPES_INGREDIENTS + "(" +
            COL_RI_ID + " integer primary key, " +
            COL_RI_RECIPE_ID + " integer, " +
            COL_RI_INGREDIENT_ID + " integer, " +
            COL_RI_AMOUNT + " integer not null, " +
            "foreign key(" + COL_RI_RECIPE_ID + ") references " + TABLE_RECIPES + "(" + COL_RECIPE_ID + "), " +
            "foreign key(" + COL_RI_INGREDIENT_ID + ") references " + TABLE_INGREDIENTS + "(" + COL_INGREDIENT_ID + "));";

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new DbHelper(context.getApplicationContext());
        }
        return singletonInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_RECIPES);
        onCreate(db);
    }
}
