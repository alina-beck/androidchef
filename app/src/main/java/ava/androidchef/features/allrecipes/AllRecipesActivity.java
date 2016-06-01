package ava.androidchef.features.allrecipes;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.EnterRecipeFragment;
import ava.androidchef.features.displaymenu.DisplayRecipeFragment;
import ava.androidchef.models.recipe.Recipe;

public class AllRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_all_recipes, new AllRecipesFragment()).commit();
    }

    public void onRecipeSelected(Recipe recipe) {
        DisplayRecipeFragment displayRecipeFragment = new DisplayRecipeFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.selected_recipe), recipe);
        displayRecipeFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_all_recipes, displayRecipeFragment);
        fragmentTransaction.commit();
    }

    public void editRecipeButtonClicked(Recipe recipe) {
        EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();

        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.editing_recipe), recipe);
        enterRecipeFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_all_recipes, enterRecipeFragment);
        fragmentTransaction.commit();
    }
}
