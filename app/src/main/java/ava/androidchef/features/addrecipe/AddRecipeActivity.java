package ava.androidchef.features.addrecipe;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import ava.androidchef.R;

public class AddRecipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        if (savedInstanceState != null) {
            return;
        }

        EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_enter_recipe, enterRecipeFragment).commit();
    }
}
