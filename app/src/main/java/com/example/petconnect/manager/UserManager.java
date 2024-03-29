package com.example.petconnect.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.models.User;
import com.google.gson.Gson;

public class UserManager {
    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    private static final String USER_KEY = "USER_KEY";
    private static final String PREF_ACCESS_TOKEN_NAME = "access_token_prefs";
    private static final String PERF_USER_NAME = "user_prefs";
    private SharedPreferences sharedPreferencesAccessToken;
    private SharedPreferences sharedPreferencesUser;

    public UserManager(Context context) {
        sharedPreferencesAccessToken = context.getSharedPreferences(PREF_ACCESS_TOKEN_NAME, Context.MODE_PRIVATE);
        sharedPreferencesUser = context.getSharedPreferences(PERF_USER_NAME, Context.MODE_PRIVATE);
    }

    public ExtendedAccount getUser() {
        Gson gson = new Gson();
        String json = sharedPreferencesUser.getString(USER_KEY, null);
        ExtendedAccount user = gson.fromJson(json, ExtendedAccount.class);
        return user;
    }

    public void saveUser(ExtendedAccount user) {
        SharedPreferences.Editor editor = sharedPreferencesUser.edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        editor.putString(USER_KEY, userJson);
        editor.apply();
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferencesAccessToken.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    public String getAccessToken() {
        return sharedPreferencesAccessToken.getString(ACCESS_TOKEN_KEY, null);
    }

    public void clearAccessToken() {
        SharedPreferences.Editor editor = sharedPreferencesAccessToken.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.apply();
    }

}
