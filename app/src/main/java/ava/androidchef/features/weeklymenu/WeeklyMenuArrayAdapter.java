package ava.androidchef.features.weeklymenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.recipe.Recipe;

public class WeeklyMenuArrayAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private ArrayList<Recipe> recipes;
    private WeeklyMenuFragment fragment;

    public WeeklyMenuArrayAdapter(Context context, int layout, ArrayList<Recipe> recipes, WeeklyMenuFragment fragment) {
        super(context, layout, recipes);
        this.context = context;
        this.recipes = recipes;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_replace, parent, false);
        }

        TextView recipeTitle = (TextView) view.findViewById(R.id.list_item_menu_text);
        recipeTitle.setText(recipes.get(position).getTitle());

        Button replaceButton = (Button) view.findViewById(R.id.button_replace);
        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.replaceButtonClicked(recipes, getPosition(recipes.get(position)));
            }
        });

        return view;
    }
}
