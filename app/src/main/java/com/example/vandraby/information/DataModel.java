package com.example.vandraby.information;

import android.util.Log;

import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DataModel {
    private static DataModel instance = null;
    private User user = null;
    public static Sight sight;
    private ArrayList<Sight> objects;

    private DataModel() {
    }

    public static DataModel getInstance() {
        if (instance == null) {
            instance = new DataModel();
        }
        return instance;
    }

    public ArrayList<Sight> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Sight> objects) {
        this.objects = objects;
    }

    public void loadUser(String login, String password, Callable<Void> onSuccess, Callable<Void> onFail) {

    }

    public void onSuccess(String response) {
        Log.i("Response received: ", response);
    }

    public void updateUser() {
        RequestQueue requestQueue;
        StringRequest request = RequestFactory.createUpdateUserInformationRequest(user, this::onSuccess, null);
        Log.i("request", "" + request.toString());

        //requestQueue.sendRequest(request);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        return user;
    }
}
