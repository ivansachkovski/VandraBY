package com.example.vandraby.model;

import android.util.Log;

import java.util.ArrayList;

public class DataModel extends FirebaseDatabaseHelper implements Contract.Model {

    private final static String LOGGER_TAG = "LOG_VANDRA_DATAMODEL";

    private static DataModel mInstance;

    private final User mUser;

    private final ArrayList<Place> mAllPlaces;

    private DataModel() {
        mUser = new User();
        mAllPlaces = new ArrayList<>();
    }

    public static DataModel getInstance() {
        if (mInstance == null) {
            mInstance = new DataModel();
        }
        return mInstance;
    }

    @Override
    public void initialize() {
        checkIfUserExistsOrCreate();

        addUpdateUserListener();
        addUpdatePlacesListener();
    }

    @Override
    public boolean isReady() {
        boolean result;

        synchronized (mUser) {
            result = mUser.getId() > 0;
        }

        synchronized (mAllPlaces) {
            result = result && mAllPlaces.size() > 0;
        }

        return result;
    }

    @Override
    protected void setUser(User user) {
        if (user != null) {
            synchronized (mUser) {
                mUser.copyFrom(user);
            }
        }
    }

    @Override
    protected void setPlaces(ArrayList<Place> places) {
        synchronized (mAllPlaces) {
            mAllPlaces.clear();
            mAllPlaces.addAll(places);
        }
    }

    public ArrayList<Place> getUnratedPlaces() {
        Log.i(LOGGER_TAG, "call getUnratedPlaces");

        ArrayList<Place> result = new ArrayList<>();

        // TODO::possible deadlock - refactor
        synchronized (mUser) {
            synchronized (mAllPlaces) {
                for (Place place : mAllPlaces) {
                    if (!mUser.isLiked(place.getId()) && !mUser.isDisliked(place.getId())) {
                        result.add(place);
                    }
                }
            }
        }
        return result;
    }

    public User getCurrentUser() {
        User user;
        synchronized (mUser) {
            user = mUser;
        }

        return user;
    }

    public void likeObject(long id) {
        mUser.addLikedObject(id);

        updateLikedPlace(mUser.getLikedPlaces());
    }

    public void dislikeObject(long id) {
        mUser.addDislikedObject(id);

        updateDislikedPlace(mUser.getDislikedPlaces());
    }

    public void setUserName(String newName) {
        mUser.setName(newName);

        updateName(mUser.getName());
    }

    public ArrayList<Place> getUserLikedPlaces() {
        ArrayList<Place> result = new ArrayList<>();
        for (Place place : mAllPlaces) {
            if (mUser.isLiked(place.getId())) {
                result.add(place);
            }
        }
        return result;
    }

    public void reset() {
        mUser.resetObjects();

        updateLikedPlace(mUser.getLikedPlaces());
        updateDislikedPlace(mUser.getDislikedPlaces());
    }

}
