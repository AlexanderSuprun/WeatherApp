package com.example.weatherapp.activity;

import android.app.Activity;
import android.location.Location;

import com.example.weatherapp.utils.LocationRequestManager;

public class MainPresenter implements MainContract.Presenter, LocationRequestManager.OnLocationResultListener {

    private final LocationRequestManager locationRequestManager;
    private MainContract.View view;

    public MainPresenter(Activity activity) {
        this.view = (MainContract.View) activity;
        locationRequestManager = new LocationRequestManager(activity);
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
            view.setCity(MainRepository.getInstance().requestLocationKey(result, new MainRepository.OnLocationKeyResult() {
                @Override
                public void onLocationResult() {
                    //LocationKey received
                    //Now it's safe to call
                    //HomePresenter and MorePresenter methods to update data.
                }
            }).getCity());
        } else {
            view.showMessageEnableGPS();
        }
    }
}
