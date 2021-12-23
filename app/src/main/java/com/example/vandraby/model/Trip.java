package com.example.vandraby.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Trip {

    private long id = 0;

    private ArrayList<Long> placesIds;

    public Trip() {

    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", id);
        result.put("placesIds", placesIds);

        return result;
    }

    public void addPlace(long placeId) {
        placesIds.add(placeId);
    }

    public void removePlace(long placeId) {

    }
}
