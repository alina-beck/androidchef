package ava.androidchef.utils;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;

public abstract class BaseRecipePresenter {

    public abstract void saveRecipeButtonClicked();
    public abstract void ingredientsSaved(LinkedHashMap<Ingredient, Integer> ingredients);
    public abstract void viewCreated();
}
