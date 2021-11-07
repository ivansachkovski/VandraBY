package com.example.vandraby.activities.main.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.vandraby.activities.main.pages.details.DetailsFragment;
import com.example.vandraby.activities.main.pages.profile.ProfileFragment;
import com.example.vandraby.activities.main.pages.settings.ProfileSettingsFragment;
import com.example.vandraby.activities.main.pages.settings.SearchSettingsFragment;
import com.example.vandraby.activities.main.pages.swipes.SwipesFragment;
import com.example.vandraby.model.Place;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ProfileSettingsFragment.OnAccountExitListener, DetailsFragment.PlaceDetailsPageListener {

    private static final String SETTINGS_NAME = "userCredentials";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String PASSWORD_FIELD_NAME = "password";
    private static final String AUTO_LOGIN_FLAG_NAME = "auto-login";

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // disable showing title on toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            // imitate clicking "back" button
            this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
            this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
        });

        NavigationBarView bottomNavigationView = findViewById(R.id.bottom_navigation_panel);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_swipes:
                    return onOpenSwipesPage();
                case R.id.item_profile:
                    return onOpenProfilePage();
                default:
                    return false;
            }
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {
            // do nothing
        });

        // open swipes page
        bottomNavigationView.setSelectedItemId(R.id.item_profile);
        bottomNavigationView.setSelectedItemId(R.id.item_swipes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        if (menu != null) {
            menu.findItem(R.id.item_search_settings).setVisible(true);
            menu.findItem(R.id.item_profile_settings).setVisible(false);
        }

        loadFragment(SwipesFragment.newInstance(this), false);
        return true;
    }

    private boolean onOpenProfilePage() {
        if (menu != null) {
            menu.findItem(R.id.item_search_settings).setVisible(false);
            menu.findItem(R.id.item_profile_settings).setVisible(true);
        }

        loadFragment(ProfileFragment.newInstance(), true);
        return true;
    }

    private boolean onOpenProfileSettingsPage() {
        loadFragment(ProfileSettingsFragment.newInstance(this), true);
        return true;
    }

    private boolean onOpenSearchSettingsPage() {
        loadFragment(SearchSettingsFragment.newInstance(), true);
        return true;
    }

    private void loadFragment(Fragment fragment, boolean bSave) {
        if (bSave) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, fragment).commit();
        }
    }

    @Override
    public void onAccountExitAction() {
        // Reset SharedPreferences
        SharedPreferences settings = getSharedPreferences(SETTINGS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LOGIN_FIELD_NAME, "");
        editor.putString(PASSWORD_FIELD_NAME, "");
        editor.putBoolean(AUTO_LOGIN_FLAG_NAME, false);
        editor.apply();

        // Open authorization activity
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);

        // Close current activity
        finish();
    }

    @Override
    public void onOpenPlaceDetailsPage(Place place) {
        loadFragment(DetailsFragment.newInstance(place), true);
    }
}