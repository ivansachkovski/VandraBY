package com.example.vandraby.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.vandraby.requests.RequestQueue;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.information.Sight;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SwipeActivity extends AppCompatActivity implements DragAndDropSightCallback {

    private static final int MIN_SWIPE_DISTANCE = 500;
    private float xPressed; // to implement swipes

    private final Queue<Sight> sightsQueue = new LinkedList<>();
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
            onUpdateSightElement(0, 0);
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

        StringRequest request = RequestFactory.createGetAllSightsRequest(this);

        RequestQueue requestQueue = RequestQueue.getInstance(getCacheDir());
        requestQueue.sendRequest(request);
    }

    public void onSuccessLoadSights(Sight[] sights) {

        Toast.makeText(this, "All sights were loaded.", Toast.LENGTH_SHORT).show();

        sightsQueue.addAll(Arrays.asList(sights));

        onUpdateSightElement(0, 0);
    }

    private void onUpdateSightElement(float startX, float currentX) {
        Sight sight = sightsQueue.peek();
        if (sight == null) {
            Toast.makeText(this, "Объекты закончились", Toast.LENGTH_SHORT).show();
            return;
        }

        float distanceX = startX - currentX;
        if (Math.abs(distanceX) >= MIN_SWIPE_DISTANCE) {
            // we have to swipe the element
            if (distanceX < 0) {
                Toast.makeText(this, "Like", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Dislike", Toast.LENGTH_SHORT).show();
            }
            sightsQueue.poll();
        }
        else if (distanceX == 0) {
            // it was a simple click, change the picture of the current sight
            // Toast.makeText(this, "pressed.", Toast.LENGTH_SHORT).show();
            int width = picture.getWidth();
            if (currentX < width / 2.0) {
                sight.updatePhotoIndex(-1);
            }
            else {
                sight.updatePhotoIndex(1);
            }
        }

        sight = sightsQueue.peek();
        if (sight != null) {
            updateSightPicture(sight);
            updateSightDescription(sight);
        }
        else {
            picture.setVisibility(View.INVISIBLE);
        }
    }

    public void updateSightPicture(Sight sight) {
        int photoIndex = sight.getPhotoIndex();
        String url = sight.getPhotoUrls()[photoIndex];
        Picasso.with(this).load(url).into(picture);
    }

    private void updateSightDescription(Sight sight) {
        TextView descriptionView = findViewById(R.id.sight_description);
        descriptionView.setText(sight.getDescription());
    }

    @Override
    public void onExitDragAndDrop(float startX, float finishX) {
        onUpdateSightElement(startX, finishX);
    }
}
