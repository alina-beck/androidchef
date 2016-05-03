package ava.androidchef.features.createmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ava.androidchef.R;

public class CreateMenuFragment extends Fragment implements View.OnClickListener {

    public CreateMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_menu, container, false);

        Button createMenuButton = (Button) view.findViewById(R.id.button_create_random_menu);
        createMenuButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        EditText menuTitleInput = (EditText) v.getRootView().findViewById(R.id.input_new_menu_title);
        String menuTitle = menuTitleInput.getText().toString();

        EditText menuLengthInput = (EditText) v.getRootView().findViewById(R.id.input_new_menu_days);
        int menuLength = Integer.parseInt(menuLengthInput.getText().toString());

        ((CreateMenuActivity) getActivity()).createButtonClicked(menuTitle, menuLength);
    }
}
