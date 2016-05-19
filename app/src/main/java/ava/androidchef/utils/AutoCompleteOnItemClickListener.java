package ava.androidchef.utils;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

public class AutoCompleteOnItemClickListener implements AdapterView.OnItemClickListener {

    private LinearLayout selectedRow;
    private AdapterView.OnItemClickListener originalListener;

    public AutoCompleteOnItemClickListener(LinearLayout selectedRow, AdapterView.OnItemClickListener originalListener) {
        this.selectedRow = selectedRow;
        this.originalListener = originalListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        originalListener.onItemClick(parent, selectedRow, position, id);
    }
}
