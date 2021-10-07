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
    private static final String LOGGER_TAG = "DEVELOPMENT";
    Queue<Sight> sights = new LinkedList<>();
    Callable<Void> ready;

    public SwipeModel() {
        Log.d(LOGGER_TAG, "public SwipeModel()");
    }

    public void loadDataFromDatabase(Callable<Void> ready) {
        this.ready = ready;
        Log.d(LOGGER_TAG, "public void loadDataFromDatabase()");
        StringRequest request = RequestFactory.createGetAllSightsRequest(this::onSuccessGetAllSightsRequest, this::onFailGetAllSightsRequest);
        RequestQueue requestQueue = RequestQueue.getInstance(null);
        requestQueue.sendRequest(request);
    }

    public void onSuccessGetAllSightsRequest(String response) {
        Log.d(LOGGER_TAG, "private void onSuccessGetAllSightsRequest(String response)");
        Log.i(LOGGER_TAG, "onSuccessGetAllSightsRequest response: " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            int returnCode = jsonObject.getInt("code");
            if (0 == returnCode) {
                JSONArray jsonSights = jsonObject.getJSONArray("sights");
                Sight[] sights = new Sight[jsonSights.length()];
                for (int i = 0; i < jsonSights.length(); i++) {
                    sights[i] = new Sight(jsonSights.getJSONObject(i));
                }

                this.sights.addAll(Arrays.asList(sights));
                ready.call();
                //onSuccessLoadSights(sights);
            }  // TODO::

        } catch (JSONException e) {
            Toast.makeText(null, "Невозможно распарсить json.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onFailGetAllSightsRequest(VolleyError error) {
        Log.d(LOGGER_TAG, "private void onFailGetAllSightsRequest(VolleyError error)");
        Toast.makeText(null, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public Sight getCurrentSight() {
        return sights.peek();
    }

    public void swipe() {
        sights.poll();
    }
}
