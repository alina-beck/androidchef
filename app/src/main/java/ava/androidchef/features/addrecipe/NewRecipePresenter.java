package ava.androidchef.features.addrecipe;

import java.util.ArrayList;

import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class NewRecipePresenter {

    private NewRecipeFragment fragment;

    public NewRecipePresenter(NewRecipeFragment fragment) {
        this.fragment = fragment;
    }

    public void onButtonClick() {
        String title = fragment.getRecipeInput();
        ArrayList<ArrayList<String>> ingredients = fragment.getIngredientInput();

        Recipe newRecipe = new Recipe (title);
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity().getApplicationContext());

        boolean didSave = recipeDAO.insertRecipe(newRecipe);
        fragment.saveComplete(didSave);

    }
}
