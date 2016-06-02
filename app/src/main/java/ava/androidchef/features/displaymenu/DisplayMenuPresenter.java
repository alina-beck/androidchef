package ava.androidchef.features.displaymenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;

public class DisplayMenuPresenter {

    private DisplayMenuFragment fragment;

    public DisplayMenuPresenter(DisplayMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate() {

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        Menu menu = menuDAO.getMenu();
        if (menu != null) {
            fragment.displayMenu(menu);
        }
        else {
            fragment.displayNoMenu();
        }
    }

    public void onReplaceButtonClick(ArrayList<Recipe> currentRecipes, int index) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        Recipe newRecipe = recipeDAO.selectRandomRecipe(currentRecipes);
        Recipe oldRecipe = currentRecipes.get(index);

        if (newRecipe != null) {
            MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
            Menu menu = menuDAO.getMenu();
            currentRecipes.set(index, newRecipe);
            menu.setRecipes(currentRecipes);
            menuDAO.insertMenu(menu);
            fragment.displayMenu(menu);

            ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
            shoppingListDAO.updateShoppingListWithRecipe(oldRecipe, newRecipe);
        }

        else fragment.alertNoMoreRecipes();
    }
}
