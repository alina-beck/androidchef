package ava.androidchef.features.allrecipes;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.recipe.Recipe;

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
        AllRecipesArrayAdapter adapter = new AllRecipesArrayAdapter(getActivity(), R.layout.list_item_edit, recipes, this);
        setListAdapter(adapter);
    }

    public void displayToast(boolean update) {
        if (update) {
            Toast.makeText(getActivity(), "updated in database", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "error when saving", Toast.LENGTH_LONG).show();
        }
    }

    public void saveButtonClicked(int id, String newTitle) {
        presenter.onSaveButtonClick(id, newTitle);
    }

    public void deleteButtonClicked(int id) {
        presenter.onDeleteButtonClick(id);
    }

}
