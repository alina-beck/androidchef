package ava.androidchef.features.addrecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ava.androidchef.database.RelationDAO;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.ingredient.IngredientDAO;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.models.recipe.RecipeDAO;

public class NewRecipePresenter {

    private NewRecipeFragment fragment;

    public NewRecipePresenter(NewRecipeFragment fragment) {
        this.fragment = fragment;
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

        IngredientDAO ingredientDAO = new IngredientDAO(fragment.getActivity());
        LinkedHashMap<Ingredient, Integer> updatedIngredients = ingredientDAO.insertIngredients(recipesIngredients);

        boolean didSave = relationDAO.insertNewRelation(recipe);
        fragment.saveComplete(didSave);

    }
}
