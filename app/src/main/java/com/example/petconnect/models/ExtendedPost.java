package com.example.petconnect.models;

import com.example.petconnect.R;

import java.util.ArrayList;

public class ExtendedPost extends Post {
   private boolean selfLike;

    public boolean isSelfLike() {
        return selfLike;
    }

    public void setSelfLike(boolean selfLike) {
        this.selfLike = selfLike;
    }

    public User getUser() {
        return user;
    }

    private ArrayList<ExtendedComment> comments;
    private User user;
    private ArrayList<LikePost> likes;

    public ArrayList<LikePost> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<LikePost> likes) {
        this.likes = likes;
    }
//    private  int likes;
//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }


    public void setUser(User user) {
        this.user = user;
    }


    public ArrayList<ExtendedComment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<ExtendedComment> comments) {
        this.comments = comments;
    }



}
