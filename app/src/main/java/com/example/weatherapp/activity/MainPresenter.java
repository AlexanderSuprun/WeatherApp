package com.example.weatherapp.activity;

import android.location.Location;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.screen.home.HomeContract;
import com.example.weatherapp.screen.home.HomeFragment;
import com.example.weatherapp.screen.more.MoreContract;
import com.example.weatherapp.screen.more.MoreFragment;
import com.example.weatherapp.utils.LocationRequestManager;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

public class MainPresenter implements MainContract.Presenter, LocationRequestManager.OnLocationResultListener {

    private final LocationRequestManager locationRequestManager;
    private final HomeFragment homeFragment;
    private final MoreFragment moreFragment;
    private HomeContract.Presenter homePresenter;
    private MoreContract.Presenter morePresenter;
    private MainContract.View view;

    public MainPresenter(AppCompatActivity activity, ViewPagerAdapter viewPagerAdapter) {
        this.view = (MainContract.View) activity;
        homeFragment = viewPagerAdapter.getHomeFragment();
        moreFragment = viewPagerAdapter.getMoreFragment();
        locationRequestManager = new LocationRequestManager(activity, this);
    }

    @Override
    public void updateData() {
        locationRequestManager.requestLocation();
    }

    @Override
    public void dropView() {
        view = null;
        locationRequestManager.clearActivity();
    }

    @Override
    public void onLocationResult(Location result) {
        if (result != null) {
//            MainRepository.getInstance().requestLocationKey(result, locationResponse -> {
//                //LocationKey received
//                //Now it's safe to call
//                //HomePresenter and MorePresenter methods to update data.
//                homePresenter = homeFragment.getPresenter();
//                morePresenter = moreFragment.getPresenter();
//                MainRepository.getInstance().requestCurrentWeather(currentWeather -> {
//                    homePresenter.updateCurrentWeather(currentWeather.get(0));
//                    morePresenter.updateCurrentWeatherDetails(currentWeather.get(0));
//                    view.hideProgress();
//                });
//                homePresenter.setCity(locationResponse.getCity());
//                homePresenter.updateDailyForecasts();
//                morePresenter.updateHourlyForecast();
//            });
        } else {
            view.hideProgress();
            view.showMessageEnableGPS();
        }
    }
}
