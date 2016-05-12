package ava.androidchef.features.displaymenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.R;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;

public class DisplayRecipeFragment extends Fragment {


    public DisplayRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_recipe, container, false);

        Recipe recipe = getArguments().getParcelable("selected_recipe");

        if (recipe != null) {
            TextView titleView = (TextView) view.findViewById(R.id.display_recipe_title);
            titleView.setText(recipe.getTitle());

            ScrollView ingredientsList = (ScrollView) view.findViewById(R.id.display_recipe_ingredients);

            LinkedHashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
            System.out.println(ingredients.size());
            for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
                int amount = entry.getValue();
                Ingredient ingredient = entry.getKey();

                LinearLayout ingredientDisplayLine = (LinearLayout) inflater.inflate(R.layout.list_item_display_ingredient, null);

                TextView amountView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_amount);
                amountView.setText(amount);

                TextView unitView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_unit);
                unitView.setText(ingredient.getUnit());

                TextView ingredientTitleView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_title);
                ingredientTitleView.setText(ingredient.getName());

                ingredientsList.addView(ingredientDisplayLine);
            }

            TextView instructionsView = (TextView) view.findViewById(R.id.display_recipe_instructions);
            instructionsView.setText(recipe.getInstructions());
        }


        return view;
    }

}
