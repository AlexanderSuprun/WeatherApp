package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.weatherapp.R;

import java.util.function.Consumer;

import static com.example.weatherapp.activity.ScreenActivity.LOCATION_REQUEST_CODE;

public class LocationRequestManager {

    private final Activity activity;
    private final OnLocationResultListener listener;

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
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER, null, activity.getMainExecutor(),
                        new Consumer<Location>() {
                            @Override
                            public void accept(Location location) {
                                listener.onLocationResult(location);
                            }
                        });
            } else {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                        new AlertDialog.Builder(activity)
                                .setMessage(R.string.alert_message_enable_gps)
                                .setNeutralButton(activity.getString(R.string.button_title_close), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
                        listener.onProviderDisabled();
                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {

                    }

                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        listener.onLocationResult(location);
                    }
                }, null);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void showMessageRationale(Class<?> activityToStart) {
        new AlertDialog.Builder(activity)
                .setMessage(R.string.rationale_message)
                .setPositiveButton(activity.getString(R.string.button_title_allow), new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    }
                })
                .setNegativeButton(activity.getString(R.string.button_title_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainActivityIntent = new Intent(activity, activityToStart);
                        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(mainActivityIntent);
                    }
                })
                .create()
                .show();
    }

    public interface OnLocationResultListener {
        void onLocationResult(Location result);
        void onProviderDisabled();
    }
}
