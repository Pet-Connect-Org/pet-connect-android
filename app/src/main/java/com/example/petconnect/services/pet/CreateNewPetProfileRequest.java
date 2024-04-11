package com.example.petconnect.services.pet;

public class CreateNewPetProfileRequest {
    String name, birthday, sex, description, image, favoriteFood;
    Integer pet_type_id;

    public CreateNewPetProfileRequest(String name, String birthday, String sex, String description, String image, String favoriteFood, Integer pet_type_id, Boolean isFriendlyWithDog, Boolean isFriendlyWithCat, Boolean isFriendlyWithKid, Boolean isCleanProperly, Boolean isHyperactive, Boolean isShy) {
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.description = description;
        this.image = image;
        this.favoriteFood = favoriteFood;
        this.pet_type_id = pet_type_id;
        this.isFriendlyWithDog = isFriendlyWithDog;
        this.isFriendlyWithCat = isFriendlyWithCat;
        this.isFriendlyWithKid = isFriendlyWithKid;
        this.isCleanProperly = isCleanProperly;
        this.isHyperactive = isHyperactive;
        this.isShy = isShy;
    }

    Boolean isFriendlyWithDog, isFriendlyWithCat, isFriendlyWithKid, isCleanProperly, isHyperactive, isShy;
}
