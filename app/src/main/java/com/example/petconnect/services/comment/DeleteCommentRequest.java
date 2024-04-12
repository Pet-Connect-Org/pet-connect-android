package com.example.petconnect.services.comment;

public class DeleteCommentRequest {
    private String content;
    private int post_id;
    // construct

    public DeleteCommentRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }
}
