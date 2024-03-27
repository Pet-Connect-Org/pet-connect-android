package com.example.petconnect.models;

import java.util.ArrayList;

public class ExtendedComment extends Comment {
    private User user;
    private ArrayList<LikeComment> likes;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<LikeComment> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<LikeComment> likes) {
        this.likes = likes;
    }
}
