package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.weatherapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

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
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(activity);
        try {
            locationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, null)
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                listener.onLocationResult(location);
                            } else {
                                showMessageGPSDisabled();
                            }
                        }
                    });
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

    private void showMessageGPSDisabled() {
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
    }

    public interface OnLocationResultListener {
        void onLocationResult(Location result);
    }
}
