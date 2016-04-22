package ava.androidchef.utils;

import java.util.ArrayList;

public abstract class Unit {
    private static final String PIECE = "pc";
    private static final String GRAM = "g";
    private static final String MILLILITRE = "ml";

    public static ArrayList<String> getUnits() {
        ArrayList<String> units = new ArrayList<>();
        units.add(PIECE);
        units.add(GRAM);
        units.add(MILLILITRE);

        return units;
    }
}