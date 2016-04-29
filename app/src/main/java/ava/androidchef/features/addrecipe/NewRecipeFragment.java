package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.utils.Unit;

public class NewRecipeFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private NewRecipePresenter presenter;
    private AutoCompleteTextView currentIngredientInput;

    public NewRecipeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        this.presenter = new NewRecipePresenter(this);

        // vertical LinearLayout to hold all ingredient input lines
        LinearLayout inputWrapper = (LinearLayout) view.findViewById(R.id.ingredient_input_wrapper);

        // horizontal LinearLayout for the input fields of each ingredient
        LinearLayout newIngredientLine = (LinearLayout) inputWrapper.findViewById(R.id.ingredient_input);

        // input field for ingredient name
        AutoCompleteTextView ingredientAutoComplete = (AutoCompleteTextView) newIngredientLine.findViewById(R.id.input_ingredient_name);
        this.currentIngredientInput = ingredientAutoComplete;
        ingredientAutoComplete.setOnFocusChangeListener(this);

        // put existing ingredients in adapter to display suggestions during input
        ArrayList<Ingredient> ingredients = presenter.getIngredients();
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredients);
        ingredientAutoComplete.setAdapter(ingredientAdapter);

        // dropdown for ingredient unit
        Spinner unitSpinner = (Spinner) newIngredientLine.findViewById(R.id.spinner_unit);

        // put units in adapter to fill dropdown
        ArrayList<String> units = Unit.getUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        // save button
        Button button = (Button) view.findViewById(R.id.button_save_recipe);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
                presenter.onButtonClick();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        // show additional ingredient input line
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout inputWrapper = (LinearLayout) view.getRootView().findViewById(R.id.ingredient_input_wrapper);
        View newIngredientLine = inflater.inflate(R.layout.new_ingredient, null);
        inputWrapper.addView(newIngredientLine);

        // remove listener from old line
        currentIngredientInput.setOnFocusChangeListener(null);

        // set listener to new line
        this.currentIngredientInput = (AutoCompleteTextView) newIngredientLine.findViewById(R.id.input_ingredient_name);
        currentIngredientInput.setOnFocusChangeListener(this);
    }

    public String getRecipeInput() {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        return inputTitle.getText().toString();
    }

    public ArrayList<ArrayList<String>> getIngredientInput() {
        ArrayList<ArrayList<String>> userIngredientsInput = new ArrayList<>();

        LinearLayout inputWrapper = (LinearLayout) getView().findViewById(R.id.ingredient_input_wrapper);

        for (int i = 0; i < inputWrapper.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) inputWrapper.getChildAt(i);
            EditText inputName = (EditText) ll.findViewById(R.id.input_ingredient_name);
            Spinner inputUnit = (Spinner) ll.findViewById(R.id.spinner_unit);
            EditText inputAmount = (EditText) ll.findViewById(R.id.input_ingredient_amount);

            ArrayList<String> userIngredient1 = new ArrayList<>();
            userIngredient1.add(inputName.getText().toString());
            userIngredient1.add(inputUnit.getSelectedItem().toString());
            userIngredient1.add(inputAmount.getText().toString());
            userIngredientsInput.add(userIngredient1);
        }

        return userIngredientsInput;
    }

    public void saveComplete(boolean didSave) {
        if (didSave) {
            Toast.makeText(getContext(), "Recipe saved", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Unable to save", Toast.LENGTH_LONG).show();
        }
    }
}
