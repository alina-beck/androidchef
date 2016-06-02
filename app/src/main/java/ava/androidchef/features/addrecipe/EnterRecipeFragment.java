package ava.androidchef.features.addrecipe;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedHashMap;

import ava.androidchef.R;
import ava.androidchef.features.allrecipes.AllRecipesActivity;
import ava.androidchef.features.displaymenu.DisplayMenuActivity;
import ava.androidchef.features.editrecipe.EditRecipePresenter;
import ava.androidchef.models.ingredient.Ingredient;
import ava.androidchef.models.recipe.Recipe;
import ava.androidchef.utils.BaseRecipePresenter;

public class EnterRecipeFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private BaseRecipePresenter presenter;

    public EnterRecipeFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_recipe, container, false);
        setHasOptionsMenu(true);

        EnterIngredientsFragment enterIngredientsFragment = new EnterIngredientsFragment();

        if (getArguments() == null) {
            this.presenter = new EnterRecipePresenter(this);
            getActivity().setTitle(R.string.new_recipe);
        }
        else {
            enterIngredientsFragment.setArguments(getArguments());
            Recipe recipe = getArguments().getParcelable(getString(R.string.editing_recipe));
            this.presenter = new EditRecipePresenter(this, recipe);
            getActivity().setTitle(R.string.edit_recipe);
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.nested_fragment_container, enterIngredientsFragment);
        fragmentTransaction.commit();

        EditText titleInput = (EditText) view.findViewById(R.id.input_recipe_title);
        titleInput.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.viewCreated();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.action_edit);
        Button editIcon = (Button) item.getActionView();

        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "Flaticon.ttf");
        editIcon.setTypeface(iconfont);
        editIcon.setText(R.string.icon_save);
        editIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        editIcon.setBackgroundColor(Color.TRANSPARENT);
        editIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.saveRecipeButtonClicked();
    }

    public String getRecipeTitle() {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        return inputTitle.getText().toString();
    }

    public void setRecipeTitle(String recipeTitle) {
        EditText inputTitle = (EditText) getView().findViewById(R.id.input_recipe_title);
        inputTitle.setText(recipeTitle);
    }

    public String getRecipeInstructions() {
        EditText inputInstructions = (EditText) getView().findViewById(R.id.input_recipe_instructions);
        return inputInstructions.getText().toString();
    }

    public void setRecipeInstructions(String recipeInstructions) {
        EditText inputInstructions = (EditText) getView().findViewById(R.id.input_recipe_instructions);
        inputInstructions.setText(recipeInstructions);
    }

    public void saveIngredients() {
        EnterIngredientsFragment enterIngredientsFragment =
                (EnterIngredientsFragment) getChildFragmentManager().findFragmentById(R.id.nested_fragment_container);
        enterIngredientsFragment.saveRecipeButtonClicked();
    }

    public void ingredientsSaved(LinkedHashMap<Ingredient, Integer> ingredients) {
        presenter.ingredientsSaved(ingredients);
    }

    public void alert(String alertMessage) {
        Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            AutoCompleteTextView ingredientTitle = (AutoCompleteTextView) getView().findViewById(R.id.input_ingredient_name);
            handled = ingredientTitle.requestFocus();
        }
        return handled;
    }

    public void resetInputFields() {
        ((AddRecipeActivity) getActivity()).resetInputFields();
    }

    public void displayRecipe(Recipe recipe) {
        // TODO: implement interface to be independent from Activities
        if (getActivity() instanceof DisplayMenuActivity) {
            ((DisplayMenuActivity) getActivity()).recipeSelected(recipe);
        }
        else if (getActivity() instanceof AllRecipesActivity) {
            ((AllRecipesActivity) getActivity()).onRecipeSelected(recipe);
        }
    }
}
