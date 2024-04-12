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

    public ArrayList<ExtendedFollow> getFollowers() {
        return followers;
    }
    public void setFollowers(ArrayList<ExtendedFollow> followers) {
        this.followers = followers;
    }

    public ArrayList<ExtendedFollow> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<ExtendedFollow> following) {
        this.following = following;
    }

    ArrayList<ExtendedFollow> followers;
    ArrayList<ExtendedFollow> following;

    ArrayList<ExtendedPet> pets;

    public ArrayList<ExtendedPet> getPets() {
        return pets;
    }

    public void setPets(ArrayList<ExtendedPet> pets) {
        this.pets = pets;
    }
}
