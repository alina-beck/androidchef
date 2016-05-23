package ava.androidchef.features.shoppinglist;

import android.support.v4.app.ListFragment;

import ava.androidchef.R;
import ava.androidchef.models.shoppinglist.ShoppingList;

public class ShoppingListFragment extends ListFragment {

    public ShoppingListFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        ShoppingListPresenter presenter = new ShoppingListPresenter(this);
        presenter.fragmentCreated();
    }

    public void displayShoppingList(ShoppingList shoppingList) {
        ShoppingListArrayAdapter adapter = new ShoppingListArrayAdapter(getActivity(), R.layout.list_item_shopping, shoppingList);
        setListAdapter(adapter);
    }
}
