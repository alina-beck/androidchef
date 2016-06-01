package ava.androidchef.features.displaymenu;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.recipe.Recipe;

public class DisplayMenuFragment extends ListFragment {

    private DisplayMenuPresenter presenter;

    public DisplayMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_menu, container, false);

        this.presenter = new DisplayMenuPresenter(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onFragmentCreate();
    }

    public void displayMenu(Menu menu) {
        getActivity().setTitle(menu.getTitle());

        DisplayMenuArrayAdapter adapter = new DisplayMenuArrayAdapter(getActivity(), R.layout.list_item_replaceable_recipe, menu.getRecipes(), this);
        setListAdapter(adapter);
    }

    public void replaceButtonClicked(ArrayList<Recipe> currentRecipes, int index) {
        presenter.onReplaceButtonClick(currentRecipes, index);
    }

    public void recipeSelected(Recipe selectedRecipe) {
        ((DisplayMenuActivity) getActivity()).recipeSelected(selectedRecipe);
    }

    public void alertNoMoreRecipes() {
        Toast.makeText(getActivity(), "No more recipes in database", Toast.LENGTH_LONG).show();
    }

    public void displayNoMenu() {
        TextView noRecipes = (TextView) getView().findViewById(R.id.text_menu_title);
        noRecipes.setText(R.string.no_menu);
    }
}
