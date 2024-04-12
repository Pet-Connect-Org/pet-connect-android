package com.example.petconnect.services.post;

import com.example.petconnect.models.ExtendedPost;
import com.example.petconnect.models.Post;

public class UpdatePostResponse {
    String message;
    ExtendedPost data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getData() {
        return data;
    }

    public void setData(ExtendedPost data) {
        this.data = data;
    }

}
