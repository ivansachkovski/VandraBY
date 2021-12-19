package com.example.vandraby.activities.main.pages.swipes;

import com.example.vandraby.model.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SwipesPageModel implements SwipesPageContract.Model {

    Queue<Place> mPlaces = new LinkedList<>();

    public SwipesPageModel(ArrayList<Place> places) {
        mPlaces.addAll(places);
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
