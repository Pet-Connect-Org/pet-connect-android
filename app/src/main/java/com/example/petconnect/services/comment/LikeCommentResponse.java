package com.example.petconnect.services.comment;

import com.example.petconnect.models.ExtendedComment;
import com.example.petconnect.models.LikeComment;
import com.example.petconnect.models.LikePost;

public class LikeCommentResponse {
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