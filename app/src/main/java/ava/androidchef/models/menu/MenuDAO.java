package ava.androidchef.models.menu;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

import ava.androidchef.models.recipe.Recipe;

public class MenuDAO {

    private static MenuDAO singletonInstance;
    private SharedPreferences sharedPreferences;

    public static MenuDAO getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new MenuDAO(context.getApplicationContext());
        }
        return singletonInstance;
    }

    private MenuDAO(Context context) {
        this.sharedPreferences = context.getSharedPreferences("currentMenu", Context.MODE_PRIVATE);
    }

    public void saveMenu(Menu menu) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<Recipe> recipes = menu.getRecipes();
        for (int i = 0; i < recipes.size(); i++) {
            String stringMenu = recipes.get(i).getId() + "," + recipes.get(i).getTitle();

            editor.putString(Integer.toString(i), stringMenu);
            editor.apply();
        }
    }

    public ArrayList<Recipe> getMenu() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        Map<String, ?> map = sharedPreferences.getAll();
        for (int i = 0; i < map.size(); i++) {
            String stringRecipe = map.get(Integer.toString(i)).toString();
            String[] recipe = stringRecipe.split(",", 2);
            recipes.add(new Recipe(Integer.parseInt(recipe[0]), recipe[1]));
        }
        return recipes;
    }
}
