package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;

public class EnterRecipeFragment extends Fragment {

    private EnterRecipePresenter presenter;

    public EnterRecipeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_recipe, container, false);
        this.presenter = new EnterRecipePresenter(this);

        EnterIngredientsFragment enterIngredientsFragment = new EnterIngredientsFragment();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.nested_fragment_container, enterIngredientsFragment);
        fragmentTransaction.commit();

        Button button = (Button) view.findViewById(R.id.button_save_recipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveRecipeButtonClicked();
            }
        });

        return view;
    }

    public String getRecipeTitle() {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        return inputTitle.getText().toString();
    }

    public String getRecipeInstructions() {
        EditText inputInstructions = (EditText) getView().findViewById(R.id.input_recipe_instructions);
        return inputInstructions.getText().toString();
    }

    public void saveIngredients() {
        EnterIngredientsFragment enterIngredientsFragment =
                (EnterIngredientsFragment) getChildFragmentManager().findFragmentById(R.id.nested_fragment_container);
        enterIngredientsFragment.saveRecipeButtonClicked();
    }

    public void ingredientsSaved(LinkedHashMap<Ingredient, Integer> ingredients) {
        presenter.ingredientsSaved(ingredients);
    }

    public void alert(String alertMessage) {
        Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
    }
}
