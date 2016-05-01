package ava.androidchef.features.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.AddRecipeActivity;
import ava.androidchef.features.allrecipes.AllRecipesActivity;
import ava.androidchef.features.weeklymenu.WeeklyMenuActivity;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    public NavigationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        Button goToAddRecipe = (Button) view.findViewById(R.id.button_goto_add_recipe);
        goToAddRecipe.setOnClickListener(this);

        Button goToCreateMenu = (Button) view.findViewById(R.id.button_goto_create_menu);
        goToCreateMenu.setOnClickListener(this);

        Button goToCurrentMenu = (Button) view.findViewById(R.id.button_goto_current_menu);
        goToCurrentMenu.setOnClickListener(this);

        Button goToAllRecipes = (Button) view.findViewById(R.id.button_goto_all_recipes);
        goToAllRecipes.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.button_goto_add_recipe:
                i = new Intent(getActivity(), AddRecipeActivity.class);
                break;
            case R.id.button_goto_create_menu:
                i = new Intent(getActivity(), WeeklyMenuActivity.class);
                i.putExtra("buttonClicked", "createMenu");
                break;
            case R.id.button_goto_current_menu:
                i = new Intent(getActivity(), WeeklyMenuActivity.class);
                i.putExtra("buttonClicked", "currentMenu");
                break;
            case R.id.button_goto_all_recipes:
                i = new Intent(getActivity(), AllRecipesActivity.class);
                break;
            default:
                i = null;
        }
        if (i != null) {
            startActivity(i);
        }
    }
}
