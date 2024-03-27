package com.example.petconnect.services.post;

public class CreatePostRequest {

        private String content;
        private int latitude;
        private int longitude;



    public CreatePostRequest(String content) {
        this.content = content;

    }
}
