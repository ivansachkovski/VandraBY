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

        likedSights.clear();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("liked_sights");
            for (int i = 0; i < jsonArray.length(); ++i) {
                likedSights.add(jsonArray.getInt(i));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        is_owner = jsonObject.getBoolean("is_owner");
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    private final boolean is_owner; // TODO::change the name of the flag

    /**
     * Convert User object to JSON format.
     *
     * @return JSON presentation of User object.
     * @throws JSONException if any issues during creating JSON object.
     */
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname", nickname);
        jsonObject.put("first_name", firstName);
        jsonObject.put("last_name", lastName);
        jsonObject.put("photo_url", photoUrl);
        jsonObject.put("liked_sights", likedSights);
        return jsonObject;
    }

    public void addLikedObject(int id) {
        likedSights.add(id);
    }

    public void addDislikedObject(int id) {
        dislikedSights.add(id);
    }

    public boolean doLike(int objectId) {
        for (int id: likedSights) {
            if (id == objectId) {
                return true;
            }
        }
        return false;
    }
}
