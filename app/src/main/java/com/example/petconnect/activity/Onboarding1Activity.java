package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.petconnect.R;

public class Onboarding1Activity extends AppCompatActivity {
    private static final int DURATION = 2000;
    private View onboardingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding1);

        onboardingView = findViewById(R.id.onboardingView);

        onboardingView.setAlpha(0f);
        onboardingView.animate()
                .alpha(1f)
                .setDuration(2000) // Fade in duration
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPreferences = getSharedPreferences("AccountPreference", Context.MODE_PRIVATE);
                                Intent intent;

                                intent = new Intent(Onboarding1Activity.this, Onboarding2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, DURATION);
                    }
                });
    }
}