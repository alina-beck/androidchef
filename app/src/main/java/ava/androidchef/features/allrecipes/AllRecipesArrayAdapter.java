package ava.androidchef.features.allrecipes;

import android.content.Context;
import android.support.v4.app.Fragment;
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
import ava.androidchef.models.Recipe;

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
            view = inflater.inflate(R.layout.list_item_edit, parent, false);
        }

        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.list_item_view_switcher);

        TextView listItemTitle = (TextView) switcher.getCurrentView().findViewById(R.id.list_item_text);
        if (listItemTitle != null) {
            listItemTitle.setText(recipes.get(position).getTitle());
        }

        Button editButton = (Button) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.setDisplayedChild(1);

                EditText updateTitle = (EditText) switcher.getCurrentView().findViewById(R.id.list_item_edittext);
                updateTitle.setText(recipes.get(position).getTitle());
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.button_update);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText updateTitle = (EditText) switcher.getCurrentView().findViewById(R.id.list_item_edittext);
                Recipe recipe = recipes.get(position);
                recipe.setTitle(updateTitle.getText().toString());
                AllRecipesPresenter presenter = new AllRecipesPresenter(fragment);
                presenter.onSaveButtonClick(recipe);

                switcher.setDisplayedChild(0);
                notifyDataSetChanged();

            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = recipes.get(position).getId();
                AllRecipesPresenter presenter = new AllRecipesPresenter(fragment);
                presenter.onDeleteButtonClick(id);

                remove(recipes.get(position));
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
