package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class DailyForecast {

    @SerializedName("EpochDate")
    private long epochDate;
    @SerializedName("Temperature")
    private Temperature temperature;
    @SerializedName("Day")
    private Day forecast;

    public DailyForecast(int epochDate, Temperature temperature, Day forecast) {
        this.epochDate = epochDate;
        this.temperature = temperature;
        this.forecast = forecast;
    }

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Day getForecast() {
        return forecast;
    }

    public void setForecast(Day forecast) {
        this.forecast = forecast;
    }

    public static class Temperature {
        @SerializedName("Minimum")
        Metric metricMin;
        @SerializedName("Maximum")
        Metric metricMax;

        public Metric getMetricMin() {
            return metricMin;
        }

        public void setMetricMin(Metric metricMin) {
            this.metricMin = metricMin;
        }

        public Metric getMetricMax() {
            return metricMax;
        }

        public void setMetricMax(Metric metricMax) {
            this.metricMax = metricMax;
        }
    }

    public static class Day {
        int iconNumber;
        @SerializedName("IconPhrase")
        String forecast;
        @SerializedName("HasPrecipitation")
        boolean hasPrecipitation;
        @SerializedName("PrecipitationType")
        String precipitationType;

        public int getIconNumber() {
            return iconNumber;
        }

        public void setIconNumber(int iconNumber) {
            this.iconNumber = iconNumber;
        }

        public String getForecast() {
            return forecast;
        }

        public void setForecast(String forecast) {
            this.forecast = forecast;
        }

        public boolean isHasPrecipitation() {
            return hasPrecipitation;
        }

        public void setHasPrecipitation(boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
        }

        public String getPrecipitationType() {
            return precipitationType;
        }

        public void setPrecipitationType(String precipitationType) {
            this.precipitationType = precipitationType;
        }
    }
}