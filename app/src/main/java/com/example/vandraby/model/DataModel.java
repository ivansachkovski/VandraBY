package com.example.vandraby.model;

import android.util.Log;

import java.util.ArrayList;

public class DataModel {

    private final static String LOGGER_TAG = "LOG_VANDRA_DATA_MODEL";

    private static DataModel mInstance;
    private ArrayList<Place> mAllPlaces;
    private User mUser;

    private DataModel() {
        mAllPlaces = new ArrayList<>();
    }

    public static DataModel getInstance() {
        if (mInstance == null) {
            mInstance = new DataModel();
        }
        return mInstance;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public void setPlaces(ArrayList<Place> places) {
        mAllPlaces = places;
    }

    public ArrayList<Place> getAllPlaces() {
        return mAllPlaces;
    }

    public ArrayList<Place> getNotSwipedPlaces() {
        Log.i(LOGGER_TAG, "getObjectsForSwipes()");
        ArrayList<Place> result = new ArrayList<>();
        for (Place place : mAllPlaces) {
            if (!mUser.isLiked(place.getId()) && !mUser.isDisliked(place.getId())) {
                result.add(place);
            }
        }
        return result;
    }

    public User getCurrentUser() {
        return mUser;
    }

    public void likeObject(long id) {
        mUser.addLikedObject(id);
    }

    public void dislikeObject(long id) {
        mUser.addDislikedObject(id);
    }

    public ArrayList<Place> getUserLikedPlaces() {
        ArrayList<Place> result = new ArrayList<>();
        for (Place place : mAllPlaces) {
            if (mUser.isLiked(place.getId())) {
                result.add(place);
                // result.add(place);
                // result.add(place);
            }
        }
        return result;
    }

    public void reset() {
        mUser.resetObjects();
    }
}
