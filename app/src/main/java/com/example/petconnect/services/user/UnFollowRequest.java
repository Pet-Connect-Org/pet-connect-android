package com.example.petconnect.services.user;

public class UnFollowRequest {
    private String content;
    private int user_id;

    public UnFollowRequest(String content, int user_id) {
        this.content = content;
        this.user_id = user_id;
    }
}
