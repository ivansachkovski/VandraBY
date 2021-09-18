package com.example.vandraby.information;

import android.telecom.Call;

import com.example.vandraby.requests.RequestQueue;

import java.io.File;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
