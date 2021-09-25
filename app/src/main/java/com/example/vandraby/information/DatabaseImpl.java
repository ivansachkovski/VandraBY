package com.example.vandraby.information;

import android.app.VoiceInteractor;
import android.telecom.Call;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class DatabaseImpl {

    private RequestQueue requestQueue;

    private static DatabaseImpl instance = null;
    private User user = null;

    private DatabaseImpl(File cacheDir) {
        requestQueue = RequestQueue.getInstance(cacheDir);
    }

    public static DatabaseImpl getInstance(File cacheDir) {
        if (instance == null) {
            instance = new DatabaseImpl(cacheDir);
        }

        return instance;
    }

    public void loadUser(String login, String password, Callable<Void> onSuccess, Callable<Void> onFail) {

    }

    public void onSuccess(String response) {
        Log.i("Response received: ", response);
    }

    public void updateUser() {
        StringRequest request = RequestFactory.createUpdateUserInformationRequest(user, this::onSuccess, null);
        Log.i("request", "" + request.toString());

        requestQueue.sendRequest(request);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
