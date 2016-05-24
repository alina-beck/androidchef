package ava.androidchef.features.shoppinglist;

import android.support.v4.app.ListFragment;

import ava.androidchef.R;
import ava.androidchef.models.shoppinglist.ShoppingList;
import ava.androidchef.models.shoppinglist.ShoppingListItem;

public class ShoppingListFragment extends ListFragment {

    private ShoppingListPresenter presenter;

    public ShoppingListFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        this.presenter = new ShoppingListPresenter(this);
        presenter.fragmentCreated();
    }

    public void displayShoppingList(ShoppingList shoppingList) {
        ShoppingListArrayAdapter adapter = new ShoppingListArrayAdapter(getActivity(), R.layout.list_item_shopping, shoppingList, this);
        setListAdapter(adapter);
    }

    public void checkboxClicked(ShoppingListItem item) {
        presenter.checkboxClicked(item);
    }
}
