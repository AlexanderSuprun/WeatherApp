package com.example.weatherapp.utils;

import android.app.Activity;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationRequestManager {

    private final OnLocationResultListener listener;
    private Activity activity;

    public LocationRequestManager(Activity activity) {
        if (activity instanceof OnLocationResultListener) {
            this.activity = activity;
            this.listener = (OnLocationResultListener) activity;
        } else {
            throw new ClassCastException(getClass().toString()
                    + " must implement OnLocationResultListener");
        }
    }

    // Call only if location permission granted
    // otherwise throws SecurityException.
    public void requestLocation() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(activity);
        try {
            locationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            listener.onLocationResult(location);
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void clearActivity() {
        activity = null;
    }

    public interface OnLocationResultListener {
        void onLocationResult(Location result);
    }
}
