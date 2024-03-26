package com.example.petconnect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedAccount;

public class CustomAvatar extends LinearLayout {
    Button button;

    public CustomAvatar(Context context) {
        super(context);
        initializeViews(context, null);
    }

    public CustomAvatar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_avatar, this);

        button = findViewById(R.id.avatar);

        UserManager userManager = new UserManager(context);
        ExtendedAccount user = userManager.getUser();

        if (user != null && user.getUser() != null && user.getUser().getName() != null) {
            setName(user.getUser().getName());
        }
    }

    public void setName(String name) {
        if (button != null) {
            button.setText(name.toUpperCase().charAt(0));
        }
    }
}
