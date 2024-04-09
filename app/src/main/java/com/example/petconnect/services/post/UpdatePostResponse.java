package com.example.petconnect.services.post;

import com.example.petconnect.models.Post;

public class UpdatePostResponse {
    String message;
    Post data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getData() {
        return data;
    }

    public void setData(Post data) {
        this.data = data;
    }

    public UpdatePostResponse() {
    }

    public UpdatePostResponse(String message, Post data) {
        this.message = message;
        this.data = data;
    }
}
