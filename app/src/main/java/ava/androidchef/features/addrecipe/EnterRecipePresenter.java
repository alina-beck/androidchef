package ava.androidchef.features.addrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;
import ava.androidchef.utils.InputValidator;

public class EnterRecipePresenter {

    private EnterRecipeFragment fragment;

    public EnterRecipePresenter(EnterRecipeFragment fragment) {
        this.fragment = fragment;
    }

    //TODO: split method into validateUserInput() and saveRecipe()
    public void saveButtonClicked(LinkedHashMap<Ingredient, Integer> savedIngredients) {
        InputValidator validator = new InputValidator(fragment.getActivity());

        String recipeTitle = fragment.getRecipeTitle();
        String recipeInstructions = fragment.getRecipeInstructions();

        if (recipeTitle.trim().equals("")) {
            fragment.alert("Please enter a title for your recipe!");
            return;
        }
        if (recipeInstructions.trim().equals("")) {
            fragment.alert("Please enter instructions for your recipe!");
            return;
        }

        if(validator.recipeExistsInDatabase(recipeTitle)) {
            fragment.alert("This recipe already exists. Please enter a new one!");
            return;
        }

        Recipe recipe = new Recipe (recipeTitle, savedIngredients, recipeInstructions);
        if (saveRecipe(recipe)) {
            fragment.alert("Save successful!");
        }
        else {
            fragment.alert("Error when saving.");
        }
    }

    public boolean saveRecipe(Recipe recipe) {
        RecipeDAO recipeDAO = RecipeDAO.getInstance(fragment.getActivity());
        return (recipeDAO.insertRecipe(recipe) != -1);
    }
}
