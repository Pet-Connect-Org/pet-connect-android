package com.example.petconnect;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.petconnect.activity.MainActivity;

public class CustomPostBox extends LinearLayout {
    Button txtToCreatePost;
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
        txtToCreatePost = findViewById(R.id.to_create_post);
        txtToCreatePost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),activity_createnewpost.class);
                getContext().startActivity(intent);
            }
        });{

        }
    }


}
