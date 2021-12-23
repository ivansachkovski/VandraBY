package com.example.vandraby.activities.main.pages.createtrip;

import com.example.vandraby.activities.main.pages.swipes.SwipesPageContract;
import com.example.vandraby.model.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CreateTripPageModel implements CreateTripPageContract.Model {

    Queue<Place> mPlaces = new LinkedList<>();

    public CreateTripPageModel(ArrayList<Place> places) {
    //    mPlaces.addAll(places);
    }

    @Override
    public Place getPlaceToRate() {
        return mPlaces.peek();
    }

    @Override
    public void swipe() {
        mPlaces.poll();
    }
}
