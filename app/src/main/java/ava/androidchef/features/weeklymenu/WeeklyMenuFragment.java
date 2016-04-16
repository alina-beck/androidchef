package ava.androidchef.features.weeklymenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ava.androidchef.models.MockData;
import ava.androidchef.models.Recipe;
import ava.androidchef.models.RecipeDAO;

public class WeeklyMenuFragment extends ListFragment {

    private ArrayList<Recipe> recipes;

    public WeeklyMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecipeDAO recipeDAO = new RecipeDAO(getActivity());
        recipes = recipeDAO.fetchWeeklyMenu();

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recipes);
        setListAdapter(adapter);
    }
}
