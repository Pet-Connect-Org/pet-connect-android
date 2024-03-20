package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.petconnect.R;
import com.example.petconnect.manager.UserManager;

public class Onboarding1Activity extends AppCompatActivity {
    private static final int DURATION = 2000;
    private View onboardingView;
    UserManager userManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userManager = new UserManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding1);
        onboardingView = findViewById(R.id.onboardingView);
        String token = userManager.getAccessToken();

        if (token != null) {
            intent = new Intent(Onboarding1Activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
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
                                intent = new Intent(Onboarding1Activity.this, Onboarding2Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, DURATION);
                    }
                });
    }
}