package ava.androidchef.features.displaymenu;

import java.util.ArrayList;

import ava.androidchef.models.menu.Menu;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class DisplayMenuPresenter {

    private DisplayMenuFragment fragment;

    public DisplayMenuPresenter(DisplayMenuFragment fragment) {
        this.fragment = fragment;
    }

    public void onFragmentCreate(String intent) {

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        Menu menu;

        switch (intent) {
            case "create_menu":
                menu = createMenu();
                menuDAO.saveMenu(menu);
                break;
            case "display_menu":
                menu = menuDAO.getMenu();
                break;
            default:
                menu = null;
        }

        if (menu != null) {
            fragment.displayMenu(menu);
        }
    }

    public Menu createMenu() {
        String menuTitle = fragment.readMenuTitle();
        int menuLength = fragment.readMenuLength();

        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        ArrayList<Recipe> recipes = recipeDAO.selectRandomMenu(menuLength);

        return new Menu(menuTitle, menuLength, recipes);
    }

    public void onReplaceButtonClick(ArrayList<Recipe> currentRecipes, int index) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        Recipe newRecipe = recipeDAO.selectRandomRecipe(currentRecipes);

        Menu menu = new Menu("newMenu", currentRecipes);
        currentRecipes.set(index, newRecipe);
        fragment.displayMenu(menu);

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        menuDAO.saveMenu(menu);
    }
}
