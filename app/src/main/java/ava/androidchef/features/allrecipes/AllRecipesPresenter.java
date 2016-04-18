package ava.androidchef.features.allrecipes;

import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class AllRecipesPresenter {

    private AllRecipesFragment fragment;
    private RecipeDAO recipeDAO;

    public AllRecipesPresenter(AllRecipesFragment fragment) {
        this.fragment = fragment;
        this.recipeDAO = new RecipeDAO(fragment.getActivity());
    }

    public void onFragmentCreate() {
        fragment.displayAllRecipes(recipeDAO.getAllRecipes());
    }

    public void onSaveButtonClick(int id, String newTitle) {
        Recipe recipe = new Recipe(id, newTitle);
        fragment.makeToast(recipeDAO.updateRecipe(recipe));
    }

    public void onDeleteButtonClick(int id) {
        fragment.makeToast(recipeDAO.deleteRecipe(id));
    }


}
