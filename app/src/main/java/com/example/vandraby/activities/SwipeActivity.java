package com.example.vandraby.activities;

import androidx.appcompat.app.AppCompatActivity;
import java.lang.Object.*;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.callbacks.DragAndDropSightCallback;
import com.example.vandraby.listeners.DragAndDropSightListener;
import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseImpl;
import com.example.vandraby.information.RequestHandler;
import com.example.vandraby.information.Sight;
import com.squareup.picasso.Picasso;

public class SwipeActivity extends AppCompatActivity implements DragAndDropSightCallback {

    private static final int MIN_SWIPE_DISTANCE = 150;
    private float xPressed; // to implement swipes

    private Sight[] sights;
    private int[] sightIndexes;
    private int pointerToCurrentSight = -1;
    private ImageView picture;

    private float savedPressedX;
    private float savedPressedY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        picture = findViewById(R.id.sight_picture);
        LinearLayout mainLayout = findViewById(R.id.main);

        mainLayout.setOnTouchListener((view, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                savedPressedX = event.getX();
                savedPressedY = event.getY();
            }

            return false;
        });

        mainLayout.setOnClickListener(view -> {

            // Toast.makeText(view.getContext(), "simple click", Toast.LENGTH_SHORT).show();

            int width = picture.getWidth();
            sightIndexes[pointerToCurrentSight] = updateValue(sightIndexes[pointerToCurrentSight], (savedPressedX < width / 2.0) ? (-1) : 1, 0, sights[pointerToCurrentSight].getPhotoUrls().length - 1);

            updateSightPicture(pointerToCurrentSight, sightIndexes[pointerToCurrentSight]);
        });

        mainLayout.setOnLongClickListener(view -> {

            // Toast.makeText(view.getContext(), "long click", Toast.LENGTH_SHORT).show();

            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view) {
                @Override
                public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
                    outShadowSize.set(view.getWidth(), view.getHeight());
                    outShadowTouchPoint.set((int)savedPressedX, (int)savedPressedY);
                }
            };

            view.startDrag(clipData, shadowBuilder, null, 0);

            return true;
        });

        mainLayout.setOnDragListener(new DragAndDropSightListener(this));

        loadSightsFromDatabase();
    }

    private void loadSightsFromDatabase() {

        StringRequest request = RequestHandler.getAllSightsRequest(this);

        DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
        database.sendRequest(request);
    }

    public void onSuccessLoadSights(Sight[] sights) {

        Toast.makeText(this, "All sights were loaded.", Toast.LENGTH_SHORT).show();

        pointerToCurrentSight = 0;
        this.sights = sights;
        this.sightIndexes = new int[sights.length];

        onUpdateSightElement(0, 0);
    }

    private int updateValue(int currentValue, int addition, int minRange, int maxRange) {

        currentValue += addition;
        if (currentValue < minRange) {
            currentValue = maxRange;
        }
        else if (currentValue > maxRange) {
            currentValue = minRange;
        }

        return currentValue;
    }

    private void onUpdateSightElement(float startX, float currentX) {

        float distanceX = startX - currentX;

        if (Math.abs(distanceX) >= MIN_SWIPE_DISTANCE) {
            // we have to swipe the element
            // Toast.makeText(this, "swipe.", Toast.LENGTH_SHORT).show();
            pointerToCurrentSight = updateValue(pointerToCurrentSight, (distanceX > 0) ? (-1) : 1, 0, sights.length - 1);
        }
        else if (distanceX == 0) {
            // it was a simple click, change the picture of the current sight
            // Toast.makeText(this, "pressed.", Toast.LENGTH_SHORT).show();
            int width = picture.getWidth();
            sightIndexes[pointerToCurrentSight] = updateValue(sightIndexes[pointerToCurrentSight], (currentX < width / 2.0) ? (-1) : 1, 0, sights[pointerToCurrentSight].getPhotoUrls().length - 1);
        }

        // TODO::check if we should not change the picture
        updateSightPicture(pointerToCurrentSight, sightIndexes[pointerToCurrentSight]);
        updateSightDescription(pointerToCurrentSight);
    }

    public void updateSightPicture(int sightIndex, int urlIndex) {

        String url = sights[sightIndex].getPhotoUrls()[urlIndex];
        Picasso.with(this).load(url).into(picture);
    }

    private void updateSightDescription(int sightIndex) {

        String description = sights[sightIndex].getDescription();
        TextView descriptionView = findViewById(R.id.sight_description);
        descriptionView.setText(description);
    }

    @Override
    public void onExitDragAndDrop(float startX, float finishX) {
        onUpdateSightElement(startX, finishX);
    }
}
