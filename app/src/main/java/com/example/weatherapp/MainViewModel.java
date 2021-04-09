package com.example.weatherapp;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.utils.AppPrefsManager;
import com.example.weatherapp.utils.LocationRequestManager;

import java.util.List;

public class MainViewModel extends ViewModel implements ViewModelContract.Activity, ViewModelContract.HomeFragment, ViewModelContract.MoreFragment,
        LocationRequestManager.OnLocationResultListener {

    private final LocationRequestManager mLocationManager = new LocationRequestManager(this);
    private final AppPrefsManager prefsManager;
    private final MainRepository mMainRepository;
    private final LiveData<CurrentWeather> mCurrentWeather;
    private final MutableLiveData<String> mCity;
    private final LiveData<List<DailyForecast>> mDailyForecasts;
    private final LiveData<List<HourlyForecast>> mHourlyForecasts;
    private final LiveData<Integer> mLocationKey;
    private Observer<Integer> mLocationKeyObserver;

    public MainViewModel() {
        mMainRepository = new MainRepository();
        mCurrentWeather = mMainRepository.getCurrentWeather();
        mCity = (MutableLiveData<String>) mMainRepository.getCity();
        mDailyForecasts = mMainRepository.getDailyForecasts();
        mHourlyForecasts = mMainRepository.getHourlyForecasts();
        mLocationKey = mMainRepository.getLocationKey();
        prefsManager = new AppPrefsManager();
        updateData();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void updateData() {
        mLocationManager.requestLocation();
        mLocationKey.observeForever(mLocationKeyObserver = integer -> {
            mMainRepository.requestCurrentWeather();
            mMainRepository.requestDailyForecasts();
            mMainRepository.requestHourlyForecasts();
            prefsManager.saveLocationKey(mLocationKey.getValue());
            prefsManager.saveCity(mCity.getValue());
        });
    }

    @Override
    public void onLocationResult(Location result) {
        if (result != null) {
            mMainRepository.requestLocationKey(result);
        } else {
            //if location cannot be determined, get it from SharedPrefs
            if (prefsManager.getLocationKey() != AppPrefsManager.LOCATION_KEY_NULL) {
                mMainRepository.setLocationKey(prefsManager.getLocationKey());
                mCity.setValue(prefsManager.getCity());
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mLocationManager.clearContext();
        mLocationKey.removeObserver(mLocationKeyObserver);
    }

    @Override
    public LiveData<String> getCity() {
        return mCity;
    }

    @Override
    public LiveData<CurrentWeather> getCurrentWeather() {
        return mCurrentWeather;
    }

    @Override
    public LiveData<List<DailyForecast>> getDailyForecasts() {
        return mDailyForecasts;
    }

    @Override
    public LiveData<List<HourlyForecast>> getHourlyForecasts() {
        return mHourlyForecasts;
    }
}
