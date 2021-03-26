package com.example.weatherapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherapp.R;
import com.example.weatherapp.screen.home.HomeFragment;
import com.example.weatherapp.utils.DialogUtil;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnButtonMoreClickListener,
    MainContract.View {

    public static final int LOCATION_REQUEST_CODE = 142;
    private final DialogUtil dialogUtil = new DialogUtil(this);
    private MainContract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                presenter.updateData();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                dialogUtil.showMessageRationale();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }

        } else {
            presenter.updateData();
        }

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity_main_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.updateData();
            }
        });

        viewPager = findViewById(R.id.view_pager_activity_main);
        viewPager.setAdapter(new ViewPagerAdapter(MainActivity.this));
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
        dialogUtil.showEnableGPSDialog().show();
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
                presenter.updateData();
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                dialogUtil.showMessageRationale().show();
            }
        }
    }

    @Override
    public void onButtonClick() {
        viewPager.setCurrentItem(1);
    }
}