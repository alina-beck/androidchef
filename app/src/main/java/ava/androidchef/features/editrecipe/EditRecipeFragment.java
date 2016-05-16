package ava.androidchef.features.editrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Map;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.utils.Unit;

public class EditRecipeFragment extends Fragment implements View.OnClickListener {

    public EditRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        Recipe recipe = getArguments().getParcelable("editing_recipe");

        EditText editTitle = (EditText) view.findViewById(R.id.input_edit_recipe_title);
        editTitle.setText(recipe.getTitle());

        LinearLayout ingredientEditWrapper = (LinearLayout) view.findViewById(R.id.edit_ingredient_wrapper);

        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            LinearLayout ingredientEditRow = (LinearLayout) inflater.inflate(R.layout.list_item_enter_ingredient, null);

            EditText ingredientTitle = (EditText) ingredientEditRow.findViewById(R.id.input_ingredient_name);
            ingredientTitle.setText(entry.getKey().getName());

            Spinner unitSpinner = (Spinner) ingredientEditRow.findViewById(R.id.spinner_unit);
            ArrayList<String> units = Unit.getUnits();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, units);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            unitSpinner.setAdapter(adapter);
            unitSpinner.setSelection(adapter.getPosition(entry.getKey().getUnit()));

            EditText ingredientAmount = (EditText) ingredientEditRow.findViewById(R.id.input_ingredient_amount);
            ingredientAmount.setText(entry.getValue().toString());

            ingredientEditWrapper.addView(ingredientEditRow);
        }

        EditText editInstructions = (EditText) view.findViewById(R.id.input_edit_recipe_instructions);
        editInstructions.setText(recipe.getInstructions());

        Button updateRecipeButton = (Button) view.findViewById(R.id.button_update_recipe);
        updateRecipeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        //TODO: update recipe in database
    }

}
