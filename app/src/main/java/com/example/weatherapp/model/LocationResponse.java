package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class LocationResponse {

    @SerializedName("Key")
    private int locationKey;
    @SerializedName("EnglishName")
    private String city;

    public int getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(int locationKey) {
        this.locationKey = locationKey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
