package com.example.vandraby.activities.objectdetails;

import android.os.Bundle;
import android.widget.TextView;

import com.example.vandraby.BasicActivityWithFooter;
import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.User;

public class ObjectDetailsActivity extends BasicActivityWithFooter {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_details);

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getCacheDir());
        User user = databaseHandler.getCurrentUser();

        TextView tvObjectDetails = findViewById(R.id.tv_object_details);
        tvObjectDetails.setText(user.getFullName());
    }
}