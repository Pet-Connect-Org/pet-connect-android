package com.example.petconnect.services.comment;

public class AddCommentRequest {
    private String content;
    private int post_id;
    public AddCommentRequest(String content, int post_id) {
        this.content = content;
        this.post_id = post_id;
    }

}
