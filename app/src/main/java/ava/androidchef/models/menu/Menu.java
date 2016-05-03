package ava.androidchef.models.menu;

import java.util.ArrayList;

import ava.androidchef.models.recipe.Recipe;

public class Menu {

    private int id;
    private String title;
    private int numberOfDishes;
    private ArrayList<Recipe> recipes;

    public Menu(String title, int numberOfDishes) {
        this(-1, title, numberOfDishes, new ArrayList<Recipe>());
    }

    public Menu(String title, ArrayList<Recipe> recipes) {
        this(-1, title, recipes.size(), recipes);
    }

    public Menu(int id, String title, int numberOfDishes, ArrayList<Recipe> recipes) {
        this.id = id;
        this.title = title;
        this.numberOfDishes = numberOfDishes;
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
}
