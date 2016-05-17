package ava.androidchef.features.displaymenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class DisplayMenuPresenter {

    private DisplayMenuFragment fragment;

    public DisplayMenuPresenter(DisplayMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate() {

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        Menu menu = menuDAO.getMenu();
        fragment.displayMenu(menu);
        }

    public void onReplaceButtonClick(ArrayList<Recipe> currentRecipes, int index) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        Recipe newRecipe = recipeDAO.selectRandomRecipe(currentRecipes);

        if (newRecipe != null) {
            MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
            Menu menu = menuDAO.getMenu();
            currentRecipes.set(index, newRecipe);
            menu.setRecipes(currentRecipes);
            fragment.displayMenu(menu);

            menuDAO.insertMenu(menu);
        }

        else fragment.alertNoMoreRecipes();
    }
}
