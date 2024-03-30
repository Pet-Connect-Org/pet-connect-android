package com.example.petconnect.models;

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

    public List<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follow> followers) {
        this.followers = followers;
    }

    public List<Follow> getFollowing() {
        return following;
    }

    public void setFollowing(List<Follow> following) {
        this.following = following;
    }

    List<Follow> followers;
    List<Follow> following;
}
