package ava.androidchef.features.allrecipes;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ava.androidchef.features.weeklymenu.WeeklyMenuPresenter;
import ava.androidchef.models.Recipe;

public class AllRecipesFragment extends ListFragment {

    private AllRecipesPresenter presenter;

    public AllRecipesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.presenter = new AllRecipesPresenter(this);
        presenter.onFragmentCreate();
    }

    public void displayAllRecipes(ArrayList<Recipe> recipes) {
        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recipes);
        setListAdapter(adapter);
    }
}
