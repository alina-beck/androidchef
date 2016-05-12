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

import ava.androidchef.R;

public class EnterRecipeFragment extends Fragment implements View.OnClickListener {

    private EnterRecipePresenter presenter;

    public EnterRecipeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_recipe, container, false);
        this.presenter = new EnterRecipePresenter(this);

        Button button = (Button) view.findViewById(R.id.button_save_recipe);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
                presenter.onButtonClick();
    }

    public String getTitleInput() {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        return inputTitle.getText().toString();
    }

    public String getInstructionsInput() {
        EditText inputInstructions = (EditText) getView().findViewById(R.id.input_recipe_instructions);
        return inputInstructions.getText().toString();
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
