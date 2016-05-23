package ava.androidchef.models.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;

public class ShoppingListDAO {

    private static ShoppingListDAO singletonInstance;
    private Context context;
    private SharedPreferences sharedPreferences;

    public static ShoppingListDAO getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new ShoppingListDAO(context);
        }
        return singletonInstance;
    }

    private ShoppingListDAO(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("shopping_list_preferences", Context.MODE_PRIVATE);
    }

    public void insertShoppingList(ShoppingList shoppingList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String shoppingListAsJson = gson.toJson(shoppingList);

        editor.putString("current_shopping_list", shoppingListAsJson);
        editor.apply();
    }

    public ShoppingList getShoppingList() {
        String shoppingListAsJson = sharedPreferences.getString("current_shopping_list", "no list");
        ShoppingList shoppingList;

        if (shoppingListAsJson.equals("no list")) {
            MenuDAO menuDAO = MenuDAO.getInstance(context);

            shoppingList = createShoppingListFromMenu(menuDAO.getMenu());
            insertShoppingList(shoppingList);
        }
        else {
            Gson gson = new Gson();
            shoppingList = gson.fromJson(shoppingListAsJson, ShoppingList.class);
        }

        return shoppingList;
    }

    private ShoppingList createShoppingListFromMenu(Menu menu) {
        ShoppingList shoppingList = new ShoppingList();
        LinkedHashMap<String, Integer> ingredientsWithoutDuplicates = new LinkedHashMap<>();
        ArrayList<Recipe> recipes = menu.getRecipes();

        for (Recipe recipe : recipes) {
            LinkedHashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
            System.out.println("ingredients in recipe: " + ingredients);
            for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
                System.out.println("ingredient: " + entry.getKey() + " und amount: " + entry.getValue());
                if (ingredientsWithoutDuplicates.containsKey(entry.getKey().getName())) {
                    System.out.println("ingredient is already in list");
                    int updatedValue = ingredientsWithoutDuplicates.get(entry.getKey().getName()) + entry.getValue();
                    System.out.println("new value: " + updatedValue);
                    ingredientsWithoutDuplicates.remove(entry.getKey().getName());
                    ingredientsWithoutDuplicates.put(entry.getKey().getName(), updatedValue);
                }
                else {
                    System.out.println("ingredient not yet in list, putting it now");
                    ingredientsWithoutDuplicates.put(entry.getKey().getName(), entry.getValue());
                }
            }
        }

        for (Map.Entry<String, Integer> entry : ingredientsWithoutDuplicates.entrySet()) {
            IngredientDAO ingredientDAO = IngredientDAO.getInstance(context);
            Ingredient ingredient = ingredientDAO.selectIngredientByName(entry.getKey());
            ShoppingListItem item = new ShoppingListItem(ingredient, entry.getValue(), false);
            shoppingList.add(item);
        }

        return shoppingList;
    }

    public void updateShoppingList(Menu menu) {
        //TODO: check for existing values so that shopping list is not overwritten when recipe is replaced
        insertShoppingList(createShoppingListFromMenu(menu));
    }

}
