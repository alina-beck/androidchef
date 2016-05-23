package ava.androidchef.models.menu;

import java.util.ArrayList;

import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.shoppinglist.ShoppingListItem;

public class Menu {

    private String title;
    private int numberOfDishes;
    private ArrayList<Recipe> recipes;

    public Menu(String title, int numberOfDishes) {
        this(title, numberOfDishes, new ArrayList<Recipe>());
    }

    public Menu(String title, ArrayList<Recipe> recipes) {
        this(title, recipes.size(), recipes);
    }

    public Menu(String title, int numberOfDishes, ArrayList<Recipe> recipes) {
        this.title = title;
        this.numberOfDishes = numberOfDishes;
        this.recipes = recipes;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public String toString() {
        return title;
    }
}
