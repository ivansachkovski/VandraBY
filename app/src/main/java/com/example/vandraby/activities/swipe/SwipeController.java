package com.example.vandraby.activities.swipe;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.vandraby.information.DatabaseImpl;
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
            return null;
        });
    }

    public void onLikeButtonClick() {
        Sight sight = model.getCurrentSight();
        // TODO::save sight as liked sight
        DatabaseImpl.getInstance(null).getUser().addLikedSight(sight.getId());
        DatabaseImpl.getInstance(null).updateUser();
        Toast.makeText(view, "LIKE - " + sight.getId(), Toast.LENGTH_SHORT).show();

        model.swipe();
        updateScreen(model.getCurrentSight());
    }

    public void onDislikeButtonClick() {
        Sight sight = model.getCurrentSight();
        // TODO::save sight as disliked sight
        DatabaseImpl.getInstance(null).getUser().addDislikedSight(sight.getId());
        DatabaseImpl.getInstance(null).updateUser();
        Toast.makeText(view, "DISLIKE - " + sight.getId(), Toast.LENGTH_SHORT).show();

        model.swipe();
        updateScreen(model.getCurrentSight());
    }

    public void onUpdatePicture() {
        model.updateShowedPhoto();
        updateScreen(model.getCurrentSight());
    }

    private void updateScreen(Sight sight) {
        if (sight != null) {
            view.updateScreen(sight);
        } else {
            view.blockScreen();
            Toast.makeText(view, "No more objects", Toast.LENGTH_SHORT).show();
        }
    }
}
