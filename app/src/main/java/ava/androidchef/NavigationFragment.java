package ava.androidchef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NavigationFragment extends Fragment implements View.OnClickListener {

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        Button button1 = (Button) view.findViewById(R.id.button_new_recipe);
        button1.setOnClickListener(this);

        Button button2 = (Button) view.findViewById(R.id.button_get_menu);
        button2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_new_recipe:
                Intent intent = new Intent(getActivity(), NewRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.button_get_menu:
                break;
        }
    }

}
