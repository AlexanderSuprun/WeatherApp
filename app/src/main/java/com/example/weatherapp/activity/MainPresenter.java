package com.example.weatherapp.activity;

import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.LocationResponse;
import com.example.weatherapp.screen.home.HomeContract;
import com.example.weatherapp.screen.more.MoreContract;
import com.example.weatherapp.utils.LocationRequestManager;
import com.example.weatherapp.utils.adapter.ViewPagerAdapter;

import java.util.List;

public class MainPresenter implements MainContract.Presenter, LocationRequestManager.OnLocationResultListener {

    private final LocationRequestManager locationRequestManager;
    private final HomeContract.Presenter homePresenter;
    private final MoreContract.Presenter morePresenter;
    private MainContract.View view;

    public MainPresenter(AppCompatActivity activity, ViewPagerAdapter viewPagerAdapter) {
        this.view = (MainContract.View) activity;
        this.homePresenter = viewPagerAdapter.getHomeFragment().getPresenter();
        this.morePresenter = viewPagerAdapter.getMoreFragment().getPresenter();
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
            MainRepository.getInstance().requestLocationKey(result, locationResponse -> {
                //LocationKey received
                //Now it's safe to call
                //HomePresenter and MorePresenter methods to update data.
                homePresenter.setCity(locationResponse.getCity());
                MainRepository.getInstance().requestCurrentWeather(currentWeather -> {
                    homePresenter.updateCurrentWeather(currentWeather.get(0));
                    morePresenter.updateCurrentWeatherDetails(currentWeather.get(0));
                    view.hideProgress();
                });
                homePresenter.updateDailyForecasts();
                morePresenter.updateHourlyForecast();
            });
        } else {
            view.hideProgress();
            view.showMessageEnableGPS();
        }
    }
}
