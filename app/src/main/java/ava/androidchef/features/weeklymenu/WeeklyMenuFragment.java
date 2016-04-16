package ava.androidchef.features.weeklymenu;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ava.androidchef.R;

public class WeeklyMenuFragment extends ListFragment {

    public WeeklyMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly_menu, container, false);
    }

}
