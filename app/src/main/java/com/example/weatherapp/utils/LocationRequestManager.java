package com.example.weatherapp.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.location.LocationManagerCompat;

import com.example.weatherapp.app.WeatherApp;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationRequestManager {

    private final OnLocationResultListener mListener;
    private Context mContext = WeatherApp.getAppContext();

    public LocationRequestManager(OnLocationResultListener listener) {
        this.mListener = listener;
    }

    // Call only if location permission granted
    // otherwise throws SecurityException.
    public void requestLocation() {
        if (LocationManagerCompat.isLocationEnabled((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE))) {
            try {
                FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(mContext);
                locationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener(mListener::onLocationResult);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mListener.onLocationResult(((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE))
                        .getLastKnownLocation(LocationManager.GPS_PROVIDER));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearContext() {
        mContext = null;
    }

    public interface OnLocationResultListener {
        void onLocationResult(Location result);
    }
}
