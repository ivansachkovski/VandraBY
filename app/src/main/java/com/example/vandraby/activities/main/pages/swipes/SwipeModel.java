package com.example.vandraby.activities.main.pages.swipes;

import com.example.vandraby.model.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SwipeModel {
    private static final String LOGGER_TAG = "VANDRA_DEV_SWIPE_MODEL";
    Queue<Place> objects = new LinkedList<>();

    public SwipeModel(ArrayList<Place> objects) {
        this.objects.addAll(objects);
    }

    public Place getCurrentObject() {
        return objects.peek();
    }

    public void swipe() {
        objects.poll();
    }
}
