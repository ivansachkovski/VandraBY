package com.example.vandraby.requests;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.information.User;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private final static String BASE_URL = "https://vandraby.000webhostapp.com";

    private final static String AUTHORIZATION_URL = "requests/Authorization.php";
    private final static String GET_USER_INFORMATION_BY_ID_URL = "requests/GetUserInformationById.php";
    private final static String UPDATE_USER_INFORMATION_URL = "requests/UpdateUserInformation.php";
    private final static String GET_ALL_SIGHTS_URL = "requests/GetAllSights.php";

    public static StringRequest createAuthorizationRequest(String login, String password, Response.Listener<String> onSuccess, Response.ErrorListener onFail) {
        String url = String.format("%s/%s", BASE_URL, AUTHORIZATION_URL);

        return new StringRequest(Request.Method.POST, url, onSuccess, onFail) {
            @Override
            protected @NotNull Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);

                return params;
            }
        };
    }

    public static StringRequest createGetUserInformationByIdRequest(int userId, Response.Listener<String> onSuccess, Response.ErrorListener onFail) {
        String url = String.format("%s/%s", BASE_URL, GET_USER_INFORMATION_BY_ID_URL);

        return new StringRequest(Request.Method.POST, url, onSuccess, onFail) {
            @Override
            protected @NotNull Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(userId));

                return params;
            }
        };
    }

    public static StringRequest createUpdateUserInformationRequest(User user, Response.Listener<String> onSuccess, Response.ErrorListener onFail) {
        String url = String.format("%s/%s", BASE_URL, UPDATE_USER_INFORMATION_URL);

        return new StringRequest(Request.Method.POST, url, onSuccess, onFail) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    String msg = user.toJson().toString();
                    Log.i("123123123", msg);
                    return msg.getBytes();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return super.getBody();
            }
        };
    }

    public static StringRequest createGetAllSightsRequest(Response.Listener<String> onSuccess, Response.ErrorListener onFail) {
        String url = String.format("%s/%s", BASE_URL, GET_ALL_SIGHTS_URL);

        return new StringRequest(Request.Method.GET, url, onSuccess, onFail);
    }
}
