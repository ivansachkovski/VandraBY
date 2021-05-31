package com.example.vandraby.callbacks;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.vandraby.information.User;

public interface AuthorizationCallback {
    void onSuccessAuthorizationRequest(String response);
    void onSuccessUserInformationLoadingRequest(String response);
    void onFail(VolleyError error);
}
