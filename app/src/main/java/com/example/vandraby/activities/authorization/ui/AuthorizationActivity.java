package com.example.vandraby.activities.authorization.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.vandraby.R;
import com.example.vandraby.activities.main.ui.MainActivity;
import com.example.vandraby.model.DataModel;
import com.example.vandraby.model.Place;
import com.example.vandraby.model.User;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuthorizationActivity extends AppCompatActivity {

    private final static String LOGGER_TAG = "VANDRA_LOGGER";

    SharedPreferences settings;
    private static final String SETTINGS_NAME = "userCredentials";
    private static final String LOGIN_FIELD_NAME = "login";
    private static final String PASSWORD_FIELD_NAME = "password";
    private static final String AUTO_LOGIN_FLAG_NAME = "auto-login";

    EditText editLoginInput;
    EditText editPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        // Initialize singleton
        RequestQueue.getInstance(getCacheDir());
        // Init views
        editLoginInput = findViewById(R.id.edit_login);
        editPasswordInput = findViewById(R.id.edit_password);
        // Fill views with the credentials from the  previous session
        settings = getSharedPreferences(SETTINGS_NAME, 0);
        editLoginInput.setText(settings.getString(LOGIN_FIELD_NAME, ""));
        editPasswordInput.setText(settings.getString(PASSWORD_FIELD_NAME, ""));

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(v -> {
            loginWithCredentials();
        });

        // Try to log in if auto-login is on
        if (settings.getBoolean(AUTO_LOGIN_FLAG_NAME, false)) {
            loginWithCredentials();
        }
    }

    private void loginWithCredentials() {
        // Get user's input
        String login = editLoginInput.getText().toString();
        String password = editPasswordInput.getText().toString();

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(LOGIN_FIELD_NAME, login);
        editor.putString(PASSWORD_FIELD_NAME, password);
        editor.putBoolean(AUTO_LOGIN_FLAG_NAME, true);
        editor.apply();

        AuthorizationTask task = new AuthorizationTask();
        task.execute(login, password);
    }

    private void blockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.INVISIBLE);
    }

    private void unblockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.VISIBLE);
    }

    public class AuthorizationTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            // Input strings: [0] - login, [1] - password
            try {
                int userId = getUserId(strings[0], strings[1]);
                User user = getUser(userId);
                ArrayList<Place> places = getPlaces();
                // Data was loaded successfully, save it
                DataModel dataModel = DataModel.getInstance();
                dataModel.setUser(user);
                dataModel.setPlaces(places);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            blockScreen();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                unblockScreen();
            }
        }

        private int getUserId(String login, String password) throws Exception {
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = RequestFactory.createAuthorizationRequest(login, password, future);
            RequestQueue.getInstance(null).sendRequest(request);

            JSONObject response = future.get();
            int returnCode = response.getInt("code");
            if (returnCode == 0) {
                return response.getInt("id");
            } else {
                throw new Exception();
            }
        }

        private User getUser(int userId) throws Exception {
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = RequestFactory.createGetUserInformationByIdRequest(userId, future);
            RequestQueue.getInstance(null).sendRequest(request);

            JSONObject response = future.get();
            int returnCode = response.getInt("code");
            if (returnCode == 0) {
                return new User(response);
            } else {
                throw new Exception();
            }
        }

        private ArrayList<Place> getPlaces() throws Exception {
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = RequestFactory.createGetAllPlacesRequest(future);
            RequestQueue.getInstance(null).sendRequest(request);

            JSONObject response = future.get();
            int returnCode = response.getInt("code");
            if (returnCode == 0) {
                JSONArray jsonArrPlaces = response.getJSONArray("places");
                ArrayList<Place> places = new ArrayList<>();
                for (int i = 0; i < jsonArrPlaces.length(); i++) {
                    places.add(new Place(jsonArrPlaces.getJSONObject(i)));
                }
                return places;
            } else {
                throw new Exception();
            }
        }
    }
}
