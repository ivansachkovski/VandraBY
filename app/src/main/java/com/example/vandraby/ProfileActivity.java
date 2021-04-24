package com.example.vandraby;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

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
        updateUserPhoto();
    }

    private void updateUserPhoto() {
        ImageView imageView = (ImageView) findViewById(R.id.userProfileImage);
        Picasso.with(this).load("https://sun9-21.userapi.com/impf/c637126/v637126533/10b43/3gEczuIgifs.jpg?size=1760x1707&quality=96&sign=d612a7bba1c66f1c72709fa64b2f9623&type=album").into(imageView);
    }

    private void addInteractiveButtons() {
        addHelpMenu();
    }

    private void addHelpMenu() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.yourPlacesLayout);
        layout.setOnClickListener(view -> Snackbar.make(view, "Переход в Ваши места не реализован", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

}
