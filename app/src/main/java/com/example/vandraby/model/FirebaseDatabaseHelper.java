package com.example.vandraby.model;

import android.provider.ContactsContract;
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

    private final DatabaseReference mDatabaseUsers;
    private final DatabaseReference mDatabasePlaces;

    private DatabaseReference mDatabaseCurrentUserChild;

    protected FirebaseDatabaseHelper() {
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("users");
        mDatabasePlaces = FirebaseDatabase.getInstance().getReference("places");
    }

    protected void checkIfUserExistsOrCreate() {
        Log.i(LOGGER_TAG, "call checkIfUserExistsOrCreate");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        String uid = firebaseUser.getUid();

        mDatabaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists()) {
                    // user exists
                } else {
                    // user does not exist
                    mDatabaseUsers.child(uid).setValue(new User(firebaseUser).toMap());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.i(LOGGER_TAG, mDatabaseUsers.child(uid).getKey());
    }

    protected void addUpdateUserListener() {
        Log.i(LOGGER_TAG, "call addUpdateUserListener");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;

        mDatabaseCurrentUserChild = mDatabaseUsers.child(firebaseUser.getUid());

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

    protected void updateName(String newName) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", newName);

        mDatabaseCurrentUserChild.updateChildren(childUpdates);
    }

    abstract protected void setUser(User user);

    abstract protected void setPlaces(ArrayList<Place> places);
}
