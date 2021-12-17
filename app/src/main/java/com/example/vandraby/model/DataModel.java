package com.example.vandraby.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataModel implements Contract.Model {

    private final static String LOGGER_TAG = "LOG_VANDRA_DATA_MODEL";

    private static DataModel mInstance;

    private final Object mUserLock = new Object(); // uses for synchronization
    private User mUser;

    private final Object mAllPlacesLock = new Object(); // uses for synchronization
    private ArrayList<Place> mAllPlaces;

    private DataModel() {
    }

    public static DataModel getInstance() {
        if (mInstance == null) {
            mInstance = new DataModel();
        }
        return mInstance;
    }

    @Override
    public void initialize() {
        addUpdateUserListener();
        addUpdatePlacesListener();
    }

    @Override
    public boolean isReady() {
        boolean result = true;

        synchronized (mUserLock) {
            result = mUser != null;
        }

        synchronized (mAllPlacesLock) {
            result = result && mAllPlaces != null;
        }

        return result;
    }

    private void addUpdateUserListener() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                setUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addUpdatePlacesListener() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("places");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Place> places = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Place place = ds.getValue(Place.class);
                    places.add(place);
                }
                setPlaces(places);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUser(User user) {
        synchronized (mUserLock) {
            mUser = user;
        }
    }

    public void setPlaces(ArrayList<Place> places) {
        synchronized (mAllPlacesLock) {
            mAllPlaces = places;
        }
    }

    public ArrayList<Place> getUnratedPlaces() {
        Log.i(LOGGER_TAG, "call getUnratedPlaces");

        ArrayList<Place> result = new ArrayList<>();

        // TODO::possible deadlock - refactor
        synchronized (mUserLock) {
            synchronized (mAllPlacesLock) {
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
        synchronized (mUserLock) {
            user = mUser;
        }

        return user;
    }

    public void likeObject(long id) {
        mUser.addLikedObject(id);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("likedPlaces", mUser.getLikedPlaces());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid).updateChildren(childUpdates);
    }

    public void dislikeObject(long id) {
        mUser.addDislikedObject(id);
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

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("likedPlaces", mUser.getLikedPlaces());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid).updateChildren(childUpdates);
    }

}
