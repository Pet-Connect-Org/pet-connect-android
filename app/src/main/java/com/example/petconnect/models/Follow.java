package com.example.petconnect.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

public class Follow implements Parcelable {
    int id, following_user_id, user_id;
    private Date created_at;


    public Follow(int id, int following_user_id, int user_id, Date created_at, Date updated_at) {
        this.id = id;
        this.following_user_id = following_user_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected Follow(Parcel in) {
        id = in.readInt();
        following_user_id = in.readInt();
        user_id = in.readInt();
    }

    public static final Creator<Follow> CREATOR = new Creator<Follow>() {
        @Override
        public Follow createFromParcel(Parcel in) {
            return new Follow(in);
        }

        @Override
        public Follow[] newArray(int size) {
            return new Follow[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(following_user_id);
        dest.writeInt(user_id);
    }
}
