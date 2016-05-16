package ava.androidchef.features.createmenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class CreateMenuPresenter {

    private CreateMenuFragment fragment;

    public CreateMenuPresenter(CreateMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onCreateButtonClicked(String title, int length) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        ArrayList<Recipe> recipes = recipeDAO.selectRandomMenu(length);

        Menu newMenu = new Menu(title, length, recipes);
        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        menuDAO.insertMenu(newMenu);

        fragment.displayMenu();
    }

}
