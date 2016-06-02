package ava.androidchef.features.createmenu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        this.presenter = new CreateMenuPresenter(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_edit);
        Button editIcon = (Button) item.getActionView();

        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "Flaticon.ttf");
        editIcon.setTypeface(iconfont);
        editIcon.setText(R.string.icon_save);
        editIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        editIcon.setBackgroundColor(Color.TRANSPARENT);
        editIcon.setOnClickListener(this);
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
