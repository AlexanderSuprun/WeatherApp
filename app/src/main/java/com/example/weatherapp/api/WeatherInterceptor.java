package com.example.weatherapp.api;

import com.example.weatherapp.R;
import com.example.weatherapp.app.WeatherApp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

class WeatherInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl url = originalRequest.url().newBuilder().addQueryParameter("apikey",
                WeatherApp.getAppContext().getString(R.string.apikey)).build();
        Request request = originalRequest.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
