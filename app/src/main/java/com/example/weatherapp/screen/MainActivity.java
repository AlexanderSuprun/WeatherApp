package com.example.weatherapp.screen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherapp.R;
import com.example.weatherapp.screen.main.MainFragment;
import com.example.weatherapp.utils.LocationRequestManager;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonMoreClickListener,
        LocationRequestManager.OnLocationResultListener {

    public static final int LOCATION_REQUEST_CODE = 142;
    private final LocationRequestManager locationRequestManager = new LocationRequestManager(MainActivity.this);
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity_main_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                locationRequestManager.requestLocation();
            }
        });

        viewPager = findViewById(R.id.view_pager_activity_main);
        viewPager.setAdapter(new ViewPagerAdapter(MainActivity.this));
    }

    @Override
    public void onLocationResult(Location result) {
        if (result != null) {
            String city = getCityAndCountry(result.getLatitude(), result.getLongitude())[0];
            if (!TextUtils.isEmpty(city)) {
                ((AppCompatTextView) findViewById(R.id.text_view_fragment_main_city)).setText(city);
            }
        } else {
            showMessageEnableGPS();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationRequestManager.clearActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationRequestManager.requestLocation();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationRequestManager.showMessageRationale(MainActivity.class);
            }
        }
    }

    private void showMessageEnableGPS() {
        new AlertDialog.Builder(MainActivity.this)
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
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    // Returns city and country code in String array
    private String[] getCityAndCountry(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        String[] result = new String[2];
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                result[0] = addresses.get(0).getLocality();
                result[1] = addresses.get(0).getCountryCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onButtonClick() {
        viewPager.setCurrentItem(1);
    }
}