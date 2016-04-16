package ava.androidchef.features.allrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ava.androidchef.R;
import ava.androidchef.models.Recipe;

public class AllRecipesArrayAdapter extends ArrayAdapter<Recipe> implements View.OnClickListener {

    private ArrayList<Recipe> recipes;
    private Context context;

    public AllRecipesArrayAdapter(Context context, int layout, ArrayList<Recipe> recipes) {
        super(context, layout, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_edit, parent, false);
        }

        TextView listItemTitle = (TextView) view.findViewById(R.id.list_item_text);
        listItemTitle.setText(recipes.get(position).getTitle());

        Button editButton = (Button) view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(this);

        Button deleteButton = (Button) view.findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(this);

        return view;
    }

    // TODO: update and delete in database; change textview to edittext
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_edit:
                break;
            case R.id.button_delete:
                break;
        }
    }


}
