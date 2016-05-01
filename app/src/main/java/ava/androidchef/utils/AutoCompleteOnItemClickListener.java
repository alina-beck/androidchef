package ava.androidchef.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

public class AutoCompleteOnItemClickListener implements AdapterView.OnItemClickListener {

    private LinearLayout selectedLine;
    private AdapterView.OnItemClickListener originalListener;

    public AutoCompleteOnItemClickListener(LinearLayout selectedLine, AdapterView.OnItemClickListener originalListener) {
        this.selectedLine = selectedLine;
        this.originalListener = originalListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        originalListener.onItemClick(parent, selectedLine, position, id);
    }
}
