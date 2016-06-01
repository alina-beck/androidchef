package ava.androidchef.features.addrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.features.editrecipe.EditIngredientsPresenter;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.utils.AutoCompleteOnItemClickListener;
import ava.androidchef.utils.BaseIngredientsPresenter;
import ava.androidchef.utils.IngredientInputTextWatcher;

public class EnterIngredientsFragment extends Fragment
        implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {

    private BaseIngredientsPresenter presenter;
    private LinearLayout ingredientInputRows;

    public EnterIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_ingredients, container, false);
        this.ingredientInputRows = (LinearLayout) view.findViewById(R.id.ingredient_input_rows);

        if (getArguments() == null) {
            this.presenter = new EnterIngredientsPresenter(this);
        }

        else {
            this.presenter = new EditIngredientsPresenter(this, (Recipe) getArguments().getParcelable("edited_recipe"));
        }

        displayIngredientInputRow();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.viewCreated();
    }

    private void displayIngredientInputRow() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final LinearLayout ingredientInputRow = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);

        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) ingredientInputRow.findViewById(R.id.input_ingredient_name);
        populateIngredientNameDropdown(ingredientNameInput);
        ingredientNameInput.setOnItemClickListener(new AutoCompleteOnItemClickListener(ingredientInputRow, this));
        ingredientNameInput.setOnFocusChangeListener(this);
        ingredientNameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    EditText inputAmount = (EditText) ingredientInputRow.findViewById(R.id.input_ingredient_amount);
                    handled = inputAmount.requestFocus();
                }
                return handled;
            }
        });

        EditText inputAmount = (EditText) ingredientInputRow.findViewById(R.id.input_ingredient_amount);
        inputAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) ingredientInputRow.findViewById(R.id.spinner_unit);
                    unitSpinner.requestFocus();
                    unitSpinner.showDropDown();
                    handled = true;
                }
                return handled;
            }
        });

        final MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) ingredientInputRow.findViewById(R.id.spinner_unit);
        populateUnitSpinner(unitSpinner);

        ingredientInputRows.addView(ingredientInputRow);
    }

    private void populateIngredientNameDropdown(AutoCompleteTextView ingredientNameInput) {
        ArrayList<String> ingredientNames = presenter.getAllIngredientNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ingredientNames);
        ingredientNameInput.setAdapter(adapter);
    }

    public void populateUnitSpinner(MaterialBetterSpinner unitSpinner) {
        //TODO: rebuild custom TextWatcher and set method to private again
        //TODO: style dropdown items & selected item
        ArrayList<String> units = presenter.getAllUnits();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);
    }

    public void fillIngredientRow(String name, String unit, String amount, int position) {
        LinearLayout filledRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        AutoCompleteTextView nameInput = (AutoCompleteTextView) filledRow.findViewById(R.id.input_ingredient_name);
        nameInput.setText(name);
        nameInput.setOnFocusChangeListener(null);

        MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) filledRow.findViewById(R.id.spinner_unit);
        int positionOfUnit = ((ArrayAdapter<String>) unitSpinner.getAdapter()).getPosition(unit);
        unitSpinner.setSelection(positionOfUnit);

        EditText amountInput = (EditText) filledRow.findViewById(R.id.input_ingredient_amount);
        amountInput.setText(amount);

        displayIngredientInputRow();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View selectedRow, int position, long id) {
        String selectedIngredientName = (String) parent.getAdapter().getItem(position);

        AutoCompleteTextView ingredientNameInput = (AutoCompleteTextView) selectedRow.findViewById(R.id.input_ingredient_name);
        ingredientNameInput.setText(selectedIngredientName);
        ingredientNameInput.addTextChangedListener(new IngredientInputTextWatcher(this, selectedRow, ingredientNameInput));

        MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) selectedRow.findViewById(R.id.spinner_unit);
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) unitSpinner.getAdapter();
        String matchingUnit = presenter.getUnit(selectedIngredientName);
        adapter.clear();
        adapter.add(matchingUnit);
        adapter.notifyDataSetChanged();
        unitSpinner.setText(matchingUnit);

        EditText inputAmount = (EditText) selectedRow.findViewById(R.id.input_ingredient_amount);
        inputAmount.requestFocus();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        view.setOnFocusChangeListener(null);
        displayIngredientInputRow();
    }

    public void saveRecipeButtonClicked() {
        presenter.saveRecipeButtonClicked();
    }

    public int getNumberOfIngredientInputRows() {
        return ingredientInputRows.getChildCount();
    }

    public String getIngredientNameAt(int position) {
        LinearLayout requestedRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        AutoCompleteTextView ingredientName = (AutoCompleteTextView) requestedRow.findViewById(R.id.input_ingredient_name);
        return ingredientName.getText().toString();
    }

    public String getIngredientUnitAt(int position) {
        LinearLayout requestedRow = (LinearLayout) ingredientInputRows.getChildAt(position);
        MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) requestedRow.findViewById(R.id.spinner_unit);

        return unitSpinner.getText().toString();
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
        ((EnterRecipeFragment) getParentFragment()).ingredientsSaved(ingredients);
    }
}
