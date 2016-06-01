package ava.androidchef.features.allrecipes;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.recipe.Recipe;

public class AllRecipesFragment extends ListFragment {

    private AllRecipesPresenter presenter;

    public AllRecipesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_recipes, container, false);

        this.presenter = new AllRecipesPresenter(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.onFragmentCreate();
    }

    public void displayAllRecipes(ArrayList<Recipe> recipes) {
        AllRecipesArrayAdapter adapter = new AllRecipesArrayAdapter(getActivity(), R.layout.list_item_editable_recipe, recipes, this);
        setListAdapter(adapter);
    }

    public void displayNoRecipes() {
        TextView noRecipes = (TextView) getView().findViewById(R.id.text_no_recipes);
        noRecipes.setText(R.string.no_recipes);
    }

    public void displayToast(boolean update) {
        if (update) {
            Toast.makeText(getActivity(), R.string.recipe_deleted, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), R.string.saving_error, Toast.LENGTH_LONG).show();
        }
    }

    public void recipeClicked(Recipe recipe) {
        ((AllRecipesActivity) getActivity()).onRecipeSelected(recipe);
    }

    public void deleteButtonClicked(long id) {
        presenter.onDeleteButtonClick(id);
    }

}
