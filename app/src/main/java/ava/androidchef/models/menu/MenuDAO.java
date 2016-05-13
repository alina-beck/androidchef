package ava.androidchef.models.menu;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Map;

import ava.androidchef.models.recipe.Recipe;

public class MenuDAO {

    private static MenuDAO singletonInstance;
    private SharedPreferences sharedPreferences;

    public static MenuDAO getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new MenuDAO(context.getApplicationContext());
        }
        return singletonInstance;
    }

    private MenuDAO(Context context) {
        this.sharedPreferences = context.getSharedPreferences("currentMenu", Context.MODE_PRIVATE);
    }

    public void saveMenu(Menu menu) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        String menuAsString = gson.toJson(menu);
        System.out.println("this menu gets saved: " + menuAsString);

        editor.putString("current_menu", menuAsString);
        editor.apply();
    }

    public Menu getMenu() {
        String json = sharedPreferences.getString("current_menu", "no menu");
        System.out.println("this menu comes from sharedPrefs: " + json);
        Gson gson = new Gson();
        return gson.fromJson(json, Menu.class);
    }
}
