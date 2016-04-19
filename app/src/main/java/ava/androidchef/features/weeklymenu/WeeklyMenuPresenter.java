package ava.androidchef.features.weeklymenu;

import android.content.Context;
import android.content.SharedPreferences;

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
        SharedPreferences currentMenu = fragment.getActivity().getPreferences(Context.MODE_PRIVATE);

        switch (buttonClicked) {
            case "createMenu":
                RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());
                recipes = recipeDAO.getRandomMenu(7);

                fragment.displayWeeklyMenu(recipes);

                SharedPreferences.Editor editor = currentMenu.edit();

                for (int i = 0; i < recipes.size(); i++) {
                    String stringMenu = recipes.get(i).getId() + "," + recipes.get(i).getTitle();

                    editor.putString(Integer.toString(i), stringMenu);
                    editor.apply();
                }
                break;
            case "currentMenu":
                recipes = new ArrayList<>();
                recipes.add(new Recipe("testworks"));

                fragment.displayWeeklyMenu(recipes);
                break;
        }

    }


}
