package ava.androidchef;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewRecipePresenter {

    public static void onClick(View view) {
        EditText inputTitle = (EditText) view.findViewById(R.id.input_recipe_title);
        String title = inputTitle.toString();

        Recipe newRecipe = new Recipe (title);

        if(RecipeDAO.insertRecipe(newRecipe)) {
            Toast.makeText(view.getContext(), "Recipe saved", Toast.LENGTH_LONG).show();
        }

    }
}
