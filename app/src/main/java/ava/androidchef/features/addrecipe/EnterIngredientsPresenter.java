package ava.androidchef.features.addrecipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;

public class EnterIngredientsPresenter {

    private EnterIngredientsFragment fragment;
    private IngredientDAO ingredientDAO;

    public EnterIngredientsPresenter(EnterIngredientsFragment fragment) {
        this.fragment = fragment;
        this.ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
    }

    public LinkedHashMap<Ingredient, Integer> saveIngredients() {
        LinkedHashMap<Ingredient, Integer> ingredientsFromUser = fragment.getIngredientInput();
        return ingredientDAO.insertIngredients(ingredientsFromUser);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredientDAO.selectAllIngredients();
    }
}
