package com.example.petconnect.models;

import java.util.Date;

public class Follow {
    int id, following_user_id, user_id;
    private Date created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollowing_user_id() {
        return following_user_id;
    }

    public void setFollowing_user_id(int following_user_id) {
        this.following_user_id = following_user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    private Date updated_at;

}
