package ava.androidchef.models.recipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;

public class Recipe {
    private int id;
    private String title;
    private LinkedHashMap<Ingredient, Integer> ingredients;
    private String instructions;

    public Recipe(String title, String instructions) {
        this(-1, title, new LinkedHashMap<Ingredient, Integer>(), instructions);
    }

    public Recipe(int id, String title, String instructions) {
        this(id, title, new LinkedHashMap<Ingredient, Integer>(), instructions);
    }

    public Recipe(int id, String title, LinkedHashMap<Ingredient, Integer> ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return title;
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

    public String getInstructions() {
        return instructions;
    }
}
