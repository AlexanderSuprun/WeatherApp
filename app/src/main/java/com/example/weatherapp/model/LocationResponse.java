package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class LocationResponse {

    @SerializedName("Key")
    private int locationKey;

    public int getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(int locationKey) {
        this.locationKey = locationKey;
    }
}
