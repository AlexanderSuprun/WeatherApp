package com.example.weatherapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
    private final LocationRequestManager locationRequestManager = new LocationRequestManager(ScreenActivity.this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        Glide.with(ScreenActivity.this)
                .load(R.drawable.animat_sun)
                .into((AppCompatImageView) findViewById(R.id.image_view_activity_screen_start_image));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                locationRequestManager.requestLocation();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationRequestManager.showMessageRationale(MainActivity.class);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

        } else {
            locationRequestManager.requestLocation();
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
        if (result != null) {
            Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainActivityIntent.putExtra(EXTRA_LOCATION_LATITUDE, result.getLatitude());
            mainActivityIntent.putExtra(EXTRA_LOCATION_LONGITUDE, result.getLongitude());
            startActivity(mainActivityIntent);
        } else {
            new AlertDialog.Builder(ScreenActivity.this)
                    .setMessage(R.string.alert_message_enable_gps)
                    .setPositiveButton(getString(R.string.button_title_close), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent mainActivityIntent = new Intent(ScreenActivity.this, MainActivity.class);
                            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainActivityIntent);
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationRequestManager.clearActivity();
    }
}
