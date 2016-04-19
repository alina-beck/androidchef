package ava.androidchef.features.weeklymenu;

import java.util.ArrayList;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class WeeklyMenuPresenter {

    private WeeklyMenuFragment fragment;
    private String buttonClicked;
    private ArrayList<Recipe> recipes;

    public WeeklyMenuPresenter(WeeklyMenuFragment fragment, String buttonClicked) {
        this.fragment = fragment;
        this.buttonClicked = buttonClicked;
    }

    public void onFragmentCreate() {

        switch (buttonClicked) {
            case "createMenu":
                RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());
                this.recipes = recipeDAO.getRandomMenu(7);

                fragment.displayWeeklyMenu(recipes);
                break;
            case "currentMenu":
                this.recipes = new ArrayList<>();
                recipes.add(new Recipe("testworks"));

                fragment.displayWeeklyMenu(recipes);
                break;
        }

    }


}
