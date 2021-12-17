package com.example.vandraby.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private final long id;

    private final String nickname;
    private String firstName;
    private String lastName;

    private final String photoUrl;

    private final ArrayList<Long> likedPlaces;
    private final ArrayList<Long> dislikedPlaces;

    public User() {
        id = 0;
        nickname = ""; // todo::
        photoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/1200px-User_icon-cp.svg.png";

        likedPlaces = new ArrayList<>();
        dislikedPlaces = new ArrayList<>();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);

        result.put("nickname", nickname);
        result.put("firstName", firstName);
        result.put("lastName", lastName);

        result.put("photoUrl", photoUrl);

        result.put("likedPlaces", likedPlaces);
        result.put("dislikedPlaces", dislikedPlaces);

        return result;
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
