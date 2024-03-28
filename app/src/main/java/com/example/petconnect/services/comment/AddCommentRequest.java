package com.example.petconnect.services.auth;

public class CommentRequest {
    private String content;
    private int post_id;

    public CommentRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }

}
