package com.example.weatherapp.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ScreenActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int LOCATION_REQUEST_CODE = 142;
    public static final String EXTRA_LOCATION_LATITUDE = "com.example.weatherapp.EXTRA_LOCATION_LATITUDE";
    public static final String EXTRA_LOCATION_LONGITUDE = "com.example.weatherapp.EXTRA_LOCATION_LONGITUDE";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Glide.with(ScreenActivity.this)
                .load(R.drawable.animat_sun)
                .into((AppCompatImageView) findViewById(R.id.image_view_activity_screen_start_image));

        requestLocationPermission();
    }

    // Using EasyPermissions wrapper library
    @AfterPermissionGranted(LOCATION_REQUEST_CODE)
    private void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            requestLocation();
        } else {
            EasyPermissions.requestPermissions(this, "Rationale message", LOCATION_REQUEST_CODE, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            requestLocation();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainActivityIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void requestLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        try {
            locationManager.requestSingleUpdate(criteria, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
                    mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mainActivityIntent.putExtra(EXTRA_LOCATION_LATITUDE, location.getLatitude());
                    mainActivityIntent.putExtra(EXTRA_LOCATION_LONGITUDE, location.getLongitude());
                    startActivity(mainActivityIntent);
                }
            }, null);
        } catch (SecurityException e) {
            throw new SecurityException(e);
        }
    }
}
