package com.example.petconnect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CustomPostBox extends LinearLayout {
    public CustomPostBox(Context context) {
        super(context);
        initializeViews(context, null);
    }
    public CustomPostBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }
    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_post_box, this);
    }
}
