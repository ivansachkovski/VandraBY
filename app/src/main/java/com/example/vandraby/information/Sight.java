package com.example.vandraby.information;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class Sight {

    public Sight(JSONObject jsonObject) {
        try {
            name = (String) jsonObject.get("name");
            description = (String) jsonObject.get("description");

            photoUrls = ((String) jsonObject.get("urls")).split(";");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    private String name;
    private String description;
    private String[] photoUrls;
}
