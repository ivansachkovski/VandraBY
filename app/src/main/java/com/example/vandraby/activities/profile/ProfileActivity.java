package com.example.vandraby.activities.profile;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vandraby.BasicActivityWithFooter;
import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.User;
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
    }
}
