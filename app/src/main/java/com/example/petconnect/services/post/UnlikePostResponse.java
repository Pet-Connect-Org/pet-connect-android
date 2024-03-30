package com.example.petconnect.services.post;

import com.example.petconnect.models.LikePost;

public class UnlikePostResponse {
    String message;
    LikePost data;

    public UnlikePostResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LikePost getData() {
        return data;
    }

    public void setData(LikePost data) {
        this.data = data;
    }
}
