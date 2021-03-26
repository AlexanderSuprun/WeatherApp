package com.example.weatherapp.screen.home;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter() {

    }

    @Override
    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void requestDailyForecast(int locationKey) {

    }

    @Override
    public void dropView() {
        view = null;
    }
}
