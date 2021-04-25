package com.example.vandraby.information;

public class Trip {
    public Trip(String pictureUrl, String locationType, String district, String region) {
        this.pictureUrl = pictureUrl;
        this.locationType = locationType;
        this.district = district;
        this.region = region;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getDistrict() {
        return district;
    }

    public String getRegion() {
        return region;
    }

    private String pictureUrl;
    private String locationType;
    private String district;
    private String region;
}
