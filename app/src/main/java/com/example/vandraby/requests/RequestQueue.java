package com.example.vandraby.requests;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.io.File;

public class RequestQueue {
    private static RequestQueue instance = null;
    private final com.android.volley.RequestQueue requestQueue;

    private RequestQueue(File cacheDir) {
        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new com.android.volley.RequestQueue(cache, network);

        requestQueue.start();
    }

    public static RequestQueue getInstance(File cashDir) {
        if (instance == null) {
            instance = new RequestQueue(cashDir);
        }
        return instance;
    }

    public void sendRequest(JsonObjectRequest request) {
        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }
}
