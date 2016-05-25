package ava.androidchef.features.displaymenu;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.R;
import ava.androidchef.features.allrecipes.AllRecipesActivity;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.utils.IconTypefaceSpan;

public class DisplayRecipeFragment extends Fragment implements View.OnClickListener {

    private Recipe recipe;

    public DisplayRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_recipe, container, false);

        this.recipe = getArguments().getParcelable("selected_recipe");
        setHasOptionsMenu(true);

        if (recipe != null) {

            LinearLayout ingredientsList = (LinearLayout) view.findViewById(R.id.display_recipe_ingredients);

            LinkedHashMap<Ingredient, Integer> ingredients = recipe.getIngredients();
            for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
                int amount = entry.getValue();
                Ingredient ingredient = entry.getKey();

                LinearLayout ingredientDisplayLine = (LinearLayout) inflater.inflate(R.layout.list_item_display_ingredient, null);

                TextView amountView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_amount);
                amountView.setText(Integer.toString(amount) + " ");

                TextView unitView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_unit);
                unitView.setText(ingredient.getUnit() + " ");

                TextView ingredientTitleView = (TextView) ingredientDisplayLine.findViewById(R.id.display_ingredient_title);
                ingredientTitleView.setText(ingredient.getName());

                ingredientsList.addView(ingredientDisplayLine);
            }

            TextView instructionsView = (TextView) view.findViewById(R.id.display_recipe_instructions);
            instructionsView.setText(recipe.getInstructions());

        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_edit);
        Button editIcon = (Button) item.getActionView();

        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "Flaticon.ttf");
        editIcon.setTypeface(iconfont);
        editIcon.setText(R.string.icon_edit);
        editIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        editIcon.setBackgroundColor(Color.TRANSPARENT);
        editIcon.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(recipe.getTitle());
    }

    @Override
    public void onClick(View view) {
        // TODO: implement interface to be independent from Activities
        if (getActivity() instanceof DisplayMenuActivity) {
            ((DisplayMenuActivity) getActivity()).editRecipeButtonClicked(recipe);
        }
        else if (getActivity() instanceof AllRecipesActivity) {
            ((AllRecipesActivity) getActivity()).editRecipeButtonClicked(recipe);
        }
    }
}
