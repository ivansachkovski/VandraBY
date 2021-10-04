package com.example.vandraby;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vandraby.activities.profile.ProfileActivity;
import com.example.vandraby.activities.swipe.SwipeActivity;

public class BasicActivityWithFooter extends AppCompatActivity {

    public void onClickBtnSwipes(View view) {
        Intent intent = new Intent(getApplicationContext(), SwipeActivity.class);
        startActivity(intent);
    }

    public void onClickBtnProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
