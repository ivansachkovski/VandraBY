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

abstract class FirebaseDatabaseHelper {

    private final static String LOGGER_TAG = "LOG_VANDRA_FBDBHelper";

    private final DatabaseReference mDatabaseCurrentUserChild;

    private final DatabaseReference mDatabasePlaces;

    protected FirebaseDatabaseHelper() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        mDatabaseCurrentUserChild = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        mDatabasePlaces = FirebaseDatabase.getInstance().getReference("places");
    }

    protected void addUpdateUserListener() {
        Log.i(LOGGER_TAG, "call addUpdateUserListener");

        mDatabaseCurrentUserChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(LOGGER_TAG, "User was updated");

                User user = snapshot.getValue(User.class);
                setUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(LOGGER_TAG, "User update failed: " + error.toString());
            }
        });
    }

    protected void addUpdatePlacesListener() {
        Log.i(LOGGER_TAG, "call addUpdatePlacesListener");

        mDatabasePlaces.addValueEventListener(new ValueEventListener() {
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

    protected void updateLikedPlace(ArrayList<Long> placesIds) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("likedPlaces", placesIds);

        mDatabaseCurrentUserChild.updateChildren(childUpdates);
    }

    protected void updateDislikedPlace(ArrayList<Long> placesIds) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("dislikedPlaces", placesIds);

        mDatabaseCurrentUserChild.updateChildren(childUpdates);
    }

    abstract protected void setUser(User user);

    abstract protected void setPlaces(ArrayList<Place> places);
}
