package com.example.petconnect.models;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ExtendedUser extends User {
    public List<ExtendedPost> getPosts() {
        return posts;
    }

    public void setPosts(List<ExtendedPost> posts) {
        this.posts = posts;
    }

    List<ExtendedPost> posts;

    public ArrayList<Follow> getFollowers() {
        return followers;
    }
    public void setFollowers(ArrayList<Follow> followers) {
        this.followers = followers;
    }

    public ArrayList<Follow> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Follow> following) {
        this.following = following;
    }

    ArrayList<Follow> followers;
    ArrayList<Follow> following;
}
