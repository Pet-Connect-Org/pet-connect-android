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

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000; // 2 seconds
    private View splashView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashView = findViewById(R.id.splash_view);
        splashView.setAlpha(0f);
        splashView.animate()
                .alpha(1f)
                .setDuration(1000) // Fade in duration
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPreferences = getSharedPreferences("AccountPreference", Context.MODE_PRIVATE);
                                Intent intent;
                                intent = new Intent(SplashActivity.this, Onboarding1Activity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, SPLASH_DURATION);
                    }
                });
    }
}