package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.petconnect.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 5000; // 2 seconds
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

        ImageView yarnImageView = findViewById(R.id.yarnImageView);
        ImageView stringImageView = findViewById(R.id.stringImageView);

        // Animation cho yarnImageView: xoay 360 độ
        ObjectAnimator yarnRotation = ObjectAnimator.ofFloat(yarnImageView, "rotation", 0, 360);
        yarnRotation.setDuration(3000); // Thời gian animation

        // Animation cho yarnImageView: di chuyển từ trái qua phải
        ObjectAnimator yarnTranslationX = ObjectAnimator.ofFloat(yarnImageView, "translationX", 500);
        yarnTranslationX.setDuration(3000); // Thời gian animation

        // Animation cho stringImageView: di chuyển theo yarnImageView
        ObjectAnimator stringTranslationX = ObjectAnimator.ofFloat(stringImageView, "translationX", 500);
        stringTranslationX.setDuration(3000); // Thời gian animation

        // Tạo một AnimatorSet để kết hợp cả ba animation
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(yarnRotation, yarnTranslationX, stringTranslationX);
        animatorSet.start();
    }
}