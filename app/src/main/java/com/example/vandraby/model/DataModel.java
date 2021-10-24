package com.example.vandraby.model;

import android.util.Log;

import java.util.ArrayList;

public class DataModel {
    private final static String LOGGER_TAG = "LOG_VANDRA_DATA_MODEL";
    private static DataModel instance;
    private User user;
    private ArrayList<Place> places;

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

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public ArrayList<Place> getAllObjects() {
        return places;
    }

    public ArrayList<Place> getNotSwipedPlaces() {
        Log.i(LOGGER_TAG, "getObjectsForSwipes()");
        ArrayList<Place> notSwipedPlaces = new ArrayList<>();
        for (Place place : places) {
            if (!user.isLiked(place.getId()) && !user.isDisliked(place.getId())) {
                notSwipedPlaces.add(place);
            }
        }
        return notSwipedPlaces;
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

    public ArrayList<Place> getUserLikedPlaces() {
        ArrayList<Place> likedPlaces = new ArrayList<>();
        for (Place place : places) {
            if (user.isLiked(place.getId())) {
                likedPlaces.add(place);
                //likedPlaces.add(place);
                //likedPlaces.add(place);
            }
        }
        return likedPlaces;
    }

    public void reset() {
        user.resetObjects();
    }
}
