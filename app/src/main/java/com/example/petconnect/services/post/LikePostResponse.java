package com.example.petconnect.services.post;

import com.example.petconnect.models.LikePost;

public class LikePostResponse {
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

    String message;

    LikePost data;

    public LikePostResponse(String message) {
        this.message = message;
    }

}
