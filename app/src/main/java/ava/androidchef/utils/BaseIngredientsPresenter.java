package ava.androidchef.utils;

import android.content.Context;

import java.util.ArrayList;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;

public abstract class BaseIngredientsPresenter {

    private IngredientDAO ingredientDAO;

    public BaseIngredientsPresenter(Context context) {
        this.ingredientDAO = IngredientDAO.getInstance(context);
    }

    public ArrayList<String> getAllIngredientNames() {
        ArrayList<Ingredient> allIngredients = ingredientDAO.selectAllIngredients();
        ArrayList<String> allIngredientNames = new ArrayList<>();

        for (Ingredient i : allIngredients) {
            allIngredientNames.add(i.getName());
        }

        return allIngredientNames;
    }

    public ArrayList<String> getAllUnits() {
        return Unit.getUnits();
    }

    public String getUnit(String selectedIngredientName) {
        ArrayList<Ingredient> allIngredients = ingredientDAO.selectAllIngredients();
        for (Ingredient i : allIngredients) {
            if (selectedIngredientName.equals(i.getName())) {
                return i.getUnit();
            }
        }
        return null;
    }

    public abstract void saveRecipeButtonClicked();

    public abstract void viewCreated();
}
