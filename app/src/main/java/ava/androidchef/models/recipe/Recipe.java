package ava.androidchef.models.recipe;

import java.util.HashMap;

import ava.androidchef.models.ingredient.Ingredient;

public class Recipe {
    private int id;
    private String title;
    private HashMap<Integer, Ingredient> ingredients;

    public Recipe(String title) {
        this(-1, title);
    }

    public Recipe(int id, String title) {
        this.id = id;
        this.title = title;
        this.ingredients = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
