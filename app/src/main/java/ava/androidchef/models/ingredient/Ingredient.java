package ava.androidchef.models.ingredient;

public class Ingredient {

    private int id;
    private String name;
    private String unit;

    public Ingredient(String name, String unit) {
        this(-1, name, unit);
    }

    public Ingredient(int id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return name;
    }
}
