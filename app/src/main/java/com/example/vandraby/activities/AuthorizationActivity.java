package com.example.vandraby.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.R;
import com.example.vandraby.callbacks.AuthorizationCallback;
import com.example.vandraby.information.DatabaseImpl;
import com.example.vandraby.information.RequestCreator;
import com.example.vandraby.information.User;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationCallback {
    private final static String LOGGER = "78787878";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    public void onAttemptToBeAuthorized(View view) {
        onBlockScreen();

        EditText loginBox = findViewById(R.id.login_box);
        EditText passwordBox = findViewById(R.id.password_box);

        StringRequest request = RequestCreator.getAuthorizationRequest(loginBox.getText().toString(), passwordBox.getText().toString(), this);

        DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
        database.sendRequest(request);
    }

    @Override
    public void onSuccessAuthorizationRequest(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            if (success) {
                int userId = jsonResponse.getInt("id");

                StringRequest request = RequestCreator.getUserInformationRequest(userId, this);

                DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
                database.sendRequest(request);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        onUnblockScreen();
    }

    @Override
    public void onSuccessUserInformationLoadingRequest(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            if (success) {
                User user = new User(jsonResponse);

                DatabaseImpl database = DatabaseImpl.getInstance(getCacheDir());
                database.setUser(user);

                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        } catch (JSONException e) {
            onUnblockScreen();
            e.printStackTrace();
        }
        onUnblockScreen();
    }

    @Override
    public void onFail(VolleyError error) {
        onUnblockScreen();
    }

    private void onBlockScreen() {
        findViewById(R.id.login_box).setVisibility(View.INVISIBLE);
        findViewById(R.id.password_box).setVisibility(View.INVISIBLE);
        findViewById(R.id.button_enter).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

    private void onUnblockScreen() {
        findViewById(R.id.login_box).setVisibility(View.VISIBLE);
        findViewById(R.id.password_box).setVisibility(View.VISIBLE);
        findViewById(R.id.button_enter).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
    }
}