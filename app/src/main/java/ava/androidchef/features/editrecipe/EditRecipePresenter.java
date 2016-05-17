package ava.androidchef.features.editrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class EditRecipePresenter {

    private EditRecipeFragment fragment;

    public EditRecipePresenter(EditRecipeFragment fragment) {
        this.fragment = fragment;
    }

    public void updateButtonClicked(Recipe recipe) {
        recipe.setTitle(fragment.getTitleInput());
        recipe.setInstructions(fragment.getInstructionsInput());

        LinkedHashMap<Ingredient, Integer> updatedIngredients = updateIngredients();
        recipe.setIngredients(updatedIngredients);

        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());

        //TODO: give visible feedback to user
        boolean isUpdated = recipeDAO.updateRecipe(recipe);
        System.out.println(isUpdated);
    }

    public LinkedHashMap<Ingredient, Integer> updateIngredients() {
        LinkedHashMap<Ingredient, Integer> ingredientsFromUser = fragment.getIngredientInput();

        IngredientDAO ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
        System.out.println(ingredientsFromUser);

        return ingredientDAO.updateIngredients(ingredientsFromUser);
    }
}
