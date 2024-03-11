package com.example.petconnect.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class AccessTokenManager {
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String PREF_NAME = "AccessTokenPrefs";

    private SharedPreferences sharedPreferences;

    public AccessTokenManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null);
    }

    public void clearAccessToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.apply();
    }
}