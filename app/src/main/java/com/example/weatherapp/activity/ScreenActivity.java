package com.example.weatherapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.LocationRequestManager;

public class ScreenActivity extends AppCompatActivity implements LocationRequestManager.OnLocationResultListener {

    public static final String EXTRA_LOCATION_LATITUDE = "com.example.weatherapp.EXTRA_LOCATION_LATITUDE";
    public static final String EXTRA_LOCATION_LONGITUDE = "com.example.weatherapp.EXTRA_LOCATION_LONGITUDE";
    public static final int LOCATION_REQUEST_CODE = 142;
    private final LocationRequestManager locationRequestManager = new LocationRequestManager(this);

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationRequestManager.requestLocation();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationRequestManager.showMessageRationale(MainActivity.class);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationRequestManager.requestLocation();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationRequestManager.showMessageRationale(MainActivity.class);
            } else {
                Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainActivityIntent);
            }
        }
    }

    @Override
    public void onLocationResult(Location result) {
        Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainActivityIntent.putExtra(EXTRA_LOCATION_LATITUDE, result.getLatitude());
        mainActivityIntent.putExtra(EXTRA_LOCATION_LONGITUDE, result.getLongitude());
        startActivity(mainActivityIntent);
    }

    @Override
    public void onProviderDisabled() {

    }
}
