package ava.androidchef.features.addrecipe;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.utils.BaseIngredientsPresenter;
import ava.androidchef.utils.InputValidator;

public class EnterIngredientsPresenter extends BaseIngredientsPresenter {

    private EnterIngredientsFragment fragment;

    public EnterIngredientsPresenter(EnterIngredientsFragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

    //TODO: split method into validateUserInput() and saveIngredients()
    //TODO: split method so that most part can be reused in EditIngredientsPresenter & EnterIngredientsPresenter
    @Override
    public void saveRecipeButtonClicked() {
        LinkedHashMap<Ingredient, Integer> enteredIngredients = new LinkedHashMap<>();
        InputValidator validator = new InputValidator(fragment.getActivity());

        int numberOfIngredients = fragment.getNumberOfIngredientInputRows();
        if (numberOfIngredients <= 1) {
            fragment.alert("Please enter ingredients for your recipe!");
            return;
        }

        for (int i = 0; i < numberOfIngredients; i++) {
            String ingredientName = fragment.getIngredientNameAt(i);
            String ingredientUnit = fragment.getIngredientUnitAt(i);
            String amount = fragment.getAmountAt(i);

            //TODO: make sure users did not only enter empty rows
            if (ingredientName.trim().equals("")) {
                continue;
            }
            if (ingredientUnit.equals("Select a unit")) {
                fragment.alert("Please choose a unit for " + ingredientName);
                return;
            }
            if (amount.trim().equals("")) {
                fragment.alert("Please enter an amount for " + ingredientName);
                return;
            }

            if (validator.ingredientWasEnteredTwice(enteredIngredients, ingredientName)) {
                fragment.alert("You cannot enter the same ingredient twice! Please replace " + ingredientName);
                return;
            }

            if (validator.ingredientExistsInDatabase(ingredientName)) {
                IngredientDAO ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
                Ingredient existingIngredient = ingredientDAO.selectIngredientByName(ingredientName);

                if (validator.ingredientUnitMatches(ingredientName, ingredientUnit)) {
                    enteredIngredients.put(existingIngredient, Integer.parseInt(amount));
                }
                else {
                    fragment.alert(ingredientName + " already exists, using " + existingIngredient.getUnit() + ". Please adapt your input!");
                    return;
                }
            }
            else {
                Ingredient newIngredient = getNewIngredientFromDatabase(new Ingredient(ingredientName, ingredientUnit));
                enteredIngredients.put(newIngredient, Integer.parseInt(amount));
            }
        }
        fragment.ingredientsSaved(enteredIngredients);
    }

    public Ingredient getNewIngredientFromDatabase(Ingredient ingredient) {
        IngredientDAO ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
        long newIngredientId = ingredientDAO.insertIngredient(ingredient);
        return ingredientDAO.selectIngredientById(newIngredientId);
    }

    @Override
    public void viewCreated() {
        //TODO: avoid empty method just because other subclass needs it
    }
}
