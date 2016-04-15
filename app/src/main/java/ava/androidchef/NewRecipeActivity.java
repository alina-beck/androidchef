package ava.androidchef;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class NewRecipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        if (savedInstanceState != null) {
            return;
        }

        NewRecipeFragment newRecipeFragment = new NewRecipeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_new_recipe, newRecipeFragment).commit();
    }
}
