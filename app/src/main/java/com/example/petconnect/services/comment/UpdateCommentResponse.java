
package com.example.petconnect.services.comment;

import com.example.petconnect.models.ExtendedComment;


public class UpdateCommentResponse {
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

    // Thêm các thuộc tính và phương thức getter và setter cho các thuộc tính mới
}