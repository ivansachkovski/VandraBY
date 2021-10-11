package com.example.vandraby.information;

import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import java.io.File;
import java.util.concurrent.Callable;

public class DatabaseHandler {

    private final RequestQueue requestQueue;

    private static DatabaseHandler instance = null;
    private User user = null;

    private DatabaseHandler(File cacheDir) {
        requestQueue = RequestQueue.getInstance(cacheDir);
    }

    public static DatabaseHandler getInstance(File cacheDir) {
        if (instance == null) {
            instance = new DatabaseHandler(cacheDir);
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

    public User getCurrentUser() {
        return user;
    }

    public static Sight sight;
}
