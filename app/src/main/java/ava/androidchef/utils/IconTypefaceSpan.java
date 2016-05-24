package ava.androidchef.utils;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public class IconTypefaceSpan extends MetricAffectingSpan {

    private Typeface iconFont;

    public IconTypefaceSpan(Typeface iconFont) {
        this.iconFont = iconFont;
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyIconFont(paint);
    }

    @Override
    public void updateDrawState(TextPaint paint) {
        applyIconFont(paint);
    }

    public void applyIconFont(Paint paint) {
        paint.setTypeface(iconFont);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(54);
    }
}
