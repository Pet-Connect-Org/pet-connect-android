package com.example.petconnect;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.petconnect.activity.CreateNewPostActivity;

public class CustomPostBox extends LinearLayout {
    Button txtToCreatePost;
    CustomAvatar post_box_user_avatar;
    public CustomPostBox(Context context) {
        super(context);
        initializeViews(context, null);
    }
    public CustomPostBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    public void setName(String name) {
        post_box_user_avatar.setName(name);
    }
    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_post_box, this);
        post_box_user_avatar = findViewById(R.id.post_box_user_avatar);
        txtToCreatePost = findViewById(R.id.to_create_post);
        txtToCreatePost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateNewPostActivity.class);
                getContext().startActivity(intent);
            }
        });{

        }
    }


}
