package com.example.vandraby.activities.swipe;

import android.widget.Toast;

import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.Sight;

public class SwipeController {
    private final SwipeModel model;
    private final SwipeActivity view;

    public SwipeController(SwipeModel model, SwipeActivity view) {
        this.model = model;
        this.view = view;
    }

    public void run() {

        view.blockScreen();

        model.loadDataFromDatabase(() -> {
            view.updateScreen(model.getCurrentSight());
            view.unblockScreen();
            view.fooUdp();
            return null;
        });
    }

    public void onLikeButtonClick() {
        Sight sight = model.getCurrentSight();
        // TODO::save sight as liked sight
        DatabaseHandler.getInstance(null).getCurrentUser().addLikedSight(sight.getId());
        DatabaseHandler.getInstance(null).updateUser();
        Toast.makeText(view, "LIKE - " + sight.getId(), Toast.LENGTH_SHORT).show();

        model.swipe();
        updateScreen();
        view.fooUdp();
    }

    public void onDislikeButtonClick() {
        Sight sight = model.getCurrentSight();
        // TODO::save sight as disliked sight
        DatabaseHandler.getInstance(null).getCurrentUser().addDislikedSight(sight.getId());
        DatabaseHandler.getInstance(null).updateUser();
        Toast.makeText(view, "DISLIKE - " + sight.getId(), Toast.LENGTH_SHORT).show();

        model.swipe();
        updateScreen();
        view.fooUdp();
    }

    private void updateScreen() {
        Sight sight = model.getCurrentSight();
        if (sight != null) {
            view.updateScreen(sight);
        } else {
            view.blockScreen();
            Toast.makeText(view, "No more objects", Toast.LENGTH_SHORT).show();
        }
    }
}
