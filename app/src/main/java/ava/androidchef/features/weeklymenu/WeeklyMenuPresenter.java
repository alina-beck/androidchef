package ava.androidchef.features.weeklymenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class WeeklyMenuPresenter {

    private WeeklyMenuFragment fragment;
    private RecipeDAO recipeDAO;
    private MenuDAO menuDAO;

    public WeeklyMenuPresenter(WeeklyMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate(String buttonClicked) {

        ArrayList<Recipe> recipes;
        this.menuDAO = MenuDAO.getInstance(fragment.getActivity());
        this.recipeDAO = RecipeDAO.getInstance(fragment.getActivity());

        switch (buttonClicked) {
            case "createMenu":
                recipes = recipeDAO.selectRandomMenu(7);
                Menu menu = new Menu("newMenu", recipes);

                fragment.displayWeeklyMenu(recipes);
                menuDAO.saveMenu(menu);
                break;
            case "currentMenu":
                recipes = menuDAO.getMenu();
                fragment.displayWeeklyMenu(recipes);
                break;
        }
    }

    public void onReplaceButtonClick(ArrayList<Recipe> currentRecipes, int index) {
        Recipe newRecipe = recipeDAO.selectRandomRecipe(currentRecipes);

        Menu menu = new Menu("newMenu", currentRecipes);
        currentRecipes.set(index, newRecipe);
        fragment.displayWeeklyMenu(currentRecipes);
        menuDAO.saveMenu(menu);
    }
}
