package ava.androidchef.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class NavigationButton extends Button {

    public NavigationButton(Context context) {
        super(context);
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = (width > height ? height : width) / 2 - 24;
        setMeasuredDimension(size, size);
    }
}
