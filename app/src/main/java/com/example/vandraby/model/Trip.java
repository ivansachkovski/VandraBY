package com.example.vandraby.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Trip {

    private long id = 0;

    private ArrayList<Long> placesIds;

    private float startX;
    private float startY;

    private float finishX;
    private float finishY;

    public Trip() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("placesIds", placesIds);

        result.put("startX", startX);
        result.put("startY", startY);

        result.put("finishX", finishX);
        result.put("finishY", finishY);

        return result;
    }

    public void addPlace(long placeId) {
        placesIds.add(placeId);
    }

    public void removePlace(long placeId) {

    }
}
