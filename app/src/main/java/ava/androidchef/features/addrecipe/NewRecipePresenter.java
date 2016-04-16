package ava.androidchef.features.addrecipe;

import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class NewRecipePresenter {

    private NewRecipeFragment fragment;

    public NewRecipePresenter(NewRecipeFragment fragment) {
        this.fragment = fragment;
    }

    public void onButtonClick() {
        String title = fragment.getUserInput();
        Recipe newRecipe = new Recipe (title);
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity().getApplicationContext());

        boolean didSave = recipeDAO.insertRecipe(newRecipe);
        fragment.saveComplete(didSave);

    }
}
