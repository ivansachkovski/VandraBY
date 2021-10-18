package com.example.vandraby.information;

import android.util.Log;

import java.util.ArrayList;

public class DataModel {
    private final static String LOGGER_TAG = "LOG_VANDRA_DATA_MODEL";
    private static DataModel instance = null;
    private User user = null;
    private ArrayList<Place> objects = null;
    public static Place place;

    private DataModel() {
    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setObjects(ArrayList<Place> objects) {
        this.objects = objects;
    }

    public ArrayList<Place> getAllObjects() {
        return objects;
    }

    public ArrayList<Place> getObjectsForSwipes() {
        Log.i(LOGGER_TAG, "getObjectsForSwipes()");
        ArrayList<Place> objectsForSwipes = new ArrayList<>();
        for (Place object : objects) {
            if (!user.isLiked(object.getId()) && !user.isDisliked(object.getId())) {
                objectsForSwipes.add(object);
            }
        }
        return objectsForSwipes;
    }

    public User getCurrentUser() {
        return user;
    }

    public void likeObject(int id) {
        user.addLikedObject(id);
    }

    public void dislikeObject(int id) {
        user.addDislikedObject(id);
    }

    public ArrayList<Place> getUserLikedObjects() {
        ArrayList<Place> likedObjects = new ArrayList<>();
        for (Place object : objects) {
            if (user.isLiked(object.getId())) {
                likedObjects.add(object);
            }
        }
        return likedObjects;
    }

    public void reset() {
        user.resetObjects();
    }
}
