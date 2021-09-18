package com.example.vandraby.information;

import org.json.JSONException;
import org.json.JSONObject;

public class Sight {
    private final int id;
    private final String name;
    private final String description;
    private final String[] photoUrls;
    private int photoIndex;

    public Sight(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
        description = jsonObject.getString("description");
        photoUrls = jsonObject.getString("urls").split(";");

        photoIndex = 0;
    }

    public int getId() {
        return id;
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

    public int getPhotoIndex() {
        return photoIndex;
    }

    public String getActualPhotoUrl() { return photoUrls[photoIndex]; }

    public void updatePhotoIndex(int offset) {
        photoIndex += offset;
        if (photoIndex < 0) {
            photoIndex = photoUrls.length - 1;
        }
        else if (photoIndex >= photoUrls.length) {
            photoIndex = 0;
        }
    }
}
