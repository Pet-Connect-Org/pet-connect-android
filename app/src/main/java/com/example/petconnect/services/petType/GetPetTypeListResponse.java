package com.example.petconnect.services.petType;

import com.example.petconnect.models.PetType;

import java.util.ArrayList;

public class GetPetTypeListResponse {
    public ArrayList<PetType> getData() {
        return data;
    }

    public void setData(ArrayList<PetType> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ArrayList<PetType> data;
    String message;
}
