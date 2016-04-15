package ava.androidchef;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewRecipeFragment extends Fragment implements View.OnClickListener {

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        Button button = (Button) view.findViewById(R.id.button_save_recipe);
        button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick (View view) {
        NewRecipePresenter.onClick(view);
    }

}
