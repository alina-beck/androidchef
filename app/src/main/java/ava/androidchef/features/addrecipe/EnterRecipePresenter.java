package ava.androidchef.features.addrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class EnterRecipePresenter {

    private EnterRecipeFragment fragment;

    public EnterRecipePresenter(EnterRecipeFragment fragment) {
        this.fragment = fragment;
    }

    public void onButtonClick(LinkedHashMap<Ingredient, Integer> savedIngredients) {
        String title = fragment.getTitleInput();
        String instructions = fragment.getInstructionsInput();
        Recipe recipe = new Recipe (title, savedIngredients, instructions);

        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());

        boolean didSave = (recipeDAO.insertRecipe(recipe) != -1);
        fragment.saveComplete(didSave);
    }
}
