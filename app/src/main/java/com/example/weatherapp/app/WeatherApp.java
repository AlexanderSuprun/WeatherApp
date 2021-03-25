package com.example.weatherapp.app;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class WeatherApp extends Application {

    private static WeakReference<Context> context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = new WeakReference<Context>(this);
    }

    public static Context getAppContext() {
        return context.get();
    }

}
