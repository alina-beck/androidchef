package ava.androidchef.features.createmenu;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import ava.androidchef.R;
import ava.androidchef.features.displaymenu.DisplayMenuActivity;

public class CreateMenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_menu, new CreateMenuFragment()).commit();
    }

    public void createButtonClicked(String menuTitle, int menuLength) {
        Intent intent = new Intent(this, DisplayMenuActivity.class);
        intent.putExtra("menu_title", menuTitle);
        intent.putExtra("menu_length", menuLength);
        startActivity(intent);
    }
}
