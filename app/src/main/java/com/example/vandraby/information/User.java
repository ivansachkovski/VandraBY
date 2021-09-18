package com.example.vandraby.information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class User {

    public User(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");

        nickname = jsonObject.getString("nickname");

        firstName = jsonObject.getString("first_name");
        lastName = jsonObject.getString("last_name");

        photoUrl = jsonObject.getString("photo_url");

        followersNumber = jsonObject.getInt("followers_counter");
        followingsNumber = jsonObject.getInt("followings_counter");

        is_owner = jsonObject.getBoolean("is_owner");

        trips = getFakeTrips();
        achievements = getFakeAchievements();
    }

    private Vector<Trip> getFakeTrips() {
        String correctUrl = "https://cdn-s-static.arzamas.academy/x/119-bass-QqFwvKssCCYDK2ZmNx4zWzic/dom/img/dom-2.jpg";

        Vector<Trip> trips = new Vector<>();
        trips.add(new Trip(correctUrl, "Место1", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место2", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место3", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место4", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место5", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место6", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Место7", "Пинск", "Костюшко"));

        return trips;
    }

    private Vector<Achievement> getFakeAchievements() {
        String correctUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/ColumbaOenas.jpg/1200px-ColumbaOenas.jpg";

        Vector<Achievement> achievements = new Vector<>();
        achievements.add(new Achievement(correctUrl, "1"));
        achievements.add(new Achievement(correctUrl, "2"));
        achievements.add(new Achievement(correctUrl, "3"));
        achievements.add(new Achievement(correctUrl, "4"));
        achievements.add(new Achievement(correctUrl, "5"));
        achievements.add(new Achievement(correctUrl, "6"));
        achievements.add(new Achievement(correctUrl, "7"));
        achievements.add(new Achievement(correctUrl, "8"));

        return achievements;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    public int getFollowingNumber() {
        return followingsNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Vector<Achievement> getAchievements() {
        return achievements;
    }

    public Vector<Trip> getTrips() {
        return trips;
    }

    private final int id;

    private final String nickname;

    private final String firstName;
    private final String lastName;

    int followersNumber;
    int followingsNumber;

    private String photoUrl;

    private Vector<Integer> likedSights = new Vector<>();
    private Vector<Integer> dislikedSights = new Vector<>();

    private Vector<Achievement> achievements = new Vector<>();
    private Vector<Trip> trips = new Vector<>();

    private final boolean is_owner; // TODO::change the name of the flag
}
