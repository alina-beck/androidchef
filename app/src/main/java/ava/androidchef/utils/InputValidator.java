package ava.androidchef.utils;

import android.content.Context;

import ava.androidchef.models.ingredient.IngredientDAO;

public class InputValidator {

    private IngredientDAO ingredientDAO;

    public InputValidator(Context context) {
        this.ingredientDAO = IngredientDAO.getInstance(context);
    }

    public boolean ingredientExistsInDatabase(String ingredientName) {
        return (ingredientDAO.selectIngredientByName(ingredientName) != null);
    }

    public boolean ingredientUnitMatches(String ingredientName, String ingredientUnit) {
        String unitInDatabase = ingredientDAO.selectIngredientByName(ingredientName).getUnit();
        return (ingredientUnit.equals(unitInDatabase));
    }

}
