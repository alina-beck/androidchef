package ava.androidchef.features.onboarding;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import ava.androidchef.R;
import ava.androidchef.database.DbHelper;

public class WelcomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (savedInstanceState != null) {
            return;
        }

        NavigationFragment navigationFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_welcome, navigationFragment).commit();

    }
}
