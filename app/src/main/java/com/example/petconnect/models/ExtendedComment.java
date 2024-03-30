package com.example.petconnect.models;

import java.util.ArrayList;
import java.util.Date;

public class ExtendedComment extends Comment {
    private User user;
    private ArrayList<LikeComment> likes;


    public ExtendedComment() {
        super();

    }

    public ExtendedComment(int id, int post_id, int user_id, String content, Date created_at, Date updated_at, User user, ArrayList<LikeComment> likes) {
        super(id, post_id, user_id, content, created_at, updated_at);
        this.user = user;
        this.likes = likes;
    }



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
