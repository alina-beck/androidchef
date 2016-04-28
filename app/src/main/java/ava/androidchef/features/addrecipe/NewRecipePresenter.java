package ava.androidchef.features.addrecipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class NewRecipePresenter {

    private NewRecipeFragment fragment;
    private IngredientDAO ingredientDAO;

    public NewRecipePresenter(NewRecipeFragment fragment) {
        this.fragment = fragment;
        this.ingredientDAO = new IngredientDAO(fragment.getActivity());
    }

    public void onButtonClick() {
        String title = fragment.getRecipeInput();
        Recipe recipe = new Recipe (title);

        LinkedHashMap<Ingredient, Integer> recipesIngredients = recipe.getIngredients();
        ArrayList<ArrayList<String>> ingredients = fragment.getIngredientInput();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = new Ingredient(ingredients.get(i).get(0), ingredients.get(i).get(1));
            recipesIngredients.put(ingredient, Integer.parseInt(ingredients.get(i).get(2)));
        }

        LinkedHashMap<Ingredient, Integer> updatedIngredients = ingredientDAO.insertIngredients(recipesIngredients);

        recipe.setIngredients(updatedIngredients);
        RecipeDAO recipeDAO = new RecipeDAO(fragment.getActivity());

        boolean didSave = (recipeDAO.insertRecipe(recipe) != -1);
        fragment.saveComplete(didSave);
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredientDAO.getAllIngredients();
    }
}
