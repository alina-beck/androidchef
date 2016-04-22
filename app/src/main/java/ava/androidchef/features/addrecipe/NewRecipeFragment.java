package ava.androidchef.features.addrecipe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.Unit;

public class NewRecipeFragment extends Fragment implements View.OnClickListener {

    private NewRecipePresenter presenter;

    public NewRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        this.presenter = new NewRecipePresenter(this);

        LinearLayout inputWrapper = (LinearLayout) getView().findViewById(R.id.ingredient_input_wrapper);

        for (int i = 0; i < inputWrapper.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) inputWrapper.getChildAt(i);
            Spinner unitSpinner = (Spinner) ll.findViewById(R.id.spinner_unit);

            ArrayList<Unit> units = Unit.);

        }

        Button button = (Button) view.findViewById(R.id.button_save_recipe);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        presenter.onButtonClick();

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

        System.out.println(userIngredientsInput);
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
