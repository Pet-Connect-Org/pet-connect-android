package com.example.petconnect.models;



import android.util.Log;

import java.util.List;

public class Med {

        private int id;
        private int pet_id;
        private String favoriteFood;
        private boolean isFriendlyWithDog;
        private boolean isFriendlyWithCat;
        private boolean isCleanProperly;
        private boolean isHyperactive;
        private boolean isFriendlyWithKid;
        private boolean isShy;
        private List<Allergy> allergies;
        private List<Weight> weights;
        private List<Deworm> deworms;
        private List<Vaccination> vaccinations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }
    public boolean isFriendlyWithDog() {
        Log.d("Med", "isFriendlyWithDog: " + isFriendlyWithDog);
        return isFriendlyWithDog;
    }

    public boolean isFriendlyWithCat() {
        Log.d("Med", "isFriendlyWithCat: " + isFriendlyWithCat);
        return isFriendlyWithCat;
    }

    public boolean isCleanProperly() {
        Log.d("Med", "isCleanProperly: " + isCleanProperly);
        return isCleanProperly;
    }

    public boolean isHyperactive() {
        Log.d("Med", "isHyperactive: " + isHyperactive);
        return isHyperactive;
    }

    public boolean isFriendlyWithKid() {
        Log.d("Med", "isFriendlyWithKid: " + isFriendlyWithKid);
        return isFriendlyWithKid;
    }

    public boolean isShy() {
        Log.d("Med", "isShy: " + isShy);
        return isShy;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

//    public boolean isFriendlyWithDog() {
//        return isFriendlyWithDog;
//    }

    public void setFriendlyWithDog(boolean friendlyWithDog) {
        isFriendlyWithDog = friendlyWithDog;
    }

//    public boolean isFriendlyWithCat() {
//        return isFriendlyWithCat;
//    }

    public void setFriendlyWithCat(boolean friendlyWithCat) {
        isFriendlyWithCat = friendlyWithCat;
    }

//    public boolean isCleanProperly() {
//        return isCleanProperly;
//    }

    public void setCleanProperly(boolean cleanProperly) {
        isCleanProperly = cleanProperly;
    }

//    public boolean isHyperactive() {
//        return isHyperactive;
//    }

    public void setHyperactive(boolean hyperactive) {
        isHyperactive = hyperactive;
    }

//    public boolean isFriendlyWithKid() {
//        return isFriendlyWithKid;
//    }

    public void setFriendlyWithKid(boolean friendlyWithKid) {
        isFriendlyWithKid = friendlyWithKid;
    }

//    public boolean isShy() {
//        return isShy;
//    }

    public void setShy(boolean shy) {
        isShy = shy;
    }

    public List<Allergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Allergy> allergies) {
        this.allergies = allergies;
    }

    public List<Weight> getWeights() {
        return weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }

    public List<Deworm> getDeworms() {
        return deworms;
    }

    public void setDeworms(List<Deworm> deworms) {
        this.deworms = deworms;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }
}
