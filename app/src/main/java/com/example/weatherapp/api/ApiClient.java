package com.example.weatherapp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://dataservice.accuweather.com";
    private static ApiClient sClient;
    private final ApiInterface mApiInterface;

    private ApiClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new WeatherInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getInstance() {
        if (sClient == null) {
            sClient = new ApiClient();
        }
        return sClient;
    }

    public ApiInterface getApiInterface() {
        return mApiInterface;
    }
}
