package com.example.vandraby.model;

import java.util.Map;
import java.util.Vector;

public class User {
    private final long mId;

    private final String mNickname;
    private String mFirstName;
    private String mLastName;

    private final String mPhotoUrl;

    private final Vector<Long> mLikedSights;
    private final Vector<Long> mDislikedSights;

    public User(Map<String, Object> value) {
        mId = Long.parseLong(value.get("id").toString());

        mNickname = value.get("nickname").toString();
        mFirstName = value.get("firstName").toString();
        mLastName = value.get("lastName").toString();

        mPhotoUrl = value.get("photoUrl").toString();

        mLikedSights = new Vector<>();
        mDislikedSights = new Vector<>();
    }

    public long getId() {
        return mId;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getNickname() {
        return mNickname;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void addLikedObject(long id) {
        mLikedSights.add(id);
    }

    public void addDislikedObject(long id) {
        mDislikedSights.add(id);
    }

    public boolean isLiked(long objectId) {
        for (long id : mLikedSights) {
            if (id == objectId) {
                return true;
            }
        }
        return false;
    }

    public boolean isDisliked(long objectId) {
        for (long id : mDislikedSights) {
            if (id == objectId) {
                return true;
            }
        }
        return false;
    }

    public void resetObjects() {
        mLikedSights.clear();
        mDislikedSights.clear();
    }
}
