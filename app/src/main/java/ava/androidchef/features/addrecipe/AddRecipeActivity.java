package ava.androidchef.features.addrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ava.androidchef.R;

public class AddRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        if (savedInstanceState != null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();
        fragmentTransaction.add(R.id.fragment_container_enter_recipe, enterRecipeFragment);
        fragmentTransaction.commit();
    }

    public void resetInputFields() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = getCurrentFocus();
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();
        fragmentTransaction.replace(R.id.fragment_container_enter_recipe, enterRecipeFragment);
        fragmentTransaction.commit();
    }
}
