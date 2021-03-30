package com.example.weatherapp.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HourlyForecastRecyclerAdapter extends RecyclerView.Adapter<HourlyForecastRecyclerAdapter.ViewHolder> {

    private final ArrayList<HourlyForecast> hourlyForecastItems;
    private final Context context;
    private final Calendar calendar = Calendar.getInstance(Locale.getDefault());

    public HourlyForecastRecyclerAdapter(ArrayList<HourlyForecast> hourlyForecastItems, Context context) {
        this.hourlyForecastItems = hourlyForecastItems;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyForecastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_hourly_forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyForecast item = hourlyForecastItems.get(position);
        calendar.setTimeInMillis(item.getEpochTime());
        holder.iconWindDirection.setImageDrawable(Utils.getWindDirectionIcon(item.getWind().getDirection(), context));
        holder.time.setText(calendar.get(Calendar.HOUR_OF_DAY));
        holder.temperature.setText(context.getString(R.string.rv_hourly_forecast_item_degrees, item.getTemperature()));
        holder.windSpeed.setText(context.getString(R.string.rv_hourly_forecast_item_wind_speed, item.getWind().getSpeed()));
    }

    @Override
    public int getItemCount() {
        return hourlyForecastItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView time;
        public AppCompatTextView temperature;
        public AppCompatImageView iconForecast;
        public AppCompatImageView iconWindDirection;
        public AppCompatTextView windSpeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.text_view_rv_hourly_forecast_item_time);
            temperature = itemView.findViewById(R.id.text_view_rv_hourly_forecast_item_degrees);
            iconForecast = itemView.findViewById(R.id.image_view_rv_hourly_forecast_item_icon_weather);
            iconWindDirection = itemView.findViewById(R.id.image_view_rv_hourly_forecast_item_icon_wind_direction);
            windSpeed = itemView.findViewById(R.id.text_view_rv_hourly_forecast_item_wind_speed);
        }
    }
}
