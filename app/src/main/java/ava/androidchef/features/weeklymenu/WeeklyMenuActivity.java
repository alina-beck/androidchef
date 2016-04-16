package ava.androidchef.features.weeklymenu;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import ava.androidchef.R;

public class WeeklyMenuActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_menu);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_weekly_menu, new WeeklyMenuFragment()).commit();
    }
}
