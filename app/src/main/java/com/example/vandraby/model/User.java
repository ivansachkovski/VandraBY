package com.example.vandraby.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private long id;

    private String nickname;
    private String name;

    private String photoUrl;

    private final ArrayList<Long> likedPlaces;
    private final ArrayList<Long> dislikedPlaces;
    private final ArrayList<Long> visitedPlaces;

    public User() {
        id = 0;
        nickname = "UNDEFINED"; // todo::
        photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/1200px-User_icon-cp.svg.png";

        likedPlaces = new ArrayList<>();
        dislikedPlaces = new ArrayList<>();
        visitedPlaces = new ArrayList<>();
    }

    public User(FirebaseUser firebaseUser) {
        id = 2;

        name = firebaseUser.getDisplayName();

        nickname = firebaseUser.getEmail();

        likedPlaces = new ArrayList<>();
        dislikedPlaces = new ArrayList<>();
        visitedPlaces = new ArrayList<>();
    }

    HashMap<String, Object> toMap() {
        HashMap<String, Object> result  = new HashMap<>();

        result.put("id", id);

        result.put("nickname", nickname);
        result.put("name", name);

        result.put("photoUrl", photoUrl);

        result.put("likedPlaces", likedPlaces);
        result.put("dislikedPlaces", dislikedPlaces);

        return result;
    }

    public void copyFrom(User src) {
        id = src.id;

        nickname = src.nickname;
        name = src.name;

        photoUrl = src.photoUrl;

        likedPlaces.clear();
        likedPlaces.addAll(src.likedPlaces);

        dislikedPlaces.clear();
        dislikedPlaces.addAll(src.dislikedPlaces);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
