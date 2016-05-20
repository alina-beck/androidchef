package ava.androidchef.features.addrecipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.utils.InputValidator;
import ava.androidchef.utils.Unit;

public class EnterIngredientsPresenter {

    private EnterIngredientsFragment fragment;
    private IngredientDAO ingredientDAO;
    private ArrayList<Ingredient> ingredients;

    public EnterIngredientsPresenter(EnterIngredientsFragment fragment) {
        this.fragment = fragment;
        this.ingredientDAO = IngredientDAO.getInstance(fragment.getActivity());
        this.ingredients = ingredientDAO.selectAllIngredients();
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
        for (Ingredient i : ingredients) {
            if (selectedIngredientName.equals(i.getName())) {
                return i.getUnit();
            }
        }
        return null;
    }
    //TODO: split method into validateUserInput() and saveIngredients()
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
        long newIngredientId = ingredientDAO.insertIngredient(ingredient);
        return ingredientDAO.selectIngredientById(newIngredientId);
    }
}
