package ava.androidchef.features.allrecipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ava.androidchef.R;

public class AllRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_all_recipes, new AllRecipesFragment()).commit();
    }
}
