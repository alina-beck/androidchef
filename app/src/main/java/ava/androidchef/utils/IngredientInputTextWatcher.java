package ava.androidchef.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.EnterIngredientsFragment;
import fr.ganfra.materialspinner.MaterialSpinner;

public class IngredientInputTextWatcher implements TextWatcher {

    private EnterIngredientsFragment fragment;
    private View selectedRow;
    private AutoCompleteTextView ingredientNameInput;

    public IngredientInputTextWatcher(EnterIngredientsFragment fragment, View selectedRow, AutoCompleteTextView ingredientNameInput) {
        this.fragment = fragment;
        this.selectedRow = selectedRow;
        this.ingredientNameInput = ingredientNameInput;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        // reset content of input fields when chosen ingredient is deselected by typing
        MaterialBetterSpinner unitSpinner = (MaterialBetterSpinner) selectedRow.findViewById(R.id.spinner_unit);
        fragment.populateUnitSpinner(unitSpinner);
        ingredientNameInput.removeTextChangedListener(this);
    }
}
