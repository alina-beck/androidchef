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
        LinkedHashMap<Ingredient, Integer> ingredientsFromUser = new LinkedHashMap<>();
        ArrayList<ArrayList<String>> ingredients = fragment.getIngredientInput();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = new Ingredient(ingredients.get(i).get(0), ingredients.get(i).get(1));
            ingredientsFromUser.put(ingredient, Integer.parseInt(ingredients.get(i).get(2)));
        }

        return ingredientDAO.insertIngredients(ingredientsFromUser);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredientDAO.selectAllIngredients();
    }
}
