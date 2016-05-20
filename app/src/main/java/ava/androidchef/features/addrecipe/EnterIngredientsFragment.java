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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.utils.AutoCompleteOnItemClickListener;
import ava.androidchef.utils.IngredientInputTextWatcher;
import ava.androidchef.utils.Unit;

public class EnterIngredientsFragment extends Fragment
        implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    private EnterIngredientsPresenter presenter;
    private LinearLayout ingredientInputRows;

    public EnterIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_ingredients, container, false);

        this.presenter = new EnterIngredientsPresenter(this);
        this.ingredientInputRows = (LinearLayout) view.findViewById(R.id.ingredient_input_rows);

        displayIngredientInputRow();

        return view;
    }

    private void displayIngredientInputRow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout ingredientInputRow = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);

        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputRow.findViewById(R.id.input_ingredient_name);
        populateIngredientNameDropdown(ingredientNameInput);
        ingredientNameInput.setOnItemClickListener(new AutoCompleteOnItemClickListener(ingredientInputRow, this));
        ingredientNameInput.setOnFocusChangeListener(this);

        Spinner unitSpinner = (Spinner) ingredientInputRow.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

        ingredientInputRows.addView(ingredientInputRow);
    }

    private void populateIngredientNameDropdown(AutoCompleteTextView ingredientNameInput) {
        ArrayList<String> ingredientNames = presenter.getAllIngredientNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredientNames);
        ingredientNameInput.setAdapter(adapter);
    }

    public void populateUnitSpinner(Spinner unitSpinner) {
        //TODO: make standard selection empty
        //TODO: rebuild custom TextWatcher and set method to private again
        ArrayList<String> units = presenter.getAllUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View selectedRow, int position, long id) {
        String selectedIngredientName = (String) parent.getAdapter().getItem(position);

        final AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) selectedRow.findViewById(R.id.input_ingredient_name);
        ingredientNameInput.addTextChangedListener(new IngredientInputTextWatcher(this, selectedRow, ingredientNameInput));

        Spinner unitSpinner = (Spinner) selectedRow.findViewById(R.id.spinner_unit);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) unitSpinner.getAdapter();
        adapter.clear();
        adapter.add(presenter.getUnit(selectedIngredientName));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        view.setOnFocusChangeListener(null);
        displayIngredientInputRow();
    }

    public void saveRecipeButtonClicked() {
        presenter.saveRecipeButtonClicked();
    }

    public int getNumberOfIngredients() {
        return ingredientInputRows.getChildCount();
    }

    public String getIngredientNameAt(int position) {
        LinearLayout requestedRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        AutoCompleteTextView ingredientName = (AutoCompleteTextView) requestedRow.findViewById(R.id.input_ingredient_name);
        return ingredientName.getText().toString();
    }

    public String getIngredientUnitAt(int position) {
        LinearLayout requestedRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        Spinner unitSpinner = (Spinner) requestedRow.findViewById(R.id.spinner_unit);

        return unitSpinner.getSelectedItem().toString();
    }

    public String getAmountAt(int position) {
        LinearLayout requestedRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        EditText amount = (EditText) requestedRow.findViewById(R.id.input_ingredient_amount);

        return amount.getText().toString();
    }

    public void alert(String alertMessage) {
        Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
    }

    public void ingredientsSaved(LinkedHashMap<Ingredient, Integer> ingredients) {
        ((AddRecipeActivity) getActivity()).ingredientsSaved(ingredients);
    }

}
