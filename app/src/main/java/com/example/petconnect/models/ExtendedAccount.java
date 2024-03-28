package com.example.petconnect.models;

import java.util.Date;

public class ExtendedAccount extends User {
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    private String email;
    private int role;
    private Boolean isActive;
}
