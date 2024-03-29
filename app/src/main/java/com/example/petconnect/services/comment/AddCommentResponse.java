
package com.example.petconnect.services.comment;

import com.example.petconnect.models.ExtendedComment;

import java.util.Date;

public class AddCommentResponse {
    private String message;
    private ExtendedComment data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExtendedComment getData() {
        return data;
    }

    public void setData(ExtendedComment data) {
        this.data = data;
    }

}