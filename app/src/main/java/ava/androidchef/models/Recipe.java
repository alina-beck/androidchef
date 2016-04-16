package ava.androidchef.models;

public class Recipe {
    private int id;
    private String title;

    public Recipe(String title) {
        this(-1, title);
    }

    public Recipe(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
