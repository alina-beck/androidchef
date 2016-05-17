package ava.androidchef.features.allrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import java.util.ArrayList;
import ava.androidchef.R;
import ava.androidchef.models.recipe.Recipe;

public class AllRecipesArrayAdapter extends ArrayAdapter<Recipe> {

    private ArrayList<Recipe> recipes;
    private Context context;
    private AllRecipesFragment fragment;

    public AllRecipesArrayAdapter(Context context, int layout, ArrayList<Recipe> recipes, AllRecipesFragment fragment) {
        super(context, layout, recipes);
        this.recipes = recipes;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_editable_recipe, parent, false);
        }

        TextView listItemTitle = (TextView) view.findViewById(R.id.list_item_text);
        listItemTitle.setText(recipes.get(position).getTitle());
        listItemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.recipeClicked(recipes.get(position));
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteButtonClicked(recipes.get(position).getId());

                remove(recipes.get(position));
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
