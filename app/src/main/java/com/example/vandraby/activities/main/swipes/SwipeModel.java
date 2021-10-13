package com.example.vandraby.activities.main.swipes;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.information.Sight;
import com.example.vandraby.requests.RequestFactory;
import com.example.vandraby.requests.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class SwipeModel {
    private static final String LOGGER_TAG = "VANDRA_DEV_SWIPE_MODEL";
    Queue<Sight> objects = new LinkedList<>();

    public SwipeModel(ArrayList<Sight> objects) {
        this.objects.addAll(objects);
    }

    public Sight getCurrentObject() {
        return objects.peek();
    }

    public void swipe() {
        objects.poll();
    }
}
