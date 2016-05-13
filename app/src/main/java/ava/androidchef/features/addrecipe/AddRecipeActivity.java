package ava.androidchef.features.addrecipe;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;

public class AddRecipeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        if (savedInstanceState != null) {
            return;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        final EnterRecipeFragment enterRecipeFragment = new EnterRecipeFragment();
        fragmentTransaction.add(R.id.fragment_container_enter_recipe, enterRecipeFragment);

        final EnterIngredientsFragment enterIngredientsFragment = new EnterIngredientsFragment();
        fragmentTransaction.add(R.id.fragment_container_enter_ingredients, enterIngredientsFragment);

        fragmentTransaction.commit();

        Button button = (Button) findViewById(R.id.button_save_recipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedHashMap<Ingredient, Integer> savedIngredients = enterIngredientsFragment.onSaveButtonClick();
                enterRecipeFragment.onSaveButtonClick(savedIngredients);
            }
        });

    }
}
