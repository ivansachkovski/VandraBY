package com.example.vandraby.activities.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.vandraby.R;
import com.example.vandraby.activities.main.profile.ProfileFragment;
import com.example.vandraby.activities.main.swipes.SwipesFragment;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.requests.RequestQueue;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private int currentFragmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init request queue
        RequestQueue.getInstance(getCacheDir());

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation_panel);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_swipes:
                    return onClickSwipes();
                case R.id.item_profile:
                    return onClickProfile();
                default:
                    return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.item_profile);
    }

    private boolean onClickSwipes() {
        if (currentFragmentId == R.id.item_swipes) {
            // swipes page is open now
            return false;
        }
        currentFragmentId = R.id.item_swipes;
        loadFragment(SwipesFragment.newInstance());
        return true;
    }

    private boolean onClickProfile() {
        if (currentFragmentId == R.id.item_profile) {
            // profile page is open now
            return false;
        }
        currentFragmentId = R.id.item_profile;
        loadFragment(ProfileFragment.newInstance());
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();
    }
}