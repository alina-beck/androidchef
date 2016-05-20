package ava.androidchef.features.editrecipe;

import java.util.LinkedHashMap;
import java.util.Map;

import ava.androidchef.features.addrecipe.EnterIngredientsFragment;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.utils.BaseIngredientsPresenter;
import ava.androidchef.utils.InputValidator;

public class EditIngredientsPresenter extends BaseIngredientsPresenter {

    EnterIngredientsFragment fragment;
    Recipe recipe;

    public EditIngredientsPresenter(EnterIngredientsFragment fragment, Recipe recipe) {
        super(fragment.getActivity());
        this.fragment = fragment;
        this.recipe = recipe;
    }

    @Override
    public void viewCreated() {
        int position = 0;

        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredients().entrySet()) {
            String ingredientName = entry.getKey().getName();
            String ingredientUnit = entry.getKey().getUnit();
            String amount = entry.getValue().toString();

            fragment.fillIngredientRow(ingredientName, ingredientUnit, amount, position);
            position++;
        }
    }

    @Override
    public void saveRecipeButtonClicked() {
        LinkedHashMap<Ingredient, Integer> enteredIngredients = new LinkedHashMap<>();
        InputValidator validator = new InputValidator(fragment.getActivity());

        int numberOfIngredients = fragment.getNumberOfIngredientInputRows();

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
}
