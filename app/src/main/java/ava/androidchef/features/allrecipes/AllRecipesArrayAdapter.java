package ava.androidchef.features.allrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.Recipe;

public class AllRecipesArrayAdapter extends ArrayAdapter<Recipe> {

    private ArrayList<Recipe> recipes;
    private Context context;

    public AllRecipesArrayAdapter(Context context, int layout, ArrayList<Recipe> recipes) {
        super(context, layout, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_edit, parent, false);
        }

        final ViewSwitcher switcher = (ViewSwitcher) view.findViewById(R.id.list_item_view_switcher);
        switcher.setDisplayedChild(0);

        TextView listItemTitle = (TextView) switcher.getCurrentView().findViewById(R.id.list_item_text);
        listItemTitle.setText(recipes.get(position).getTitle());

        Button editButton = (Button) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.setDisplayedChild(1);
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.button_update);
        saveButton.setOnClickListener(new View.OnClickListener() {
            // TODO: update in database
            @Override
            public void onClick(View v) {
                switcher.setDisplayedChild(0);
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            // TODO: delete from database
            @Override
            public void onClick(View v) {
                remove(recipes.get(position));
            }
        });

        return view;
    }
}
