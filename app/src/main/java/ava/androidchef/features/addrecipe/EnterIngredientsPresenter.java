package ava.androidchef.features.addrecipe;

import java.util.ArrayList;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;

public class EnterIngredientsPresenter {

    private IngredientDAO ingredientDAO;

    public EnterIngredientsPresenter(EnterIngredientsFragment fragment) {
        this.ingredientDAO = new IngredientDAO(fragment.getActivity());
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredientDAO.selectAllIngredients();
    }
}
