package ava.androidchef.models.menu;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Map;

import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.shoppinglist.ShoppingList;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;

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
        this.sharedPreferences = context.getSharedPreferences("menu_preferences", Context.MODE_PRIVATE);
    }

    public void insertMenu(Menu menu) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String menuAsJson = gson.toJson(menu);

        editor.putString("current_menu", menuAsJson);
        editor.apply();
    }

    public Menu getMenu() {
        String menuAsJson = sharedPreferences.getString("current_menu", "no menu");
        if (menuAsJson.equals("no menu")) {
            return null;
        }
        else {
        Gson gson = new Gson();
        return gson.fromJson(menuAsJson, Menu.class);
        }
    }

    public void updateRecipeInMenu(Recipe originalRecipe, Recipe updatedRecipe) {
        Menu updatingMenu = getMenu();
        ArrayList<Recipe> recipes = updatingMenu.getRecipes();
        for (Recipe r : recipes) {
            if (r.getId() == originalRecipe.getId()) {
                recipes.set(recipes.indexOf(r), updatedRecipe);
                updatingMenu.setRecipes(recipes);
                insertMenu(updatingMenu);
            }
        }
    }
}
