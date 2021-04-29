package com.example.vandraby.information;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vandraby.activities.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestHandler {
    public static StringRequest getLoginRequest(String login, String password, LoginActivity activity) {
        String url ="https://vandraby.000webhostapp.com/login.php";

        return new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.length() > 0) {
                        try {
                            User user = new User(new JSONObject(response));
                            activity.loginApp(user);
                        } catch (JSONException e) {
                            Toast.makeText((Context) activity, "Невозможно распарсить json.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText((Context) activity, "Неправильное имя пользователя и/или пароль.", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText((Context) activity, error.getMessage(), Toast.LENGTH_LONG).show();
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("login", login);
                params.put("password", password);

                return params;
            }
        };
    }
}
