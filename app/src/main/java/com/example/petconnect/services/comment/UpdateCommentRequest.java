package com.example.petconnect.services.comment;

public class UpdateCommentRequest {
    private int post_id;
    private String content;
    public UpdateCommentRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }
}
