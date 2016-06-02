package ava.androidchef.features.createmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ava.androidchef.R;
import ava.androidchef.features.displaymenu.DisplayMenuActivity;

public class CreateMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container_menu, new CreateMenuFragment()).commit();
    }

    public void displayMenu() {
        Intent intent = new Intent(this, DisplayMenuActivity.class);
        startActivity(intent);
        this.finish();
    }
}
