package com.example.weatherapp.activity;

import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.weatherapp.R;
import com.example.weatherapp.fragment.MainFragment;
import com.example.weatherapp.utils.LocationRequestManager;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.weatherapp.activity.ScreenActivity.EXTRA_LOCATION_LATITUDE;
import static com.example.weatherapp.activity.ScreenActivity.EXTRA_LOCATION_LONGITUDE;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonMoreClickListener,
        LocationRequestManager.OnLocationResultListener {

    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_activity_main_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LocationRequestManager(MainActivity.this).requestLocation();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        setupDrawerContent((NavigationView) findViewById(R.id.nav_view_activity_main));

        viewPager = findViewById(R.id.view_pager_activity_main);
        viewPager.setAdapter(new ViewPagerAdapter(MainActivity.this));
        TabLayout tabLayout = findViewById(R.id.tab_layout_activity_main);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.tab_title_main);
                        break;
                    case 1:
                        tab.setText(R.string.tab_title_more);
                }
            }
        }).attach();

        if (getIntent() != null) {
            String city = getCity(getIntent().getDoubleExtra(EXTRA_LOCATION_LATITUDE, 0.0),
                    getIntent().getDoubleExtra(EXTRA_LOCATION_LONGITUDE, 0.0));
            if (!TextUtils.isEmpty(city) && getSupportActionBar() != null) {
                getSupportActionBar().setTitle(city);
            }
        }
    }

    private String getCity(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        String city = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public void onLocationResult(Location result) {
        String city = getCity(result.getLatitude(), result.getLongitude());

        if (!TextUtils.isEmpty(city) && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(city);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onButtonClick() {
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_drawer_view_main) {

                } else if (item.getItemId() == R.id.item_drawer_view_settings) {
                    Toast.makeText(MainActivity.this, getString(R.string.toast_settings_selected),
                            Toast.LENGTH_SHORT).show();
                }

                item.setChecked(true);
                setTitle(item.getTitle());
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}