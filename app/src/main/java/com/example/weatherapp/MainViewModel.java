package com.example.weatherapp;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.utils.LocationRequestManager;

import java.util.List;

public class MainViewModel extends ViewModel implements ViewModelContract.Activity, ViewModelContract.HomeFragment, ViewModelContract.MoreFragment,
        LocationRequestManager.OnLocationResultListener {

    public static final String LOCATION_NULL = "com.example.weatherapp.LOCATION_NULL";
    private final LocationRequestManager mLocationManager = new LocationRequestManager(this);
    private final MutableLiveData<CurrentWeather> mCurrentWeather = new MutableLiveData<>();
    private final MutableLiveData<String> mCity = new MutableLiveData<>();
    private final MutableLiveData<List<DailyForecast>> mDailyForecasts = new MutableLiveData<>();
    private final MutableLiveData<List<HourlyForecast>> mHourlyForecasts = new MutableLiveData<>();
    private final MutableLiveData<String> mLocationStatus = new MutableLiveData<>();

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
            });
        } else {
            mLocationStatus.setValue(LOCATION_NULL);
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
