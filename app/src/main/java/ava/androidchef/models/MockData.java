package ava.androidchef.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class MockData {
    private static MockData singletonMockData;
    private Context appContext;
    private ArrayList<Recipe> recipes;

    private MockData (Context context) {
        this.appContext = context;
        this.recipes = new ArrayList<Recipe>();
        for (int i = 0; i <= 15; i++) {
            Recipe recipe = new Recipe("Recipe " + i);
            recipes.add(recipe);
        }
    }

    public static MockData getSingletonMockData(Context context) {
        if (singletonMockData == null) {
            singletonMockData = new MockData(context.getApplicationContext());
        }
        return singletonMockData;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

}
