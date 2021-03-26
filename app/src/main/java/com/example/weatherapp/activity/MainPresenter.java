package com.example.weatherapp.activity;

import android.app.Activity;
import android.location.Location;

import com.example.weatherapp.api.APIClient;
import com.example.weatherapp.model.LocationResponse;
import com.example.weatherapp.screen.home.HomePresenter;
import com.example.weatherapp.screen.more.MorePresenter;
import com.example.weatherapp.utils.LocationRequestManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter, LocationRequestManager.OnLocationResultListener {

    private final LocationRequestManager locationRequestManager;
    private final Activity activity;
    private HomePresenter homePresenter;
    private MorePresenter morePresenter;
    private int locationKey;
    private String city;
    private MainContract.View view;

    public MainPresenter(Activity activity) {
        this.activity = activity;
        this.view = (MainContract.View) activity;
        locationRequestManager = new LocationRequestManager(activity);
        homePresenter = new HomePresenter();
        morePresenter = new MorePresenter();
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
            APIClient.getInstance()
                    .getApiInterface()
                    .getLocationKey(String.valueOf(result.getLatitude()) + ',' + result.getLongitude())
                    .enqueue(new Callback<LocationResponse>() {
                        @Override
                        public void onResponse(@NotNull Call<LocationResponse> call, @NotNull Response<LocationResponse> response) {
                            if (response.isSuccessful()) {
                                LocationResponse locationResponse = response.body();

                                if (locationResponse != null) {
                                    locationKey = locationResponse.getLocationKey();
                                    city = locationResponse.getCity();
                                    homePresenter.requestDailyForecast(locationKey);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<LocationResponse> call, @NotNull Throwable t) {
                            t.printStackTrace();
                        }
                    });
            view.setCity(city);
        } else {
            view.showMessageEnableGPS();
        }
    }
}
