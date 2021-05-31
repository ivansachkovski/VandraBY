package com.example.vandraby.information;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.activities.AuthorizationActivity;
import com.example.vandraby.activities.SwipeActivity;
import com.example.vandraby.callbacks.AuthorizationCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestCreator {
    private final static String LOGGER_TAG = "78787878";

    public static StringRequest getAuthorizationRequest(String login, String password, AuthorizationCallback callback) {
        String url ="https://vandraby.000webhostapp.com/requests/authorization.php";

        if (login.equals("")) {
            login = "admin";
            password = "admin";
        }

        String finalLogin = login;
        String finalPassword = password;

        return new StringRequest(Request.Method.POST, url, callback::onSuccessAuthorizationRequest, callback::onFail) {
            @Override
            protected @NotNull Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", finalLogin);
                params.put("password", finalPassword);

                return params;
            }
        };
    }

    public static StringRequest getUserInformationRequest(int userId, AuthorizationCallback callback) {
        String url ="https://vandraby.000webhostapp.com/requests/getuserinformation.php";

        return new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.i(LOGGER_TAG, "getuserinformation response: " + response);

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            User user = new User(jsonResponse);
                            callback.onSuccessUserInformationLoadingRequest(response);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
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

    public static StringRequest getAllSightsRequest(SwipeActivity activity) {
        String url ="https://vandraby.000webhostapp.com/allsights.php";

        return new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.length() > 0) {
                        try {
                            JSONArray jsonSights = new JSONArray(response);
                            Sight[] sights = new Sight[jsonSights.length()];
                            for (int i = 0; i < jsonSights.length(); i++) {
                                sights[i] = new Sight(jsonSights.getJSONObject(i));
                            }
                            activity.onSuccessLoadSights(sights);
                        } catch (JSONException e) {
                            Toast.makeText(activity, "Невозможно распарсить json.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(activity, "Нет достопремичательностей.", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
