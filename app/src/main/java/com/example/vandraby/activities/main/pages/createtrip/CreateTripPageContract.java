package com.example.vandraby.activities.main.pages.createtrip;

import com.example.vandraby.model.Place;

public interface CreateTripPageContract {

    interface View {
        void goToTheNextPage();
    }

    interface Model {
        Place getPlaceToRate();
        void swipe();
    }

    interface Presenter {
        void likePlace();
        void dislikePlace();
        void goToTheNextPage();

        void invalidateScreen();
    }
}
