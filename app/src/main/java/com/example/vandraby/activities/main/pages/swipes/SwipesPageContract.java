package com.example.vandraby.activities.main.pages.swipes;

import com.example.vandraby.model.Place;

public interface SwipesPageContract {

    interface View {
        void showPlaceToRate(Place place);
        void showEmptyScreen();
    }

    interface Model {
        Place getPlaceToRate();
        void swipe();
    }

    interface Presenter {
        void likePlace();
        void dislikePlace();
        void openPlaceDetails();

        void invalidateScreen();
    }
}
