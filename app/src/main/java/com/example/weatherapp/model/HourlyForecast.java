package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class HourlyForecast {

    @SerializedName("EpochDateTime")
    private int epochTime;
    @SerializedName("IconPhrase")
    private String forecast;
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("Wind")
    private Wind wind;

    private static class Wind {
        @SerializedName("Speed")
        Speed speed;
        @SerializedName("Direction")
        Direction direction;
    }

    private static class Speed {
        @SerializedName("Value")
        float speed;
        @SerializedName("Unit")
        String unit;
    }

    private static class Direction {
        @SerializedName("English")
        String direction;
    }

    private static class Temperature {
        @SerializedName("Metric")
        Metric metric;
    }

}
