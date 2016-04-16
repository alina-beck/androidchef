package ava.androidchef.models;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ava.androidchef.database.DbHelper;

public class RecipeCursorAdapter extends CursorAdapter {


    public RecipeCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView recipeTitle = (TextView) view.findViewById(android.R.id.text1);
        String title = cursor.getString(1);
        recipeTitle.setText(title);
    }
}
