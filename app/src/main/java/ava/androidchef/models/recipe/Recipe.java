package ava.androidchef.models.recipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;

public class Recipe {
    private int id;
    private String title;
    private LinkedHashMap<Ingredient, Integer> ingredients;

    public Recipe(String title) {
        this(-1, title);
    }

    public Recipe(int id, String title) {
        this.id = id;
        this.title = title;
        this.ingredients = new LinkedHashMap<>();
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

    public LinkedHashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedHashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return title;
    }
}
