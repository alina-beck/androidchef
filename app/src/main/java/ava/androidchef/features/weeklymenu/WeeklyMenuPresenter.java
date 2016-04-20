package ava.androidchef.features.weeklymenu;

import java.util.ArrayList;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class WeeklyMenuPresenter {

    private WeeklyMenuFragment fragment;

    public WeeklyMenuPresenter(WeeklyMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate(String buttonClicked) {

        ArrayList<Recipe> recipes;
        WeeklyMenuDAO weeklyMenuDAO = new WeeklyMenuDAO(fragment.getActivity());

        switch (buttonClicked) {
            case "createMenu":
                RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());
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
}
