package ava.androidchef.features.shoppinglist;

import ava.androidchef.models.shoppinglist.ShoppingList;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;
import ava.androidchef.models.shoppinglist.ShoppingListItem;

public class ShoppingListPresenter {

    private ShoppingListFragment fragment;

    public ShoppingListPresenter(ShoppingListFragment fragment) {
        this.fragment = fragment;
    }

    public void fragmentCreated() {
        ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
        ShoppingList shoppingList = shoppingListDAO.getShoppingList();
        fragment.displayShoppingList(shoppingList);
    }

    public void checkboxClicked(ShoppingListItem item) {
        ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
        shoppingListDAO.updateItemInShoppingList(item);
    }
}
