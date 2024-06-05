package com.example.petconnect.models;

import java.util.List;

public class ExtendedPet extends Pet{
    PetType pet_type;
    private Med med;
    private List<Deworm> worms;
    private List<Vaccination> vaccinations;

    public Med getMed() {
        return med;
    }

    public void setMed(Med med) {
        this.med = med;
    }

    public List<Deworm> getWorms() {
        return worms;
    }

    public void setWorms(List<Deworm> worms) {
        this.worms = worms;
    }

    public List<Vaccination> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<Vaccination> vaccinations) {
        this.vaccinations = vaccinations;
    }
    public PetType getPet_type() {
        return pet_type;
    }

    public void setPet_type(PetType pet_type) {
        this.pet_type = pet_type;
    }


}


