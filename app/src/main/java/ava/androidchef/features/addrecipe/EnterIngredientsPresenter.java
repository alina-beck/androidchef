package ava.androidchef.features.addrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.utils.BaseIngredientsPresenter;
import ava.androidchef.utils.InputValidator;

public class EnterIngredientsPresenter extends BaseIngredientsPresenter {

    private EnterIngredientsFragment fragment;
    private IngredientDAO ingredientDAO;

    public EnterIngredientsPresenter(EnterIngredientsFragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
    }

    @Override
    public void saveRecipeButtonClicked() {
        LinkedHashMap<Ingredient, Integer> enteredIngredients = getEnteredIngredients();
        if (enteredIngredients == null) {
            return;
        }
        fragment.ingredientsSaved(enteredIngredients);
    }

    //TODO: split method into validateUserInput() and saveIngredients() so that parts can be reused in EditIngredientsPresenter & EnterIngredientsPresenter
    private LinkedHashMap<Ingredient, Integer> getEnteredIngredients() {
        LinkedHashMap<Ingredient, Integer> enteredIngredients = new LinkedHashMap<>();
        InputValidator validator = new InputValidator(fragment.getActivity());

        int numberOfIngredientRows = fragment.getNumberOfIngredientInputRows();
        if (numberOfIngredientRows <= 1) {
            fragment.alert("Please enter ingredients for your recipe!");
            return null;
        }

        for (int i = 0; i < numberOfIngredientRows; i++) {
            String ingredientName = fragment.getIngredientNameAt(i);
            String ingredientUnit = fragment.getIngredientUnitAt(i);
            String amount = fragment.getAmountAt(i);

            if (validator.isEmptyString(ingredientName)) {
                continue;
            }
            if (validator.isEmptyString(ingredientUnit)) {
                fragment.alert("Please choose a unit for " + ingredientName);
                return null;
            }
            if (validator.isEmptyString(amount)) {
                fragment.alert("Please enter an amount for " + ingredientName);
                return null;
            }

            if (validator.ingredientWasEnteredTwice(enteredIngredients, ingredientName)) {
                fragment.alert("You cannot enter the same ingredient twice! Please replace " + ingredientName);
                return null;
            }

            if (validator.ingredientExistsInDatabase(ingredientName)) {
                Ingredient existingIngredient = ingredientDAO.selectIngredientByName(ingredientName);

                if (validator.ingredientUnitMatches(ingredientName, ingredientUnit)) {
                    enteredIngredients.put(existingIngredient, Integer.parseInt(amount));
                }
                else {
                    fragment.alert(ingredientName + " already exists, using " + existingIngredient.getUnit() + ". Please adapt your input!");
                    return null;
                }
            }
            else {
                long newIngredientId = ingredientDAO.insertIngredient(new Ingredient(ingredientName, ingredientUnit));
                Ingredient savedIngredient = ingredientDAO.selectIngredientById(newIngredientId);
                enteredIngredients.put(savedIngredient, Integer.parseInt(amount));
            }
        }
        if (enteredIngredients.size() == 0) {
            fragment.alert("Please enter ingredients for your recipe!");
            return null;
        }
        return enteredIngredients;
    }

    @Override
    public void viewCreated() {
        //TODO: avoid empty method just because other subclass needs it
    }
}
