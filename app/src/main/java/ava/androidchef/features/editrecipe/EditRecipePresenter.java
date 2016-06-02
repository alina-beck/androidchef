package ava.androidchef.features.editrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.features.addrecipe.EnterRecipeFragment;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.menu.MenuDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;
import ava.androidchef.models.shoppinglist.ShoppingListDAO;
import ava.androidchef.utils.BaseRecipePresenter;
import ava.androidchef.utils.InputValidator;

public class EditRecipePresenter extends BaseRecipePresenter {

    private EnterRecipeFragment fragment;
    private Recipe recipe;

    public EditRecipePresenter(EnterRecipeFragment fragment, Recipe recipe) {
        this.fragment = fragment;
        this.recipe = recipe;
    }

    @Override
    public void viewCreated() {
        fragment.setRecipeTitle(recipe.getTitle());
        fragment.setRecipeInstructions(recipe.getInstructions());
    }

    @Override
    public void saveRecipeButtonClicked() {
        fragment.saveIngredients();
    }

    @Override
    public void ingredientsSaved(LinkedHashMap<Ingredient, Integer> ingredients) {
        InputValidator validator = new InputValidator(fragment.getActivity());

        String recipeTitle = fragment.getRecipeTitle();
        String recipeInstructions = fragment.getRecipeInstructions();

        if (validator.isEmptyString(recipeTitle)) {
            fragment.alert("Please enter a title for your recipe.");
            return;
        }

        if (validator.isEmptyString(recipeInstructions)) {
            fragment.alert("Please enter instructions for your recipe.");
            return;
        }

        if (validator.isRecipeTitleInvalid(recipeTitle, recipe.getTitle())) {
            fragment.alert("You already entered another recipe with that title.");
            return;
        }

        Recipe updatedRecipe = new Recipe(recipe);

        updatedRecipe.setTitle(recipeTitle);
        updatedRecipe.setInstructions(recipeInstructions);
        updatedRecipe.setIngredients(ingredients);

        MenuDAO menuDAO = MenuDAO.getInstance(fragment.getActivity());
        menuDAO.updateRecipeInMenu(recipe, updatedRecipe);

        if (menuDAO.menuContainsRecipe(recipe)) {
            ShoppingListDAO shoppingListDAO = ShoppingListDAO.getInstance(fragment.getActivity());
            shoppingListDAO.updateShoppingListWithRecipe(recipe, updatedRecipe);
        }

        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());

        if (recipeDAO.updateRecipe(updatedRecipe)) {
            fragment.alert("Recipe updated!");
            fragment.displayRecipe(updatedRecipe);
        }
    }

}
