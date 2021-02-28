package com.example.weatherapp.app;

import android.app.Application;
import android.content.Context;

public class WeatherApp extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public Context getAppContext() {
        return context;
    }

}
