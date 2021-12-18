package com.example.vandraby.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private long id;

    private String nickname;
    private String firstName;
    private String lastName;

    private String photoUrl;

    private final ArrayList<Long> likedPlaces;
    private final ArrayList<Long> dislikedPlaces;

    public User() {
        id = 0;
        nickname = "UNDEFINED"; // todo::
        photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/1200px-User_icon-cp.svg.png";

        likedPlaces = new ArrayList<>();
        dislikedPlaces = new ArrayList<>();
    }

    public User(FirebaseUser firebaseUser) {
        id = 2;

        firstName = firebaseUser.getDisplayName();
        lastName = firebaseUser.getDisplayName();

        nickname = firebaseUser.getEmail();

        likedPlaces = new ArrayList<>();
        dislikedPlaces = new ArrayList<>();
    }

    HashMap<String, Object> toMap() {
        HashMap<String, Object> result  = new HashMap<>();

        result.put("id", id);

        result.put("nickname", nickname);
        result.put("firstName", firstName);
        result.put("lastName", lastName);

        result.put("photoUrl", photoUrl);

        result.put("likedPlaces", likedPlaces);
        result.put("dislikedPlaces", dislikedPlaces);

        return result;
    }

    public void copyFrom(User src) {
        id = src.id;

        nickname = src.nickname;
        firstName = src.firstName;
        lastName = src.lastName;

        photoUrl = src.photoUrl;

        likedPlaces.clear();
        likedPlaces.addAll(src.likedPlaces);

        dislikedPlaces.clear();
        dislikedPlaces.addAll(src.dislikedPlaces);
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArrayList<Long> getLikedPlaces() {
        return likedPlaces;
    }

    public ArrayList<Long> getDislikedPlaces() {
        return dislikedPlaces;
    }

    public void addLikedObject(long id) {
        likedPlaces.add(id);
    }

    public void addDislikedObject(long id) {
        dislikedPlaces.add(id);
    }

    public boolean isLiked(long objectId) {
        for (long id : likedPlaces) {
            if (id == objectId) {
                return true;
            }
        }
        return false;
    }

    public boolean isDisliked(long objectId) {
        for (long id : dislikedPlaces) {
            if (id == objectId) {
                return true;
            }
        }
        return false;
    }

    public void resetObjects() {
        likedPlaces.clear();
        dislikedPlaces.clear();
    }
}
