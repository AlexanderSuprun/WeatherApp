package com.example.weatherapp;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
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
    private final MutableLiveData<CurrentWeather> mCurrentWeather;
    private final MutableLiveData<String> mCity;
    private final MutableLiveData<List<DailyForecast>> mDailyForecasts;
    private final MutableLiveData<List<HourlyForecast>> mHourlyForecasts;

    public MainViewModel() {
        mCurrentWeather = new MutableLiveData<>();
        mCity = new MutableLiveData<>();
        mDailyForecasts = new MutableLiveData<>();
        mHourlyForecasts = new MutableLiveData<>();
        prefsManager = new AppPrefsManager();
        updateData();
    }

    // TODO: Fix call on screen rotation.
    @Override
    public void updateData() {
        mLocationManager.requestLocation();
    }

    @Override
    public void onLocationResult(Location result) {
        if (result != null) {
            MainRepository.getInstance().requestLocationKey(result, locationResponse -> {
                mCity.postValue(locationResponse.getCity());
                MainRepository.getInstance().requestCurrentWeather(currentWeatherList ->
                        mCurrentWeather.postValue(currentWeatherList.get(0)));
                MainRepository.getInstance().requestDailyForecasts(mDailyForecasts::postValue);
                MainRepository.getInstance().requestHourlyForecasts(mHourlyForecasts::postValue);
                prefsManager.saveLocationKey(locationResponse.getLocationKey());
                prefsManager.saveCity(locationResponse.getCity());
            });
        } else {
            if (prefsManager.getLocationKey() != AppPrefsManager.LOCATION_KEY_NULL) {
                MainRepository.getInstance().setLocationKey(prefsManager.getLocationKey());
                mCity.setValue(prefsManager.getCity());
                MainRepository.getInstance().requestCurrentWeather(currentWeatherList ->
                        mCurrentWeather.postValue(currentWeatherList.get(0)));
                MainRepository.getInstance().requestDailyForecasts(mDailyForecasts::postValue);
                MainRepository.getInstance().requestHourlyForecasts(mHourlyForecasts::postValue);
            }
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mLocationManager.clearContext();
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
