package ava.androidchef.features.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ava.androidchef.R;
import ava.androidchef.models.shoppinglist.ShoppingList;
import ava.androidchef.models.shoppinglist.ShoppingListItem;

public class ShoppingListFragment extends ListFragment {

    private ShoppingListPresenter presenter;
    private ShoppingList shoppingList;

    public ShoppingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.presenter = new ShoppingListPresenter(this);
        presenter.fragmentCreated();
    }

    public void displayShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
        ShoppingListArrayAdapter adapter = new ShoppingListArrayAdapter(getActivity(), R.layout.list_item_shopping, shoppingList, this);
        setListAdapter(adapter);
    }

    public void displayNoShoppingList() {
        TextView noList = (TextView) getView().findViewById(R.id.text_no_shopping_list);
        noList.setText(R.string.no_menu);
    }

    public void checkboxClicked(ShoppingListItem item) {
        presenter.checkboxClicked(item);
        item.setBought(!item.isBought());
        shoppingList.set(shoppingList.indexOf(item), item);
        ShoppingListArrayAdapter adapter = (ShoppingListArrayAdapter) getListAdapter();
        adapter.notifyDataSetChanged();
    }
}
