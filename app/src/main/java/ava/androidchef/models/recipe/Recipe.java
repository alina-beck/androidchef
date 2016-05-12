package ava.androidchef.models.recipe;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedHashMap;

import ava.androidchef.models.ingredient.Ingredient;

public class Recipe implements Parcelable {
    private int id;
    private String title;
    private LinkedHashMap<Ingredient, Integer> ingredients;
    private String instructions;

    public Recipe(String title, String instructions) {
        this(-1, title, new LinkedHashMap<Ingredient, Integer>(), instructions);
    }

    public Recipe(int id, String title, String instructions) {
        this(id, title, new LinkedHashMap<Ingredient, Integer>(), instructions);
    }

    public Recipe(int id, String title, LinkedHashMap<Ingredient, Integer> ingredients, String instructions) {
        this.id = id;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
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

    public String getInstructions() {
        return instructions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        Bundle ingredientsMap = new Bundle();
        ingredientsMap.putSerializable("ingredients_map", ingredients);

        dest.writeInt(id);
        dest.writeString(title);
        dest.writeBundle(ingredientsMap);
        dest.writeString(instructions);
    }

    private Recipe(Parcel in) {
        this.id = in.readInt();
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
}
