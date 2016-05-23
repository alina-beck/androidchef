package ava.androidchef.utils;

import android.content.Context;

import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.RecipeDAO;

public class InputValidator {

    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;

    public InputValidator(Context context) {
        this.ingredientDAO = IngredientDAO.getInstance(context);
        this.recipeDAO = RecipeDAO.getInstance(context);
    }

    public boolean isEmptyString(String input) {
        return (input.trim().equals(""));
    }

    public boolean ingredientExistsInDatabase(String ingredientName) {
        return (ingredientDAO.selectIngredientByName(ingredientName) != null);
    }

    public boolean ingredientUnitMatches(String ingredientName, String ingredientUnit) {
        String unitInDatabase = ingredientDAO.selectIngredientByName(ingredientName).getUnit();
        return (ingredientUnit.equals(unitInDatabase));
    }

    public boolean ingredientWasEnteredTwice(LinkedHashMap<Ingredient, Integer> enteredIngredients, String newIngredient) {
        for (Map.Entry<Ingredient, Integer> entry : enteredIngredients.entrySet()) {
            if (entry.getKey().getName().toLowerCase().equals(newIngredient.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isRecipeTitleInvalid(String recipeTitle, String originalTitle) {
        return (!originalTitle.toLowerCase().equals(recipeTitle.toLowerCase()) && recipeExistsInDatabase(recipeTitle));
    }

    public boolean recipeExistsInDatabase(String recipeTitle) {
        return (recipeDAO.selectSimpleRecipeByName(recipeTitle) != null);
    }

    public boolean menuLengthExceedsExistingRecipes(int menuLength) {
        return (recipeDAO.getNumberOfRecipes() < menuLength);
    }

}
