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

    public String getUnit(String selectedIngredientName) {
        for (Ingredient i : ingredients) {
            if (selectedIngredientName.equals(i.getName())) {
                return i.getUnit();
            }
        }
        return null;
    }

    public void saveRecipeButtonClicked() {
        LinkedHashMap<Ingredient, Integer> enteredIngredients = new LinkedHashMap<>();
        InputValidator validator = new InputValidator(fragment.getActivity());
        int numberOfIngredientInputLines = fragment.getNumberOfIngredients();
        if (numberOfIngredientInputLines <= 1) {
            fragment.alert("Please enter ingredients for your recipe!");
            return;
        }

        for (int i = 0; i < numberOfIngredientInputLines; i++) {
            String ingredientName = fragment.getIngredientNameAt(i);
            if (ingredientName.equals("")) {
                continue;
            }
            //TODO: check if ingredient unit is empty or 0
            String ingredientUnit = fragment.getIngredientUnitAt(i);
            String amount = fragment.getAmountAt(i);
            if (amount.equals("")) {
                fragment.alert("Please enter an amount for " + ingredientName);
                return;
            }

            //TODO: find more elegant solution to avoid duplicates
            String ingredientHashMapAsString = enteredIngredients.toString();
            if (ingredientHashMapAsString.toLowerCase().contains(ingredientName.toLowerCase())) {
                fragment.alert("You cannot enter the same ingredient twice! Please replace " + ingredientName);
                return;
            }

            boolean existsInDatabase = validator.ingredientExistsInDatabase(ingredientName);

            if (existsInDatabase) {
                Ingredient existingIngredient = ingredientDAO.selectIngredientByName(ingredientName);
                boolean unitMatches = validator.ingredientUnitMatches(ingredientName, ingredientUnit);

                if (unitMatches) {
                    enteredIngredients.put(existingIngredient, Integer.parseInt(amount));
                }
                else {
                    String alertMessage = ingredientName + " already exists, using " + existingIngredient.getUnit() + ". Please adapt your input!";
                    fragment.alert(alertMessage);
                    return;
                }
            }
            else {
                Ingredient newIngredient = new Ingredient(ingredientName, ingredientUnit);
                long newIngredientId = ingredientDAO.insertIngredient(newIngredient);
                Ingredient savedIngredient = ingredientDAO.selectIngredientById(newIngredientId);
                enteredIngredients.put(savedIngredient, Integer.parseInt(amount));
            }
        }

        fragment.ingredientsSaved(enteredIngredients);
    }
}
