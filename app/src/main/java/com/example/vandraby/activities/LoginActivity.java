package com.example.vandraby.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.R;
import com.example.vandraby.information.DatabaseImpl;
import com.example.vandraby.information.RequestHandler;
import com.example.vandraby.information.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void tryLoginApp(View view) {
        EditText loginBox = findViewById(R.id.login_box);
        EditText passwordBox = findViewById(R.id.password_box);

        StringRequest request = RequestHandler.getLoginRequest(loginBox.getText().toString(), passwordBox.getText().toString(), this);

        DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
        database.sendRequest(request);
    }

    public void loginApp(User user) {
        DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
        database.setUser(user);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

}