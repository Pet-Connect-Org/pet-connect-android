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
}
