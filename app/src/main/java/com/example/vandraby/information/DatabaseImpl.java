package com.example.vandraby.information;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.Vector;

public class DatabaseImpl {
    public DatabaseImpl(File cacheDir) {
        this.cashDir = cacheDir;
    }

    public User getUser() {
        return createFakeUser();
    }

    private User createFakeUser() {
        User user = new User("Рома", "Клевжиц", "@r.klevzhits");

        String correctUrl = "https://sun9-21.userapi.com/impf/c637126/v637126533/10b43/3gEczuIgifs.jpg?size=1760x1707&quality=96&sign=d612a7bba1c66f1c72709fa64b2f9623&type=album";
        String profile = "https://vandraby.000webhostapp.com/images/users/defualt.png";
        user.setPhotoUrl(profile);

        user.setFollowersNumber(14);
        user.setFollowingNumber(88);

        Vector<Achievement> achievements = new Vector<>();
        achievements.add(new Achievement(correctUrl, "пёс"));
        achievements.add(new Achievement(correctUrl, "лох"));
        achievements.add(new Achievement(correctUrl, "чмо"));
        achievements.add(new Achievement(correctUrl, "хуй заморский"));
        achievements.add(new Achievement(correctUrl, "говно"));
        achievements.add(new Achievement(correctUrl, "урод"));
        achievements.add(new Achievement(correctUrl, "поц"));
        achievements.add(new Achievement(correctUrl, "хер"));
        user.setAchievements(achievements);

        correctUrl = "https://cdn-s-static.arzamas.academy/x/119-bass-QqFwvKssCCYDK2ZmNx4zWzic/dom/img/dom-2.jpg";
        Vector<Trip> trips = new Vector<>();
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        user.setTrips(trips);

        return user;
    }

    public void getBuildings() {
        RequestQueue requestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(cashDir, 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();

        String url ="https://vandraby.000webhostapp.com/allobjects.php";
        Vector<Building> buildings = new Vector<>();
        // Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); ++i) {
                            final boolean add = buildings.add(new Building(jsonArray.getJSONObject(i)));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                });
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        // ...
    }

    private final File cashDir;
}
