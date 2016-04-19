package ava.androidchef.features.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ava.androidchef.R;
import ava.androidchef.features.addrecipe.NewRecipeActivity;
import ava.androidchef.features.allrecipes.AllRecipesActivity;
import ava.androidchef.features.weeklymenu.WeeklyMenuActivity;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        Button newRecipe = (Button) view.findViewById(R.id.button_new_recipe);
        newRecipe.setOnClickListener(this);

        Button createMenu = (Button) view.findViewById(R.id.button_create_menu);
        createMenu.setOnClickListener(this);

        Button currentMenu = (Button) view.findViewById(R.id.button_current_menu);
        currentMenu.setOnClickListener(this);

        Button allRecipes = (Button) view.findViewById(R.id.button_all_recipes);
        allRecipes.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_new_recipe:
                Intent intent1 = new Intent(getActivity(), NewRecipeActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_create_menu:
                Intent intent2 = new Intent(getActivity(), WeeklyMenuActivity.class);
                intent2.putExtra("buttonClicked", "createMenu");
                startActivity(intent2);
                break;
            case R.id.button_current_menu:
                Intent intent3 = new Intent(getActivity(), WeeklyMenuActivity.class);
                intent3.putExtra("buttonClicked", "currentMenu");
                startActivity(intent3);
                break;
            case R.id.button_all_recipes:
                Intent intent4 = new Intent(getActivity(), AllRecipesActivity.class);
                startActivity(intent4);
                break;
        }
    }

}
