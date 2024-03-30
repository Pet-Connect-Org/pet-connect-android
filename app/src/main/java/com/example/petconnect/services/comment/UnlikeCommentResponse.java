package com.example.petconnect.services.comment;

import com.example.petconnect.models.LikeComment;

public class UnlikeCommentResponse {
    private String message;
    private LikeComment data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LikeComment getData() {
        return data;
    }

    public void setData(LikeComment data) {
        this.data = data;
    }
}