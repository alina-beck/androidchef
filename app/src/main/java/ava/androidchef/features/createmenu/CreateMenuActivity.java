package ava.androidchef.features.createmenu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import ava.androidchef.R;

public class CreateMenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);
        chooseFragment(getIntent().getStringExtra("button_clicked"));
    }

    public void chooseFragment(String buttonClicked) {
        Fragment fragment;
        switch(buttonClicked) {
            case "create_menu":
                fragment = new CreateMenuFragment();
                break;
            case "current_menu":
                fragment = new DisplayMenuFragment();
                fragment.setArguments(getIntent().getExtras());
                break;
            default:
                fragment = null;
        }
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container_menu, fragment).commit();
        }
    }

    public void createButtonClicked(String menuTitle, int menuLength) {
        Bundle bundle = new Bundle();
        bundle.putAll(getIntent().getExtras());
        bundle.putString("menu_title", menuTitle);
        bundle.putInt("menu_length", menuLength);

        DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
        displayMenuFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_menu, displayMenuFragment).commit();
    }
}
