package com.example.vandraby.activities.profile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuItemImpl;

import com.example.vandraby.BasicActivityWithFooter;
import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.User;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends BasicActivityWithFooter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getCacheDir());
        User user = databaseHandler.getCurrentUser();

        ImageView imageView = findViewById(R.id.user_profile_image);
        Picasso.with(this).load(user.getPhotoUrl()).into(imageView);

        TextView userFullName = findViewById(R.id.user_full_name_view);
        userFullName.setText(user.getFullName());

        TextView userNickname = findViewById(R.id.user_nickname_view);
        userNickname.setText(String.format("@%s", user.getNickname()));

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // select default item in menu
        bottomNavigationView.setSelectedItemId(R.id.item_profile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_swipes:
                        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.item_swipes);
                        badge.setNumber(badge.getNumber() + 1);
                        badge.setVisible(true);
                        onClickBtnSwipes(null);
                        break;
                    case R.id.item_profile:
                        BadgeDrawable badge2 = bottomNavigationView.getOrCreateBadge(R.id.item_profile);
                        badge2.setNumber(badge2.getNumber() + 1);
                        badge2.setVisible(true);
                        break;
                }
                return false;
            }
        });
    }
}
