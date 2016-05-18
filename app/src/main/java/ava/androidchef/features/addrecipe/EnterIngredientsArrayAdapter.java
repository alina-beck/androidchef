package ava.androidchef.features.addrecipe;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ava.androidchef.models.ingredient.Ingredient;

public class EnterIngredientsArrayAdapter extends ArrayAdapter<Ingredient> {

    private ArrayList<Ingredient> ingredients;

    public EnterIngredientsArrayAdapter(Context context, int layout, ArrayList<Ingredient> ingredients) {
        super(context, layout, ingredients);
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredientNames() {
        ArrayList<String> ingredientNames = new ArrayList<>();
        for (Ingredient i : ingredients) {
            ingredientNames.add(i.getName());
        }
        return ingredientNames;
    }

    public Ingredient getIngredientByName(String ingredientName) {
        Ingredient existingIngredient = null;
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredientName.equals(ingredients.get(i).getName())) {
                existingIngredient = ingredients.get(i);
                break;
            }
        }
        return existingIngredient;
    }
}
