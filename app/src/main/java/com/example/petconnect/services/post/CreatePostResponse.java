package com.example.petconnect.services.post;

import com.example.petconnect.models.Post;

public class CreatePostResponse {
    String message;
    Post data;

    public CreatePostResponse(String message, Post data) {
        this.message = message;
        this.data = data;
    }

    public CreatePostResponse() {
    }

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
}
