package ava.androidchef.features.allrecipes;

import ava.androidchef.models.recipe.RecipeDAO;

public class AllRecipesPresenter {

    private AllRecipesFragment fragment;
    private RecipeDAO recipeDAO;

    public AllRecipesPresenter(AllRecipesFragment fragment) {
        this.fragment = fragment;
        this.recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
    }

    public void onFragmentCreate() {
        fragment.displayAllRecipes(recipeDAO.selectAllRecipes());
    }

    public void onDeleteButtonClick(long id) {
        fragment.displayToast(recipeDAO.deleteRecipe(id));
    }


}
