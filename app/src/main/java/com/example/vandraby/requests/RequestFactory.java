package com.example.vandraby.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    private final static String BASE_URL = "https://vandraby.000webhostapp.com";

    private final static String AUTHORIZATION_URL = "requests/Authorization.php";
    private final static String GET_USER_INFORMATION_BY_ID_URL = "requests/GetUserInformationById.php";
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

    public static StringRequest createGetAllSightsRequest(Response.Listener<String> onSuccess, Response.ErrorListener onFail) {
        String url = String.format("%s/%s", BASE_URL, GET_ALL_SIGHTS_URL);

        return new StringRequest(Request.Method.GET, url, onSuccess, onFail);
    }
}
