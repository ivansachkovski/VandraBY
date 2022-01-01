package com.example.vandraby.activities.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vandraby.R;

public class RegistrationActivity extends AppCompatActivity {

    private final static String LOGGER_TAG = "LOG_VANDRA_REG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(v -> {
            finish();
        });

    }
}
