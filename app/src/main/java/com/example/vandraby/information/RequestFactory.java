package com.example.vandraby.information;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.activities.SwipeActivity;
import com.example.vandraby.callbacks.AuthorizationCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private final static String LOGGER_TAG = "RequestFactoryLogger";
    private final static String BASE_URL = "https://vandraby.000webhostapp.com";

    private final static String AUTHORIZATION_URL = "requests/Authorization.php";
    private final static String GET_USER_INFORMATION_BY_ID_URL = "requests/GetUserInformationById.php";
    private final static String GET_ALL_SIGHTS_URL = "requests/GetAllSights.php";

    public static StringRequest createAuthorizationRequest(String login, String password, AuthorizationCallback callback) {
        String url = String.format("%s/%s", BASE_URL, AUTHORIZATION_URL);

        return new StringRequest(Request.Method.POST, url, callback::onSuccessAuthorizationRequest, callback::onFail) {
            @Override
            protected @NotNull Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);
                return params;
            }
        };
    }

    public static StringRequest createGetUserInformationByIdRequest(int userId, AuthorizationCallback callback) {
        String url = String.format("%s/%s", BASE_URL, GET_USER_INFORMATION_BY_ID_URL);

        return new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.i(LOGGER_TAG, "GetUserInformationById response: " + response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            callback.onSuccessUserInformationLoadingRequest(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error ->                                    {
                    Toast.makeText((Context) callback, error.getMessage(), Toast.LENGTH_LONG).show();
                }) {

            @Override
            protected @NotNull Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(userId));
                return params;
            }
        };
    }

    public static StringRequest createGetAllSightsRequest(SwipeActivity activity) {
        String url = String.format("%s/%s", BASE_URL, GET_ALL_SIGHTS_URL);

        return new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.i(LOGGER_TAG, "GetAllSightsRequest response: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            JSONArray jsonSights = jsonObject.getJSONArray("sights");

                            Sight[] sights = new Sight[jsonSights.length()];
                            for (int i = 0; i < jsonSights.length(); i++) {
                                sights[i] = new Sight(jsonSights.getJSONObject(i));
                            }
                            activity.onSuccessLoadSights(sights);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(activity, "Невозможно распарсить json.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
