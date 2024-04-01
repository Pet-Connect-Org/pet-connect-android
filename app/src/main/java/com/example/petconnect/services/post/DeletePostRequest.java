package com.example.petconnect.services.post;

public class DeletePostRequest {
    private String content;
    private int post_id;

    // constructer

    public DeletePostRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }
}
