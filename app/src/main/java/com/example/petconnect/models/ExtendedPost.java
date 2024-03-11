package com.example.petconnect.models;

public class ExtendedPost extends Post{
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
}
