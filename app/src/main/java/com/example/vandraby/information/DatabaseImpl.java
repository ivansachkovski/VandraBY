package com.example.vandraby.information;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.util.Vector;

public class DatabaseImpl {
    private static DatabaseImpl instance = null;

    public static DatabaseImpl getInstance(File cashDir) {
        if (instance == null) {
            instance = new DatabaseImpl(cashDir);
        }
        return instance;
    }

    private DatabaseImpl(File cacheDir) {

        Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private User createFakeUser() {
        User user = new User("Рома", "Клевжиц", "@r.klevzhits");

        String correctUrl = "https://sun9-21.userapi.com/impf/c637126/v637126533/10b43/3gEczuIgifs.jpg?size=1760x1707&quality=96&sign=d612a7bba1c66f1c72709fa64b2f9623&type=album";
        String profile = "https://vandraby.000webhostapp.com/images/users/defualt.png";
        user.setPhotoUrl(profile);

        user.setFollowersNumber(14);
        user.setFollowingNumber(88);

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

    public void sendRequest(StringRequest request) {
        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }

    private User user = null;


    private final RequestQueue requestQueue;
}
