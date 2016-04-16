package ava.androidchef.features.weeklymenu;

import java.util.ArrayList;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class WeeklyMenuPresenter {

    private WeeklyMenuFragment fragment;
    private ArrayList<Recipe> recipes;

    public WeeklyMenuPresenter(WeeklyMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate() {
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());
        recipes = recipeDAO.getRandomMenu(7);

        fragment.displayWeeklyMenu(recipes);
    }


}
