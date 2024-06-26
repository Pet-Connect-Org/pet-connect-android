package com.example.petconnect.models;

import android.os.Parcel;

import java.util.Date;

public class ExtendedFollow extends Follow{
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    User user; // người theo dõi

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }

    User following; // người đang theo dõi

    public ExtendedFollow(int id, int following_user_id, int user_id, Date created_at, Date updated_at) {
        super(id, following_user_id, user_id, created_at, updated_at);
    }

    protected ExtendedFollow(Parcel in) {
        super(in);
    }
}
