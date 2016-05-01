package ava.androidchef.features.addrecipe;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import ava.androidchef.R;

public class AddRecipeActivity extends FragmentActivity {

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

        EnterIngredientsFragment enterIngredientsFragment = new EnterIngredientsFragment();
        fragmentTransaction.add(R.id.fragment_container_enter_ingredients, enterIngredientsFragment);

        fragmentTransaction.commit();

    }
}
