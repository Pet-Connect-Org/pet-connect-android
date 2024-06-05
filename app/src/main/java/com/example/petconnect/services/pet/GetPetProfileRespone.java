package com.example.petconnect.services.pet;

import com.example.petconnect.models.ExtendedPet;

public class GetPetProfileRespone {
    String message;

    public ExtendedPet getData() {
        return data;
    }

    public void setData(ExtendedPet data) {
        this.data = data;
    }

    ExtendedPet data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
