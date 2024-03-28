//package com.example.petconnect.services.comment;
//
//import com.example.petconnect.models.ExtendedComment;
//
//import java.util.Date;
//
//public class AddCommentResponse {
//
//    private String content;
//    private int post_id, Id;
//    private ExtendedComment comment;
//    public String getContent() {
//        return this.content;
//    }
//    public int getPost_id() {
//        return this.post_id;
//    }
//    public ExtendedComment getComment() {
//        return this.comment;
//    }
//
//
//}
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

    // Thêm các thuộc tính và phương thức getter và setter cho các thuộc tính mới
}