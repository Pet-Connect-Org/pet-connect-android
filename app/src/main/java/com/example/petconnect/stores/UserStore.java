package com.example.petconnect.stores;

import com.example.petconnect.models.ExtendedAccount;

public class UserStore {
    private static UserStore instance;
    private String token = "";
    private ExtendedAccount account;

    // Private constructor to prevent instantiation from outside the class
    private UserStore() {}

    // Public method to get the singleton instance of UserStore
    public static synchronized UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public void setAccessToken(String newToken) {
        this.token = newToken;
    }

    public String getAccessToken() {
        return this.token;
    }

    public void setAccountInformation(ExtendedAccount account) {
        this.account = account;
    }

    public ExtendedAccount getAccountInformation() {
        return this.account;
    }
}
