package ava.androidchef.models.shoppinglist;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.recipe.Recipe;

public class ShoppingList extends ArrayList<ShoppingListItem> {

    public static ShoppingList createShoppingListFromMenu(Menu menu) {
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

}
