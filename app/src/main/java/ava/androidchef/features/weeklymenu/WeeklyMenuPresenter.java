package ava.androidchef.features.weeklymenu;

import java.util.ArrayList;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class WeeklyMenuPresenter {

    private WeeklyMenuFragment fragment;
    private RecipeDAO recipeDAO;
    private WeeklyMenuDAO weeklyMenuDAO;

    public WeeklyMenuPresenter(WeeklyMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate(String buttonClicked) {

        ArrayList<Recipe> recipes;
        this.weeklyMenuDAO = new WeeklyMenuDAO(fragment.getActivity());
        this.recipeDAO = new RecipeDAO(fragment.getActivity());

        switch (buttonClicked) {
            case "createMenu":
                recipes = recipeDAO.getRandomMenu(7);

                fragment.displayWeeklyMenu(recipes);
                weeklyMenuDAO.saveMenu(recipes);
                break;
            case "currentMenu":
                recipes = weeklyMenuDAO.getMenu();
                fragment.displayWeeklyMenu(recipes);
                break;
        }
    }

    public void onReplaceButtonClick(ArrayList<Recipe> currentRecipes, int index) {
        Recipe newRecipe = recipeDAO.getRandomRecipe(currentRecipes);

        currentRecipes.set(index, newRecipe);
        fragment.displayWeeklyMenu(currentRecipes);
        weeklyMenuDAO.saveMenu(currentRecipes);
    }
}
