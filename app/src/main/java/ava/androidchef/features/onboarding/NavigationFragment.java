package ava.androidchef.features.onboarding;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.AddRecipeActivity;
import ava.androidchef.features.allrecipes.AllRecipesActivity;
import ava.androidchef.features.createmenu.CreateMenuActivity;
import ava.androidchef.features.displaymenu.DisplayMenuActivity;
import ava.androidchef.features.shoppinglist.ShoppingListActivity;
import ava.androidchef.utils.IconTypefaceSpan;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    private Typeface iconfont;

    public NavigationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        this.iconfont = Typeface.createFromAsset(getActivity().getAssets(), "Flaticon.ttf");

        Button goToAddRecipe = (Button) view.findViewById(R.id.button_goto_add_recipe);
        addIcon(goToAddRecipe, R.string.icon_addrecipe, R.string.new_recipe);
        goToAddRecipe.setOnClickListener(this);

        Button goToCreateMenu = (Button) view.findViewById(R.id.button_goto_create_menu);
        addIcon(goToCreateMenu, R.string.icon_createmenu, R.string.create_random_menu);
        goToCreateMenu.setOnClickListener(this);

        Button goToCurrentMenu = (Button) view.findViewById(R.id.button_goto_current_menu);
        addIcon(goToCurrentMenu, R.string.icon_menu, R.string.show_current_menu);
        goToCurrentMenu.setOnClickListener(this);

        Button goToAllRecipes = (Button) view.findViewById(R.id.button_goto_all_recipes);
        addIcon(goToAllRecipes, R.string.icon_recipes, R.string.show_all_recipes);
        goToAllRecipes.setOnClickListener(this);

        Button goToShoppingList = (Button) view.findViewById(R.id.button_goto_shopping_list);
        addIcon(goToShoppingList, R.string.icon_shopping, R.string.show_shopping_list);
        goToShoppingList.setOnClickListener(this);

        return view;
    }

    public void addIcon(Button button, int iconResource, int stringResource) {
        //TODO: don't break lines for shopping list button
        SpannableStringBuilder spannable = new SpannableStringBuilder(getString(iconResource) + "\n" + getString(stringResource).toUpperCase());
        spannable.setSpan(new IconTypefaceSpan(iconfont), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        button.setAllCaps(false);
        button.setText(spannable);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.button_goto_add_recipe:
                i = new Intent(getActivity(), AddRecipeActivity.class);
                break;
            case R.id.button_goto_create_menu:
                i = new Intent(getActivity(), CreateMenuActivity.class);
                break;
            case R.id.button_goto_current_menu:
                i = new Intent(getActivity(), DisplayMenuActivity.class);
                break;
            case R.id.button_goto_all_recipes:
                i = new Intent(getActivity(), AllRecipesActivity.class);
                break;
            case R.id.button_goto_shopping_list:
                i = new Intent(getActivity(), ShoppingListActivity.class);
                break;
            default:
                i = null;
        }
        if (i != null) {
            startActivity(i);
        }
    }
}
