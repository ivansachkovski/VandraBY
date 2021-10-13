package com.example.vandraby.information;

import android.widget.SimpleAdapter;

import java.util.ArrayList;

public class DataModel {
    private static DataModel instance = null;
    private User user = null;
    private ArrayList<Sight> objects = null;
    public static Sight sight;

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

    public void setObjects(ArrayList<Sight> objects) {
        this.objects = objects;
    }

    public ArrayList<Sight> getAllObjects() {
        return objects;
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

    public ArrayList<Sight> getUserLikedObjects() {
        ArrayList<Sight> likedObjects = new ArrayList<>();
        for (Sight object: objects) {
            if (user.doLike(object.getId())) {
                likedObjects.add(object);
            }
        }
        return likedObjects;
    }
}
