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

    public ShoppingListItem(ShoppingListItem copiedItem) {
        this.ingredient = copiedItem.getIngredient();
        this.amount = copiedItem.getAmount();
        this.isBought = copiedItem.isBought();
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

    public void setBought(boolean isBought) {
        this.isBought = isBought;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingListItem that = (ShoppingListItem) o;

        return !(ingredient != null ? !ingredient.equals(that.ingredient) : that.ingredient != null);

    }

    @Override
    public int hashCode() {
        return ingredient != null ? ingredient.hashCode() : 0;
    }
}
