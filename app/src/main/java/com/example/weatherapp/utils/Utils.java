package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.weatherapp.R;

import static com.example.weatherapp.MainActivity.LOCATION_REQUEST_CODE;

public class Utils {

    public static AlertDialog showEnableGPSDialog(Activity activity) {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.alert_message_enable_gps)
                .setPositiveButton(R.string.button_title_enable, (dialog, which) ->
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton(R.string.button_title_cancel, (dialog, which) -> dialog.dismiss())
                .setOnDismissListener(DialogInterface::dismiss).create();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static AlertDialog showMessageRationale(Activity activity) {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.rationale_message)
                .setPositiveButton(R.string.button_title_allow, (dialog, which) -> activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE))
                .setNegativeButton(R.string.button_title_cancel, (dialog, which) -> dialog.dismiss()).create();
    }

    public static Drawable getWeatherIcon(int iconNumber, Context context) {
        switch (iconNumber) {
            case 1:
            case 2:
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_sun_line);
            case 4:
            case 6:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_sun_cloudy_line);
            case 5:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_sun_foggy_line);
            case 7:
            case 8:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_cloudy_line);
            case 11:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_foggy_line);
            case 12:
            case 13:
            case 14:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_showers_line);
            case 15:
            case 16:
            case 17:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_thunderstorms_line);
            case 18:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_rainy_line);
            case 19:
            case 20:
            case 21:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_cloud_windy_line);
            case 22:
            case 23:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_snowy_line);
            case 24:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_icy);
            case 25:
            case 26:
            case 29:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_heavy_showers_line);
            case 30:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_temp_hot_line);
            case 31:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_temp_cold_line);
            case 32:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_windy_line);
            default:
                return ContextCompat.getDrawable(context, R.drawable.ic_weather_sun_cloudy_line);
        }
    }

    public static Drawable getWindDirectionIcon(String direction, Context context) {
        switch (direction) {
            case "N":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_n);
            case "NNE":
            case "NE":
            case "ENE":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_ne);
            case "E":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_e);
            case "ESE":
            case "SE":
            case "SSE":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_se);
            case "S":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_s);
            case "SSW":
            case "SW":
            case "WSW":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_sw);
            case "W":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_w);
            case "WNW":
            case "NW":
            case "NNW":
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_nw);
            default:
                return ContextCompat.getDrawable(context, R.drawable.ic_wind_arrow_n);
        }
    }

    public static String getWeatherText(String iconPhrase) {
        switch (iconPhrase.toLowerCase()) {
            case "sunny":
            case "mostly sunny":
            case "partly sunny":
                return "Sunny";
            case "intermittent clouds":
            case "mostly cloudy":
            case "cloudy":
                return "Cloudy";
            case "hazy sunshine":
                return "Hazy Sunshine";
            case "dreary (overcast)":
                return "Overcast";
            case "fog":
                return "Fog";
            case "showers":
            case "mostly cloudy w/ showers":
            case "partly sunny w/ showers":
                return "Showers";
            case "t-Storms":
            case "mostly cloudy w/ t-Storms":
            case "partly sunny w/ t-Storms":
                return "Thunderstorm";
            case "rain":
                return "Rain";
            case "flurries":
            case "mostly cloudy w/ flurries":
            case "partly sunny w/ flurries":
                return "Flurry";
            case "snow":
            case "mostly cloudy w/ snow":
                return "Snow";
            case "ice":
                return "Ice";
            case "sleet":
                return "Sleet";
            case "freezing rain":
                return "Cold Rain";
            case "rain and snow":
                return "Rain & Snow";
            case "hot":
                return "Hot";
            case "cold":
                return "Cold";
            case "windy":
                return "Windy";
            default:
                return "Undefined";
        }
    }
}
