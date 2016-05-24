package ava.androidchef.features.onboarding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ava.androidchef.R;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        if (savedInstanceState != null) {
            return;
        }

        NavigationFragment navigationFragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_navigation, navigationFragment).commit();
    }
}
