package com.example.petconnect.models;

public class ExtendedAccount extends Account {

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

}
