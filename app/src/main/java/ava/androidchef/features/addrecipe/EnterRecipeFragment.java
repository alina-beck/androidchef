package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import ava.androidchef.utils.AutoCompleteOnItemClickListener;
import ava.androidchef.utils.Unit;

public class EnterRecipeFragment extends Fragment
        implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener {

    private EnterRecipePresenter presenter;
    private LinearLayout currentIngredientInput;

    public EnterRecipeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_recipe, container, false);
        this.presenter = new EnterRecipePresenter(this);

        // vertical LinearLayout to hold all ingredient input lines
        LinearLayout inputWrapper = (LinearLayout) view.findViewById(R.id.ingredient_input_wrapper);

        // horizontal LinearLayout for the input fields of each ingredient
        LinearLayout firstIngredientLine = (LinearLayout) inputWrapper.findViewById(R.id.ingredient_input);
        this.currentIngredientInput = firstIngredientLine;

        // input field for ingredient name
        AutoCompleteTextView ingredientAutoComplete = (AutoCompleteTextView) firstIngredientLine.findViewById(R.id.input_ingredient_name);
        ingredientAutoComplete.setOnFocusChangeListener(this);

        // put existing ingredients in adapter to display suggestions during input
        populateIngredientSuggestions(ingredientAutoComplete);
        ingredientAutoComplete.setOnItemClickListener(new AutoCompleteOnItemClickListener(firstIngredientLine, this));

        // dropdown for ingredient unit
        Spinner unitSpinner = (Spinner) firstIngredientLine.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

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
        LinearLayout newIngredientLine = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);
        inputWrapper.addView(newIngredientLine);

        // fill ingredient unit spinner
        Spinner unitSpinner = (Spinner) newIngredientLine.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

        // remove listener from old line
        currentIngredientInput.findViewById(R.id.input_ingredient_name).setOnFocusChangeListener(null);

        // set listener to new line
        AutoCompleteTextView newIngredientInput = (AutoCompleteTextView) newIngredientLine.findViewById(R.id.input_ingredient_name);
        newIngredientInput.setOnFocusChangeListener(this);
        populateIngredientSuggestions(newIngredientInput);
        newIngredientInput.setOnItemClickListener(new AutoCompleteOnItemClickListener(newIngredientLine, this));
        this.currentIngredientInput = newIngredientLine;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Ingredient selectedIngredient = (Ingredient) parent.getAdapter().getItem(position);
        Spinner unitSpinner = (Spinner) view.findViewById(R.id.spinner_unit);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) unitSpinner.getAdapter();
        adapter.clear();
        adapter.add(selectedIngredient.getUnit());
        adapter.notifyDataSetChanged();
    }

    private void populateIngredientSuggestions (AutoCompleteTextView ingredientInput) {
        ArrayList<Ingredient> ingredients = presenter.getIngredients();
        ArrayAdapter<Ingredient> ingredientAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredients);
        ingredientInput.setAdapter(ingredientAdapter);
    }

    private void populateUnitSpinner(Spinner unitSpinner) {
        ArrayList<String> units = Unit.getUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    public String getRecipeInput() {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        return inputTitle.getText().toString();
    }

    public ArrayList<ArrayList<String>> getIngredientInput() {
        ArrayList<ArrayList<String>> userIngredientsInput = new ArrayList<>();

        LinearLayout inputWrapper = (LinearLayout) getView().findViewById(R.id.ingredient_input_wrapper);

        for (int i = 0; i < (inputWrapper.getChildCount()-1); i++) {
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
