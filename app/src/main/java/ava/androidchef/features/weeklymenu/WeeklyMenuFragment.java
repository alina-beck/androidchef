package ava.androidchef.features.weeklymenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ava.androidchef.models.Recipe;

public class WeeklyMenuFragment extends ListFragment {

    private WeeklyMenuPresenter presenter;

    public WeeklyMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.presenter = new WeeklyMenuPresenter(this);
        presenter.onFragmentCreate();
    }

    public void displayWeeklyMenu(ArrayList<Recipe> recipes) {
        ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, recipes);
        setListAdapter(adapter);
    }
}
