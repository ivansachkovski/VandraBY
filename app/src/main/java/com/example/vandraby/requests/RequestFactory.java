package com.example.vandraby.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestFactory {
    private final static String BASE_URL = "https://vandraby.000webhostapp.com";

    private final static String AUTHORIZATION_URL = "requests/Authorization.php";
    private final static String GET_USER_INFORMATION_BY_ID_URL = "requests/GetUserInformationById.php";
    private final static String UPDATE_USER_INFORMATION_URL = "requests/UpdateUserInformation.php";
    private final static String GET_ALL_PLACES_URL = "requests/GetAllPlaces.php";

    public static JsonObjectRequest createAuthorizationRequest(String login, String password, RequestFuture<JSONObject> future) throws JSONException {
        String url = String.format("%s/%s", BASE_URL, AUTHORIZATION_URL);

        JSONObject requestBody = new JSONObject();
        requestBody.put("login", login);
        requestBody.put("password", password);

        return new JsonObjectRequest(Request.Method.POST, url, requestBody, future, future);
    }

    public static JsonObjectRequest createGetUserInformationByIdRequest(int userId, RequestFuture<JSONObject> future) throws JSONException {
        String url = String.format("%s/%s", BASE_URL, GET_USER_INFORMATION_BY_ID_URL);

        JSONObject requestBody = new JSONObject();
        requestBody.put("id", userId);

        return new JsonObjectRequest(Request.Method.POST, url, requestBody, future, future);
    }

    // TODO::implement this method and use it when it's required
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

    public static JsonObjectRequest createGetAllPlacesRequest(RequestFuture<JSONObject> future) {
        String url = String.format("%s/%s", BASE_URL, GET_ALL_PLACES_URL);

        return new JsonObjectRequest(Request.Method.GET, url, null, future, future);
    }
}
