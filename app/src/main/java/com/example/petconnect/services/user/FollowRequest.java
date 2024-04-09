package com.example.petconnect.services.user;

public class FollowRequest {
    private String content;
    private int user_id;

    public FollowRequest(String content, int user_id) {
        this.content = content;
        this.user_id = user_id;
    }
}
