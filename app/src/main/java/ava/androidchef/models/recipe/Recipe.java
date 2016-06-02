package ava.androidchef.models.recipe;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;

public class Recipe implements Parcelable {
    private long id;
    private String title;
    private LinkedHashMap<Ingredient, Integer> ingredients;
    private String instructions;

    public Recipe(String title, LinkedHashMap<Ingredient, Integer> ingredients, String instructions) {
        this(-1, title, ingredients, instructions);
    }

    // TODO: see if usage can be eliminated
    public Recipe(long id, String title, String instructions) {
        this(id, title, new LinkedHashMap<Ingredient, Integer>(), instructions);
    }

    public Recipe(long id, String title, LinkedHashMap<Ingredient, Integer> ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(Recipe copiedRecipe) {
        this.id = copiedRecipe.getId();
        this.title = copiedRecipe.getTitle();
        this.ingredients = copiedRecipe.getIngredients();
        this.instructions = copiedRecipe.getInstructions();
    }

    @Override
    public String toString() {
        return title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LinkedHashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedHashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        Bundle ingredientsMap = new Bundle();
        ingredientsMap.putSerializable("ingredients_map", ingredients);
        System.out.println("map: " + ingredientsMap.toString());

        dest.writeLong(id);
        dest.writeString(title);
        dest.writeBundle(ingredientsMap);
        dest.writeString(instructions);
    }

    private Recipe(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.ingredients = (LinkedHashMap<Ingredient, Integer>) in.readBundle().getSerializable("ingredients_map");
        this.instructions = in.readString();
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Recipe that = (Recipe) o;

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
