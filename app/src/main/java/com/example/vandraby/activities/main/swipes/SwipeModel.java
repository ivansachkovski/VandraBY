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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class SwipeModel {
    private static final String LOGGER_TAG = "VANDRA_DEV_SWIPE_MODEL";
    Queue<Sight> sights = new LinkedList<>();
    Callable<Void> readyCallback;

    public SwipeModel() {
        Log.i(LOGGER_TAG, "public SwipeModel()");
    }

    public void loadDataFromDatabase(Callable<Void> readyCallback) {
        this.readyCallback = readyCallback;
        StringRequest request = RequestFactory.createGetAllSightsRequest(this::onSuccessGetAllSightsRequest, this::onFailGetAllSightsRequest);
        RequestQueue.getInstance(null).sendRequest(request);
    }

    public void onSuccessGetAllSightsRequest(String response) {
        Log.i(LOGGER_TAG, "onSuccessGetAllSightsRequest response: " + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int returnCode = jsonObject.getInt("code");
            if (0 == returnCode) {
                JSONArray jsonArrSights = jsonObject.getJSONArray("sights");
                Sight[] sights = new Sight[jsonArrSights.length()];
                for (int i = 0; i < jsonArrSights.length(); i++) {
                    sights[i] = new Sight(jsonArrSights.getJSONObject(i));
                }
                this.sights.addAll(Arrays.asList(sights));
                readyCallback.call();
            } else {
                Log.e(LOGGER_TAG, "Response with invalid returnCode=" + returnCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFailGetAllSightsRequest(VolleyError error) {
        Log.e(LOGGER_TAG, "private void onFailGetAllSightsRequest(VolleyError error)");
    }

    public Sight getCurrentSight() {
        return sights.peek();
    }

    public void swipe() {
        sights.poll();
    }
}
