package ava.androidchef.features.weeklymenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.recipe.Recipe;

public class WeeklyMenuFragment extends ListFragment {

    private WeeklyMenuPresenter presenter;

    public WeeklyMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String buttonClicked = getActivity().getIntent().getExtras().getString("buttonClicked");

        this.presenter = new WeeklyMenuPresenter(this);
        presenter.onFragmentCreate(buttonClicked);
    }

    public void displayWeeklyMenu(ArrayList<Recipe> recipes) {
        WeeklyMenuArrayAdapter adapter = new WeeklyMenuArrayAdapter(getActivity(), R.layout.list_item_replaceable_recipe, recipes, this);
        setListAdapter(adapter);
    }

    public void replaceButtonClicked(ArrayList<Recipe> currentRecipes, int index) {
        presenter.onReplaceButtonClick(currentRecipes, index);
    }
}
