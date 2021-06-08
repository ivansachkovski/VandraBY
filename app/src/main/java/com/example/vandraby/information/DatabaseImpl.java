package com.example.vandraby.information;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.Vector;

public class DatabaseImpl {
    private static DatabaseImpl instance = null;

    public static DatabaseImpl getInstance(File cashDir) {
        if (instance == null) {
            instance = new DatabaseImpl(cashDir);
        }
        return instance;
    }

    private DatabaseImpl(File cacheDir) {

        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void sendRequest(StringRequest request) {
        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }

    private User user = null;

    private final RequestQueue requestQueue;
}
