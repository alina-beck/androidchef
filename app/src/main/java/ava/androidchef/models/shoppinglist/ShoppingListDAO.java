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
        LinkedHashMap<Ingredient, Integer> ingredientsWithoutDuplicates = new LinkedHashMap<>();
        ArrayList<Recipe> recipes = menu.getRecipes();

        for (Recipe recipe : recipes) {
            LinkedHashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
            for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
                if (ingredientsWithoutDuplicates.containsKey(entry.getKey())) {
                    int updatedValue = ingredientsWithoutDuplicates.get(entry.getKey()) + entry.getValue();
                    ingredientsWithoutDuplicates.put(entry.getKey(), updatedValue);
                }
                else {
                    ingredientsWithoutDuplicates.put(entry.getKey(), entry.getValue());
                }
            }
        }

        for (Map.Entry<Ingredient, Integer> entry : ingredientsWithoutDuplicates.entrySet()) {
            ShoppingListItem item = new ShoppingListItem(entry.getKey(), entry.getValue(), false);
            shoppingList.add(item);
        }

        return shoppingList;
    }

    public void updateShoppingList(Menu menu) {
        //TODO: check for existing values so that shopping list is not overwritten when recipe is replaced
        insertShoppingList(createShoppingListFromMenu(menu));
    }

    public void updateItemInShoppingList(ShoppingListItem item) {
        ShoppingList shoppingList = getShoppingList();
        ShoppingListItem updatedItem = new ShoppingListItem(item);
        updatedItem.setBought(!item.isBought());

        for (ShoppingListItem listItem : shoppingList) {
            if (listItem.equals(item)) {
                shoppingList.set(shoppingList.indexOf(listItem), updatedItem);
                break;
            }
        }
        insertShoppingList(shoppingList);
    }

}
