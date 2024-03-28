package com.example.petconnect.services.auth;

import com.example.petconnect.models.ExtendedComment;

public class CommentResponse {
    private String content;
    private int post_id;
    private ExtendedComment comment;
    public String getContent() {
        return this.content;
    }
    public int getPost_id() {
        return this.post_id;
    }
    public ExtendedComment getComment() {
        return this.comment;
    }
}
