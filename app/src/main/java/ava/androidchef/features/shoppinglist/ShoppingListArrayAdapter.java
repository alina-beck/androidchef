package ava.androidchef.features.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import ava.androidchef.R;
import ava.androidchef.models.shoppinglist.ShoppingList;

public class ShoppingListArrayAdapter extends ArrayAdapter {

    private Context context;
    private ShoppingList shoppingList;
    private ShoppingListFragment fragment;

    public ShoppingListArrayAdapter(Context context, int resource, ShoppingList shoppingList, ShoppingListFragment fragment) {
        super(context, resource, shoppingList);
        this.context = context;
        this.shoppingList = shoppingList;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_item_shopping, parent, false);
        }

        CheckBox bought = (CheckBox) view.findViewById(R.id.checkbox_isbought);
        boolean isBought = shoppingList.get(position).isBought();
        bought.setChecked(isBought);
        bought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.checkboxClicked(shoppingList.get(position));
            }
        });

        TextView amount = (TextView) view.findViewById(R.id.textview_amount);
        String amountString = Integer.toString(shoppingList.get(position).getAmount()) + " ";
        amount.setText(amountString);

        TextView unit = (TextView) view.findViewById(R.id.textview_unit);
        String unitString = shoppingList.get(position).getIngredient().getUnit() + " ";
        unit.setText(unitString);

        TextView ingredient = (TextView) view.findViewById(R.id.textview_ingredient);
        String ingredientName = shoppingList.get(position).getIngredient().getName();
        ingredient.setText(ingredientName);

        return view;
    }
}
