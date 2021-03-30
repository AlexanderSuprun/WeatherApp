package com.example.weatherapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.weatherapp.R;
import com.example.weatherapp.screen.home.HomeFragment;
import com.example.weatherapp.utils.Utils;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnButtonMoreClickListener,
        MainContract.View {

    public static final int LOCATION_REQUEST_CODE = 142;
    private MainContract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager viewPager;
    private boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        presenter = new MainPresenter(MainActivity.this, adapter);
        viewPager = findViewById(R.id.view_pager_activity_main);
        viewPager.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Utils.showMessageRationale(this);
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

        } else {
            isPermissionGranted = true;
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity_main_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.updateData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPermissionGranted) {
            presenter.updateData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void setCity(String city) {
        ((AppCompatTextView) findViewById(R.id.text_view_fragment_home_city)).setText(city);
    }

    @Override
    public void showMessageEnableGPS() {
        Utils.showEnableGPSDialog(this).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Utils.showMessageRationale(this).show();
            }
        }
    }

    @Override
    public void onButtonClick() {
        viewPager.setCurrentItem(1);
    }
}