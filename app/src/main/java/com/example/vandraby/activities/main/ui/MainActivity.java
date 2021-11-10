package com.example.vandraby.activities.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.vandraby.R;
import com.example.vandraby.activities.authorization.ui.AuthorizationActivity;
import com.example.vandraby.activities.main.pages.details.PlaceDetailsPage;
import com.example.vandraby.activities.main.pages.profile.ProfilePage;
import com.example.vandraby.activities.main.pages.settings.ProfileSettingsPage;
import com.example.vandraby.activities.main.pages.settings.SearchSettingsPage;
import com.example.vandraby.activities.main.pages.swipes.SwipesPage;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;
import com.example.vandraby.model.User;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ProfileSettingsPage.OnAccountExitListener, PlaceDetailsPage.PlaceDetailsPageListener {

    private static final String SETTINGS_NAME = "userCredentials";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String PASSWORD_FIELD_NAME = "password";
    private static final String AUTO_LOGIN_FLAG_NAME = "auto-login";

    private static final String SWIPES_PAGE_TAG = "SwipesPage";
    private static final String PROFILE_PAGE_TAG = "ProfilePage";
    private static final String PLACE_DETAILS_PAGE_TAG = "PlaceDetailsPage";
    private static final String PROFILE_SETTINGS_PAGE_TAG = "ProfileSettingsPage";
    private static final String SEARCH_SETTINGS_PAGE_TAG = "SearchSettingsPage";

    NavigationBarView bottomNavigationBar;

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User user1 = new User(user);

        DataModel.getInstance().setUser(user1);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("places");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Place place = ds.getValue(Place.class);
                    int a = 2;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Place place = new Place();
        
        mDatabaseReference.push().setValue(place);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // disable showing title on toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            // imitate clicking "back" button
            this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
        });

        bottomNavigationBar = findViewById(R.id.bottom_navigation_panel);
        bottomNavigationBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_swipes:
                    return onOpenSwipesPage();
                case R.id.item_profile:
                    return onOpenProfilePage();
                default:
                    return false;
            }
        });

        // open swipes page
        bottomNavigationBar.setSelectedItemId(R.id.item_swipes);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        bottomNavigationBar.setSelectedItemId(R.id.item_swipes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemProfileSettings = menu.findItem(R.id.item_profile_settings);
        MenuItem itemSearchSettings = menu.findItem(R.id.item_search_settings);

        // hide all the items
        itemProfileSettings.setVisible(false);
        itemSearchSettings.setVisible(false);

        // show required item
        Fragment fragmentSwipes = getSupportFragmentManager().findFragmentByTag(SWIPES_PAGE_TAG);
        Fragment fragmentProfile = getSupportFragmentManager().findFragmentByTag(PROFILE_PAGE_TAG);
        if (fragmentSwipes != null && fragmentSwipes.isVisible()) {
            itemSearchSettings.setVisible(true);
        } else if (fragmentProfile != null && fragmentProfile.isVisible()) {
            itemProfileSettings.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_profile_settings:
                return onOpenProfileSettingsPage();
            case R.id.item_search_settings:
                return onOpenSearchSettingsPage();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean onOpenSwipesPage() {
        Fragment fragmentSwipes = getSupportFragmentManager().findFragmentByTag(SWIPES_PAGE_TAG);
        if (fragmentSwipes != null && fragmentSwipes.isVisible()) {
            return true;
        }

        loadFragment(SwipesPage.newInstance(this), SWIPES_PAGE_TAG, false);
        return true;
    }

    private boolean onOpenProfilePage() {
        loadFragment(ProfilePage.newInstance(this), PROFILE_PAGE_TAG, true);
        return true;
    }

    private boolean onOpenProfileSettingsPage() {
        loadFragment(ProfileSettingsPage.newInstance(this), PROFILE_SETTINGS_PAGE_TAG, true);
        return true;
    }

    private boolean onOpenSearchSettingsPage() {
        loadFragment(SearchSettingsPage.newInstance(), SEARCH_SETTINGS_PAGE_TAG, true);
        return true;
    }

    @Override
    public void onOpenPlaceDetailsPage(Place place) {
        loadFragment(PlaceDetailsPage.newInstance(place), PLACE_DETAILS_PAGE_TAG, true);
    }

    private void loadFragment(Fragment fragment, String tag, boolean bSave) {
        if (bSave) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment, tag).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment, tag).commit();
        }
    }

    @Override
    public void onAccountExitAction() {
        FirebaseAuth.getInstance().signOut();

        // Open authorization activity
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);

        // Close current activity
        finish();
    }
}