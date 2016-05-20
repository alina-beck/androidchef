package ava.androidchef.features.createmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ava.androidchef.R;

public class CreateMenuFragment extends Fragment implements View.OnClickListener {

    private CreateMenuPresenter presenter;

    public CreateMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_menu, container, false);

        this.presenter = new CreateMenuPresenter(this);

        Button createMenuButton = (Button) view.findViewById(R.id.button_create_random_menu);
        createMenuButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        presenter.createMenuButtonClicked();
    }

    public String getMenuTitle() {
        EditText menuTitleInput = (EditText) getView().findViewById(R.id.input_new_menu_title);
        return menuTitleInput.getText().toString();
    }

    public String getMenuLength() {
        EditText menuLengthInput = (EditText) getView().findViewById(R.id.input_new_menu_days);
        return menuLengthInput.getText().toString();
    }

    public void alert(String alertMessage) {
        Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
    }

    public void displayMenu() {
        ((CreateMenuActivity) getActivity()).displayMenu();
    }
}
