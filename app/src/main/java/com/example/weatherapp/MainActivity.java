package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapp.utils.Utils;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnButtonMoreClickListener {

    public static final int LOCATION_REQUEST_CODE = 142;
    private ViewModelContract.Activity mModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;
    private boolean mIsPermissionGranted = false;
    private boolean mIsNetworkAvailable = false;
    private ConnectivityManager mConnectivityManager;
    private ConnectivityManager.NetworkCallback mNetworkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: Try to replace ViewPager with ViewPager2.
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager = findViewById(R.id.view_pager_activity_main);
        mViewPager.setAdapter(adapter);

        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                mIsNetworkAvailable = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                mIsNetworkAvailable = false;
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                mIsNetworkAvailable = false;
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mIsPermissionGranted = true;
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Utils.showMessageRationale(this).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

        } else {
            mIsPermissionGranted = true;
        }

//        mModel = new ViewModelProvider(this).get(MainViewModel.class);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity_main_container);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mIsPermissionGranted && mIsNetworkAvailable) {
                if (mModel == null) {
                    mModel = new ViewModelProvider(this).get(MainViewModel.class);
                }
                mModel.updateData();
                mModel.getCurrentWeather().observe(this, currentWeather -> mSwipeRefreshLayout.setRefreshing(false));
            } else if (mIsPermissionGranted) {
                Toast.makeText(getBaseContext(), getString(R.string.toast_network_unavailable), Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        mConnectivityManager.registerNetworkCallback(builder.build(), mNetworkCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsPermissionGranted && mIsNetworkAvailable) {
            mModel = new ViewModelProvider(this).get(MainViewModel.class);
            //mModel.updateData();
        } else if (mIsPermissionGranted) {
            Toast.makeText(getBaseContext(), getString(R.string.toast_network_unavailable), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mConnectivityManager.unregisterNetworkCallback(mNetworkCallback);
    }

    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mIsPermissionGranted = true;
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Utils.showMessageRationale(this).show();
            }
        }
    }

    @Override
    public void onButtonClick() {
        mViewPager.setCurrentItem(1);
    }
}