package ava.androidchef.models.ingredient;

import java.util.ArrayList;

import ava.androidchef.models.recipe.Recipe;

public class Ingredient {

    private int id;
    private String name;
    private String unit;
    private ArrayList<Recipe> recipes;

    public Ingredient(String name, String unit) {
        this(-1, name, unit, new ArrayList<Recipe>());
    }

    public Ingredient(int id, String name, String unit, ArrayList<Recipe> recipes) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.recipes = recipes;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return name;
    }
}
