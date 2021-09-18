package com.example.vandraby.activities.swipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vandraby.R;
import com.example.vandraby.information.Sight;
import com.squareup.picasso.Picasso;

public class SwipeActivity extends AppCompatActivity {
    SwipeModel model = new SwipeModel();
    SwipeController controller = new SwipeController(model, this);

    private ImageView picture;
    private ProgressBar progressBar;
    private LinearLayout bottomPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        picture = findViewById(R.id.sight_picture);
        progressBar = findViewById(R.id.progressBar2);
        bottomPanel = findViewById(R.id.swipe_bottom_panel);

        Button buttonLike = findViewById(R.id.button_like);
        buttonLike.setOnClickListener(view -> controller.onLikeButtonClick());

        Button buttonDislike = findViewById(R.id.button_dislike);
        buttonDislike.setOnClickListener(view -> controller.onDislikeButtonClick());

        FrameLayout frameLayout = findViewById(R.id.main);
        frameLayout.setOnClickListener(view -> controller.onUpdatePicture());

        controller.run();
    }

    public void updateScreen(Sight sight) {
        updateSightPicture(sight);
        updateSightDescription(sight);
    }

    public void updateSightPicture(Sight sight) {
        String url = sight.getActualPhotoUrl();
        Picasso.with(this).load(url).fit().into(picture);
    }

    private void updateSightDescription(Sight sight) {
        TextView nameView = findViewById(R.id.sight_name);
        nameView.setText(sight.getName());

        TextView descriptionView = findViewById(R.id.sight_description);
        descriptionView.setText(sight.getDescription());
    }

    public void blockScreen() {
        picture.setVisibility(View.INVISIBLE);
        bottomPanel.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void unblockScreen() {
        picture.setVisibility(View.VISIBLE);
        bottomPanel.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
