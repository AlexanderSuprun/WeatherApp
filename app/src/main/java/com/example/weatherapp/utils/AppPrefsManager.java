package com.example.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weatherapp.app.WeatherApp;

public class AppPrefsManager {

    public static final String PREFS_NAME = "com.example.servicetestapp.PREFS_NAME";
    public static final String PREFS_CITY = "com.example.servicetestapp.PREFS_CITY";
    public static final String PREFS_LOCATION_KEY = "com.example.servicetestapp.PREFS_LOCATION_KEY";
    public static final String CITY_NULL = "NULL";
    public static final int LOCATION_KEY_NULL = 0;
    private final Context context;

    public AppPrefsManager() {
        this.context =  WeatherApp.getAppContext();
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveLocationKey(int locationKey) {
        getPrefs().edit().putInt(PREFS_LOCATION_KEY, locationKey).apply();
    }

    public int getLocationKey() {
        return getPrefs().getInt(PREFS_LOCATION_KEY, LOCATION_KEY_NULL);
    }

    public void saveCity(String city) {
        getPrefs().edit().putString(PREFS_CITY, city).apply();
    }

    public String getCity() {
        return getPrefs().getString(PREFS_CITY, CITY_NULL);
    }
}
