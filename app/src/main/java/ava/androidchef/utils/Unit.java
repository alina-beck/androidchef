package ava.androidchef.utils;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Unit {
    private static final String PIECE = "pc";
    private static final String GRAM = "g";
    private static final String MILLILITRE = "ml";

    public static ArrayList<String> getUnits() {
        String[] units = {
                PIECE,
                GRAM,
                MILLILITRE
        };

        return (new ArrayList<>(Arrays.asList(units)));
    }
}
