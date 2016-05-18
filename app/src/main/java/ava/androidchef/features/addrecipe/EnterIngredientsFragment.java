package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.utils.AutoCompleteOnItemClickListener;
import ava.androidchef.utils.Unit;

public class EnterIngredientsFragment extends Fragment
        implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    private EnterIngredientsPresenter presenter;
    private LinearLayout rows;

    public EnterIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_ingredients, container, false);

        this.presenter = new EnterIngredientsPresenter(this);
        this.rows = (LinearLayout) view.findViewById(R.id.ingredient_input_wrapper);

        showIngredientInputLine();

        return view;
    }

    private void showIngredientInputLine() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout ingredientInputLine = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);

        populateIngredientSuggestions(ingredientInputLine);

        Spinner unitSpinner = (Spinner) ingredientInputLine.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputLine.findViewById(R.id.input_ingredient_name);
        ingredientNameInput.setOnFocusChangeListener(this);

        rows.addView(ingredientInputLine);
    }

    private void populateIngredientSuggestions(LinearLayout ingredientInputLine) {
        ArrayList<Ingredient> ingredients = presenter.getIngredients();
        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputLine.findViewById(R.id.input_ingredient_name);

        EnterIngredientsArrayAdapter ingredientAdapter = new EnterIngredientsArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, ingredients);
        ingredientNameInput.setAdapter(ingredientAdapter);
        ingredientNameInput.setOnItemClickListener(new AutoCompleteOnItemClickListener(ingredientInputLine, this));
    }

    private void populateUnitSpinner(Spinner unitSpinner) {
        ArrayList<String> units = Unit.getUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View selectedLine, int position, long id) {
        Ingredient selectedIngredient = (Ingredient) parent.getAdapter().getItem(position);

        selectedLine.setTag(selectedIngredient);

        AutoCompleteTextView titleInput = (AutoCompleteTextView) selectedLine.findViewById(R.id.input_ingredient_name);
        titleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                selectedLine.setTag(null);
                Spinner unitSpinner = (Spinner) selectedLine.findViewById(R.id.spinner_unit);
                populateUnitSpinner(unitSpinner);
            }
        });

        Spinner unitSpinner = (Spinner) selectedLine.findViewById(R.id.spinner_unit);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) unitSpinner.getAdapter();
        adapter.clear();
        adapter.add(selectedIngredient.getUnit());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        v.setOnFocusChangeListener(null);
        showIngredientInputLine();
    }

    public LinkedHashMap<Ingredient, Integer> getIngredientInput() {
        LinkedHashMap<Ingredient, Integer> userIngredientsInput = new LinkedHashMap<>();

        LinearLayout inputWrapper = (LinearLayout) getView().findViewById(R.id.ingredient_input_wrapper);
        AutoCompleteTextView inputIngredientName = (AutoCompleteTextView) getView().findViewById(R.id.input_ingredient_name);
        EnterIngredientsArrayAdapter adapter = (EnterIngredientsArrayAdapter) inputIngredientName.getAdapter();
        ArrayList<String> existingIngredientNames = adapter.getIngredientNames();

        for (int i = 0; i < (inputWrapper.getChildCount()-1); i++) {
            LinearLayout ll = (LinearLayout) inputWrapper.getChildAt(i);
            AutoCompleteTextView inputName = (AutoCompleteTextView) ll.findViewById(R.id.input_ingredient_name);
            Ingredient enteredIngredient;

            if (ll.getTag() != null) {
                enteredIngredient = (Ingredient) ll.getTag();
            }

            else if (existingIngredientNames.contains(inputName.getText().toString())) {
                enteredIngredient = adapter.getIngredientByName(inputName.getText().toString());
            }

            else {
                Spinner inputUnit = (Spinner) ll.findViewById(R.id.spinner_unit);
                enteredIngredient = new Ingredient(inputName.getText().toString(), inputUnit.getSelectedItem().toString());
            }

            EditText inputAmount = (EditText) ll.findViewById(R.id.input_ingredient_amount);
            userIngredientsInput.put(enteredIngredient, Integer.parseInt(inputAmount.getText().toString()));
        }

        return userIngredientsInput;
    }

    public LinkedHashMap<Ingredient, Integer> onSaveButtonClick() {
        return presenter.saveIngredients();
    }
}
