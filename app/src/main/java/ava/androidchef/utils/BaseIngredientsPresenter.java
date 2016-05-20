package ava.androidchef.utils;

import android.content.Context;

import java.util.ArrayList;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;

public abstract class BaseIngredientsPresenter {

    private Context context;

    public BaseIngredientsPresenter(Context context) {
        this.context = context;
    }

    public ArrayList<String> getAllIngredientNames() {
        IngredientDAO ingredientDAO = IngredientDAO.getInstance(context);
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
        IngredientDAO ingredientDAO = IngredientDAO.getInstance(context);
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
