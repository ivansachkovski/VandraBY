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
import com.example.vandraby.information.Sight;
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
        String login = ((TextView) findViewById(R.id.login_box)).getText().toString();
        String password = ((TextView) findViewById(R.id.password_box)).getText().toString();

        // TODO::remove this default values
        login = login.isEmpty() ? "admin" : login;
        password = password.isEmpty() ? "admin" : password;

        LoadDataTask loadDataTask = new LoadDataTask();
        loadDataTask.execute(login, password);
    }

    private void blockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.INVISIBLE);
    }

    private void unblockScreen() {
        findViewById(R.id.authorization_layout).setVisibility(View.VISIBLE);
    }

    public class LoadDataTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            // Input strings: [0] - login, [1] - password
            try {
                int userId = getUserId(strings[0], strings[1]);
                User user = getUser(userId);
                ArrayList<Sight> objects = getObjects();

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

        private ArrayList<Sight> getObjects() throws Exception {
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = RequestFactory.createGetAllSightsRequest(future);
            RequestQueue.getInstance(null).sendRequest(request);

            JSONObject response = future.get();
            int returnCode = response.getInt("code");
            if (returnCode == 0) {
                JSONArray jsonArrSights = response.getJSONArray("sights");
                ArrayList<Sight> objects = new ArrayList<>();
                for (int i = 0; i < jsonArrSights.length(); i++) {
                    objects.add(new Sight(jsonArrSights.getJSONObject(i)));
                }
                return objects;
            } else {
                throw new Exception();
            }
        }
    }
}
