package com.example.vandraby.activities.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.R;
import com.example.vandraby.activities.main.MainActivity;
import com.example.vandraby.information.DatabaseHandler;
import com.example.vandraby.information.User;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class AuthorizationActivity extends AppCompatActivity {
    private final static String LOGGER_TAG = "VANDRA_LOGGER";

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        requestQueue = RequestQueue.getInstance(getCacheDir());
    }

    public void onAttemptToBeAuthorized(View view) {
        // Block the screen to avoid user's manipulation during running the requests
        blockScreen();

        // Get user's input
        String login = ((TextView) findViewById(R.id.login_box)).getText().toString();
        String password = ((TextView) findViewById(R.id.password_box)).getText().toString();

        // TODO::remove this default values
        login = login.isEmpty() ? "admin" : login;
        password = password.isEmpty() ? "admin" : password;

        DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getCacheDir());
        databaseHandler.loadUser(login, password,
                () -> null,
                () -> null
        );

        // Check if the user with these credentials exists
        StringRequest request = RequestFactory.createAuthorizationRequest(login, password, this::onSuccessAuthorizationRequest, this::onFailAuthorizationRequest);
        requestQueue.sendRequest(request);
    }

    public void onSuccessAuthorizationRequest(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            int returnCode = jsonResponse.getInt("code");
            if (0 == returnCode) {
                // Get user's id from json response
                int userId = jsonResponse.getInt("id");

                // Get general information about the user by id
                StringRequest request = RequestFactory.createGetUserInformationByIdRequest(userId, this::onSuccessGetUserInformationLoadingRequest, this::onFailGetUserInformationLoadingRequest);
                requestQueue.sendRequest(request);
                return;
            }  // TODO::check return code to check if there are some issues or user is not exist

        } catch (JSONException e) {
            Log.e(LOGGER_TAG, Arrays.toString(e.getStackTrace()));
        }

        // Unblock screen because authorization request was not successful
        unblockScreen();
    }

    public void onFailAuthorizationRequest(VolleyError error) {
        Log.e(LOGGER_TAG, error.getMessage());

        // Unblock screen because authorization request failed
        unblockScreen();
    }

    public void onSuccessGetUserInformationLoadingRequest(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            int returnCode = jsonResponse.getInt("code");
            if (0 == returnCode) {
                User user = new User(jsonResponse);

                DatabaseHandler databaseHandler = DatabaseHandler.getInstance(getCacheDir());
                databaseHandler.setUser(user);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            // TODO::

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Unblock screen because get user information request failed
        unblockScreen();
    }

    public void onFailGetUserInformationLoadingRequest(VolleyError error) {
        Log.e(LOGGER_TAG, error.getMessage());

        // Unblock screen because get user information request failed
        unblockScreen();
    }

    private void blockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.INVISIBLE);
    }

    private void unblockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.VISIBLE);
    }
}
