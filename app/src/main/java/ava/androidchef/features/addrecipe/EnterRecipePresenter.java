package ava.androidchef.features.addrecipe;

import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class EnterRecipePresenter {

    private EnterRecipeFragment fragment;

    public EnterRecipePresenter(EnterRecipeFragment fragment) {
        this.fragment = fragment;
    }

    public void onButtonClick() {
        String title = fragment.getRecipeInput();
        Recipe recipe = new Recipe (title);

        // recipe.setIngredients(updatedIngredients);
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());

        boolean didSave = (recipeDAO.insertRecipe(recipe) != -1);
        fragment.saveComplete(didSave);
    }
}
