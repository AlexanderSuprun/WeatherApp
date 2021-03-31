package com.example.weatherapp.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.location.LocationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationRequestManager {

    private final OnLocationResultListener listener;
    private Activity activity;

    public LocationRequestManager(Activity activity, OnLocationResultListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    // Call only if location permission granted
    // otherwise throws SecurityException.
    public void requestLocation() {
        if (LocationManagerCompat.isLocationEnabled((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE))) {
            try {
                FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(activity);
                locationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener(activity, listener::onLocationResult);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            try {
                listener.onLocationResult(((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE))
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearActivity() {
        activity = null;
    }

    public interface OnLocationResultListener {
        void onLocationResult(Location result);
    }
}
