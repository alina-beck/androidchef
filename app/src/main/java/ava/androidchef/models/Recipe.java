package ava.androidchef.models;

public class Recipe {
    private String title;

    public Recipe(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
