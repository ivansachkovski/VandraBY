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
import java.util.Map;

public class DataModel implements Contract.Model {

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

    public boolean isReady() {
        return mUser != null && mAllPlaces != null;
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

    @Override
    public void loadDataFromDatabase() {
        DatabaseReference databasePlacesReference = FirebaseDatabase.getInstance().getReference("places");
        databasePlacesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Place> places = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> value = (Map) ds.getValue();
                    Place place = new Place(value);
                    places.add(place);
                }
                setPlaces(places);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseUsersReference = FirebaseDatabase.getInstance().getReference("users");
        databaseUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Map<String, Object> value = (Map) ds.getValue();
                    if (value != null) {
                        if (value.get("uid").toString().equals(uid)) {
                            setUser(new User(value));
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
