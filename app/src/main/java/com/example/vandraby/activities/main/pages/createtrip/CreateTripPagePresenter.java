package com.example.vandraby.activities.main.pages.createtrip;

import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage;
import com.example.vandraby.activities.main.pages.swipes.SwipesPageContract;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;

public class CreateTripPagePresenter implements CreateTripPageContract.Presenter {

    private final CreateTripPageContract.View mView;
    private final CreateTripPageContract.Model mModel;

    public CreateTripPagePresenter(CreateTripPageContract.View view) {
        mView = view;
        mModel = new CreateTripPageModel(null);
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
    public void goToTheNextPage() {
        mView.goToTheNextPage();
    }

    @Override
    public void invalidateScreen() {
    }
}
