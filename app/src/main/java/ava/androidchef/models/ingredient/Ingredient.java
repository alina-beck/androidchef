package ava.androidchef.models.ingredient;

public class Ingredient {

    private int id;
    private String name;
    private enum Unit {
        PIECE, GRAM, MILLILITRE
    }
    private Unit unit;

    public Ingredient(String name, String unit) {
        this(-1, name, unit);
    }

    public Ingredient(int id, String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = Unit.valueOf(unit);
    }
}
