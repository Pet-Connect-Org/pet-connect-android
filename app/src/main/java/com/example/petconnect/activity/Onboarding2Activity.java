package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petconnect.R;

public class Onboarding2Activity extends AppCompatActivity {

    Button toLoginScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);

        toLoginScreen = findViewById(R.id.toLoginScreen);

        toLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Onboarding2Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}