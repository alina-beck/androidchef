package ava.androidchef.models.shoppinglist;

import ava.androidchef.models.ingredient.Ingredient;

public class ShoppingListItem {

    private Ingredient ingredient;
    private int amount;
    private boolean isBought;

    public ShoppingListItem(Ingredient ingredient, int amount, boolean isBought) {
        this.ingredient = ingredient;
        this.amount = amount;
        this.isBought = isBought;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isBought() {
        return isBought;
    }
}
