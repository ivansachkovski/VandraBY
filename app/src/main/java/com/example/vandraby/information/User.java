package com.example.vandraby.information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class User {
    public User(String firstName, String lastName, String nickname) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
    }

    public User(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");

        nickname = jsonObject.getString("nickname");

        firstName = jsonObject.getString("first_name");
        lastName = jsonObject.getString("last_name");

        photoUrl = jsonObject.getString("photo_url");

        followersNumber = jsonObject.getInt("followers_counter");
        followingsNumber = jsonObject.getInt("followings_counter");

        //followers = jsonObject.getJSONArray("followers");
        //followings = jsonObject.getJSONArray("followings");

        trips = getFakeTrips();
        achievements = getFakeAchievements();
    }

    private Vector<Trip> getFakeTrips() {
        String correctUrl = "https://cdn-s-static.arzamas.academy/x/119-bass-QqFwvKssCCYDK2ZmNx4zWzic/dom/img/dom-2.jpg";

        Vector<Trip> trips = new Vector<>();
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));
        trips.add(new Trip(correctUrl, "Очко твоей мамаши", "Пинск", "Костюшко"));

        return trips;
    }

    private Vector<Achievement> getFakeAchievements() {
        String correctUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/ColumbaOenas.jpg/1200px-ColumbaOenas.jpg";

        Vector<Achievement> achievements = new Vector<>();
        achievements.add(new Achievement(correctUrl, "пёс"));
        achievements.add(new Achievement(correctUrl, "лох"));
        achievements.add(new Achievement(correctUrl, "чмо"));
        achievements.add(new Achievement(correctUrl, "хуй заморский"));
        achievements.add(new Achievement(correctUrl, "говно"));
        achievements.add(new Achievement(correctUrl, "урод"));
        achievements.add(new Achievement(correctUrl, "поц"));
        achievements.add(new Achievement(correctUrl, "хер"));

        return achievements;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFollowersNumber() {
        return followersNumber;
    }

    void setFollowersNumber(int followersNumber) {
        this.followersNumber = followersNumber;
    }

    public int getFollowingNumber() {
        return followingsNumber;
    }

    void setFollowingNumber(int followingsNumber) {
        this.followingsNumber = followingsNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Vector<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(Vector<Achievement> achievements) {
        this.achievements = achievements;
    }

    public Vector<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Vector<Trip> trips) {
        this.trips = trips;
    }

    private int id;

    private String nickname;

    private String firstName;
    private String lastName;

    Vector<Integer> followers;
    Vector<Integer> followings;

    int followersNumber;
    int followingsNumber;

    private String photoUrl;

    private Vector<Achievement> achievements = new Vector<>();
    private Vector<Trip> trips = new Vector<>();
}
