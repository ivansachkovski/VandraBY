package com.example.vandraby;

import android.os.Bundle;

import com.example.vandraby.information.DatabaseImpl;
import com.example.vandraby.information.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

public class ObjectDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_details);

        DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
        User user = database.getUser();

        TextView tvObjectDetails = findViewById(R.id.tv_object_details);
        tvObjectDetails.setText(user.getAchievements().toString());
    }
}