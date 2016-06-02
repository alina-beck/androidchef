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
            Menu menu = menuDAO.getMenu();

            if (menu != null) {
                shoppingList = ShoppingList.createShoppingListFromMenu(menuDAO.getMenu());
                insertShoppingList(shoppingList);
            }
            else {
                shoppingList = null;
            }
        }
        else {
            Gson gson = new Gson();
            shoppingList = gson.fromJson(shoppingListAsJson, ShoppingList.class);
        }

        return shoppingList;
    }

    public void updateShoppingListWithRecipe(Recipe oldRecipe, Recipe newRecipe) {
        removeOldIngredients(oldRecipe);
        addNewIngredients(newRecipe);
    }

    private void removeOldIngredients(Recipe oldRecipe) {
        ShoppingList shoppingList = getShoppingList();
        LinkedHashMap<Ingredient, Integer> oldIngredients = oldRecipe.getIngredients();

        for (Map.Entry<Ingredient, Integer> entry : oldIngredients.entrySet()) {
            for (ShoppingListItem item : shoppingList) {
                if (item.getIngredient().equals(entry.getKey())) {
                    int newAmount = item.getAmount() - entry.getValue();
                    if (newAmount != 0) {
                        item.setAmount(newAmount);
                    }
                    else {
                        shoppingList.remove(item);
                    }
                    break;
                }
            }
        }
        insertShoppingList(shoppingList);
    }

    private void addNewIngredients(Recipe newRecipe) {
        ShoppingList shoppingList = getShoppingList();
        LinkedHashMap<Ingredient, Integer> newIngredients = newRecipe.getIngredients();

        for (Map.Entry<Ingredient, Integer> entry : newIngredients.entrySet()) {
            ShoppingListItem correspondingItem = findIngredientOnShoppingList(entry.getKey(), shoppingList);

            if (correspondingItem != null) {
                int newAmount = correspondingItem.getAmount() + entry.getValue();
                correspondingItem.setAmount(newAmount);
                correspondingItem.setBought(false);
            }

            else {
                correspondingItem = new ShoppingListItem(entry.getKey(), entry.getValue(), false);
                shoppingList.add(correspondingItem);
            }
        }
        insertShoppingList(shoppingList);
    }

    public ShoppingListItem findIngredientOnShoppingList(Ingredient ingredient, ShoppingList shoppingList) {
        for (ShoppingListItem item : shoppingList) {
            if (item.getIngredient().equals(ingredient)) {
                return item;
            }
        }
        return null;
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
