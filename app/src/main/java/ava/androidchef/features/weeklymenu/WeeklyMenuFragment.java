package ava.androidchef.features.weeklymenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ava.androidchef.models.MockData;
import ava.androidchef.models.Recipe;

public class WeeklyMenuFragment extends ListFragment {

    private ArrayList<Recipe> recipes;

    public WeeklyMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipes = MockData.getInstance(getActivity()).getRecipes();

        ArrayAdapter<Recipe> adapter = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, recipes);
        setListAdapter(adapter);
    }
}
