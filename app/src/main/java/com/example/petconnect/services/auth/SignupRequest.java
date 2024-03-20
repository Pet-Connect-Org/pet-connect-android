package com.example.petconnect.services.auth;

public class SignupRequest {
    String email;
    String password;

    public SignupRequest(String email, String password, String name, String confirmPassword, String sex, String birthday, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.confirmPassword = confirmPassword;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String name;
    String confirmPassword;
    String sex;
    String birthday;
    String address;


}
