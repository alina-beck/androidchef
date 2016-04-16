package ava.androidchef.features.allrecipes;

import java.util.ArrayList;

import ava.androidchef.features.weeklymenu.WeeklyMenuFragment;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class AllRecipesPresenter {

    private AllRecipesFragment fragment;
    private ArrayList<Recipe> recipes;

    public AllRecipesPresenter(AllRecipesFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate() {
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());
        recipes = recipeDAO.getAllRecipes();

        fragment.displayAllRecipes(recipes);
    }


}
