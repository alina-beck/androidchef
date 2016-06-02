package ava.androidchef.features.createmenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;
import ava.androidchef.models.shoppinglist.ShoppingList;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;
import ava.androidchef.utils.InputValidator;

public class CreateMenuPresenter {

    private CreateMenuFragment fragment;

    public CreateMenuPresenter(CreateMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void createMenuButtonClicked() {
        InputValidator validator = new InputValidator(fragment.getActivity());
        String menuTitle = fragment.getMenuTitle();
        String menuLength = fragment.getMenuLength();

        if (menuTitle.trim().equals("")) {
            fragment.alert("Please enter a title for your menu!");
            return;
        }

        if (menuLength.trim().equals("")) {
            fragment.alert("Please choose how many recipes you want in your menu!");
            return;
        }

        if (validator.menuLengthExceedsExistingRecipes(Integer.parseInt(menuLength))) {
            RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
            int amountOfRecipesInDatabase = recipeDAO.getNumberOfRecipes();
            fragment.alert("You only have " + amountOfRecipesInDatabase + " recipes available. Please adapt your input!");
        }
        else {
            saveMenu(menuTitle, Integer.parseInt(menuLength));
        }
    }

    private void saveMenu(String title, int length) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        ArrayList<Recipe> recipes = recipeDAO.selectRandomMenu(length);

        Menu newMenu = new Menu(title, length, recipes);
        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        menuDAO.insertMenu(newMenu);

        saveShoppingList(newMenu);

        fragment.displayMenu();
    }

    private void saveShoppingList(Menu menu) {
        ShoppingList shoppingList = ShoppingList.createShoppingListFromMenu(menu);
        ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
        shoppingListDAO.insertShoppingList(shoppingList);
    }

}
