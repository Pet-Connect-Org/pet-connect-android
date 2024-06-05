package com.example.petconnect.services.pet;

public class GetPetProfileRequest {
    private int id;

    public GetPetProfileRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
