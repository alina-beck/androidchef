package ava.androidchef.features.allrecipes;

import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;

public class AllRecipesPresenter {

    private AllRecipesFragment fragment;
    private RecipeDAO recipeDAO;

    public AllRecipesPresenter(AllRecipesFragment fragment) {
        this.fragment = fragment;
        this.recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
    }

    public void onFragmentCreate() {
        if (recipeDAO.getNumberOfRecipes() == 0) {
            fragment.displayNoRecipes();
        }
        fragment.displayAllRecipes(recipeDAO.selectAllRecipes());
    }

    public void onDeleteButtonClick(Recipe recipe) {
        boolean isDeleted = recipeDAO.deleteRecipe(recipe.getId());

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        if (menuDAO.menuContainsRecipe(recipe)) {
            menuDAO.deleteRecipeFromMenu(recipe);
            ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
            shoppingListDAO.removeOldIngredients(recipe);
        }

        fragment.displayToast(isDeleted);
    }


}
