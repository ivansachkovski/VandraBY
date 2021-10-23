package com.example.vandraby.activities.authorization;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.vandraby.R;
import com.example.vandraby.activities.main.MainActivity;
import com.example.vandraby.information.DataModel;
import com.example.vandraby.information.Place;
import com.example.vandraby.information.User;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AuthorizationActivity extends AppCompatActivity {
    private final static String LOGGER_TAG = "VANDRA_LOGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        // Initialize
        RequestQueue.getInstance(getCacheDir());
    }

    public void onBtnLoginClick(View view) {
        // Get user's input
        String login = ((TextView) findViewById(R.id.edit_login)).getText().toString();
        String password = ((TextView) findViewById(R.id.edit_password)).getText().toString();

        // TODO::remove this default values
        login = login.isEmpty() ? "sachkovski" : login;
        password = password.isEmpty() ? "123" : password;

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
                ArrayList<Place> objects = getObjects();
                // Data was loaded successfully, save it
                DataModel dataModel = DataModel.getInstance();
                dataModel.setUser(user);
                dataModel.setObjects(objects);
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

        private ArrayList<Place> getObjects() throws Exception {
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
