package com.example.vandraby.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vandraby.information.Achievement;
import com.example.vandraby.information.DatabaseBase;
import com.example.vandraby.information.DatabaseMock;
import com.example.vandraby.R;
import com.example.vandraby.information.Trip;
import com.example.vandraby.information.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

public class ProfileActivity extends AppCompatActivity {

    DatabaseBase database = new DatabaseMock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        updateUserInformation();
        addInteractiveButtons();

    }

    private void updateUserInformation() {
        User user = database.getUser();

        updateUserPhoto(user);
        updateUserName(user);

        updateUserSubscriptions(user);
        updateUserAchievements(user);
        updateUserTrips(user);
    }

    private void updateUserPhoto(@NonNull User user) {
        ImageView imageView = (ImageView) findViewById(R.id.user_profile_image);
        Picasso.with(this).load(user.getPhotoUrl()).into(imageView);
    }

    private void updateUserName(@NonNull User user) {
        TextView userFullName = (TextView) findViewById(R.id.user_full_name_view);
        userFullName.setText(user.getFullName());

        TextView userNickname = (TextView) findViewById(R.id.user_nickname_view);
        userNickname.setText(user.getNickname());
    }

    private void updateUserSubscriptions(@NonNull User user) {
        TextView followersCounter = (TextView) findViewById(R.id.followers_counter_view);
        followersCounter.setText(String.valueOf(user.getFollowersNumber()));

        TextView followingCounter = (TextView) findViewById(R.id.following_counter_view);
        followingCounter.setText(String.valueOf(user.getFollowingNumber()));
    }

    private void updateUserAchievements(@NonNull User user) {
        LinearLayout achievementsContainer = (LinearLayout) findViewById(R.id.achievements_container);

        Vector<Achievement> achievements = user.getAchievements();
        for (Achievement achievement : achievements) {
            achievementsContainer.addView(createAchievementElement(achievement));
        }
    }

    private int dpToPx(int dp) {
        float density = this.getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    private View createAchievementElement(@NonNull Achievement achievement) {
        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setPadding(dpToPx(6),dpToPx(6),dpToPx(6),dpToPx(6));

        ImageView pic = new ImageView(this);
        LinearLayout.LayoutParams picParams = new LinearLayout.LayoutParams(dpToPx(72), dpToPx(72));

        pic.setLayoutParams(picParams);
        Picasso.with(this).load(achievement.getPictureUrl()).into(pic);

        view.addView(pic);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setClickable(true);
            view.setFocusable(true);
            view.setBackground(getDrawable(R.drawable.additional_button_style));
        }

        return view;
    }

    void updateUserTrips(@NonNull User user) {
        LinearLayout tripsContainer = (LinearLayout) findViewById(R.id.trips_container);

        Vector<Trip> trips = user.getTrips();
        for (Trip trip : trips) {
            tripsContainer.addView(createTripElement(trip));
        }
    }

    private View createTripElement(@NonNull Trip trip) {
        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        view.setPadding(dpToPx(6),dpToPx(6),dpToPx(6),dpToPx(6));

        ImageView pic = new ImageView(this);
        LinearLayout.LayoutParams picParams = new LinearLayout.LayoutParams(dpToPx(96), dpToPx(96));

        pic.setLayoutParams(picParams);
        Picasso.with(this).load(trip.getPictureUrl()).into(pic);

        TextView locationType = new TextView(this);
        locationType.setText(trip.getLocationType());

        TextView district = new TextView(this);
        district.setText(trip.getDistrict());

        TextView region = new TextView(this);
        region.setText(trip.getRegion());

        view.addView(pic);
        view.addView(locationType);
        view.addView(district);
        view.addView(region);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setClickable(true);
            view.setFocusable(true);
            view.setBackground(getDrawable(R.drawable.additional_button_style));
        }

        return view;
    }

    private void addInteractiveButtons() {
        addHelpMenu();
    }

    private void addHelpMenu() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.your_places_layout);
        layout.setOnClickListener(view -> Snackbar.make(view, "Переход в Ваши места не реализован", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}
