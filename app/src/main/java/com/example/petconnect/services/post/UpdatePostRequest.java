package com.example.petconnect.services.post;

public class UpdatePostRequest {
    private int post_id;
    private String content;
    public UpdatePostRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }
}
