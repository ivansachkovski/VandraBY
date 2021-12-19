package com.example.vandraby.activities.main.pages.swipes;

import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;

public class SwipesPagePresenter implements SwipesPageContract.Presenter {

    private final SwipesPageContract.View mView;
    private final SwipesPageContract.Model mModel;

    private final PlaceDetailsPage.PlaceDetailsPageListener mPlaceDetailsPageListener;

    public SwipesPagePresenter(SwipesPageContract.View view, PlaceDetailsPage.PlaceDetailsPageListener listener) {
        mView = view;
        mModel = new SwipesPageModel(DataModel.getInstance().getUnratedPlaces());

        mPlaceDetailsPageListener = listener;
    }

    @Override
    public void likePlace() {
        Place place = mModel.getPlaceToRate();
        DataModel.getInstance().likeObject(place.getId());

        mModel.swipe();
        invalidateScreen();
    }

    @Override
    public void dislikePlace() {
        Place place = mModel.getPlaceToRate();
        DataModel.getInstance().dislikeObject(place.getId());

        mModel.swipe();
        invalidateScreen();
    }

    @Override
    public void openPlaceDetails() {
        Place place = mModel.getPlaceToRate();
        mPlaceDetailsPageListener.onOpenPlaceDetailsPage(place);
    }

    @Override
    public void invalidateScreen() {
        Place place = mModel.getPlaceToRate();
        if (place == null) {
            mView.showEmptyScreen();
        } else {
            mView.showPlaceToRate(place);
        }
    }
}
