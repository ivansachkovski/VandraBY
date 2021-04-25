package com.example.vandraby.information;

public class Achievement {
    public Achievement(String pictureUrl, String description) {
        this.pictureUrl = pictureUrl;
        this.description = description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getDescription() {
        return description;
    }

    private String description;
    private String pictureUrl;
}
