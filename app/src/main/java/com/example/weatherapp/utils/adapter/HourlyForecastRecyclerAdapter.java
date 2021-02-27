package com.example.weatherapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.app.WeatherApp;
import com.example.weatherapp.model.HourlyForecastItem;

import java.util.ArrayList;

public class HourlyForecastRecyclerAdapter extends RecyclerView.Adapter<HourlyForecastRecyclerAdapter.ViewHolder> {

    private ArrayList<HourlyForecastItem> hourlyForecastItems;

    public HourlyForecastRecyclerAdapter(ArrayList<HourlyForecastItem> hourlyForecastItems) {
        this.hourlyForecastItems = hourlyForecastItems;
    }

    @NonNull
    @Override
    public HourlyForecastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyForecastItem item = hourlyForecastItems.get(position);
        WeatherApp app = new WeatherApp();

        // add more cases for other forecasts
        if (item.getForecast().equals("Sunny")) {
            holder.iconForecast.setImageResource(R.drawable.ic_sun);
        }

        if (item.getWindDirection().equals("SE")) {
            holder.iconWindDirection.setImageResource(R.drawable.ic_wind_arrow_se);
        }

        holder.time.setText(item.getTime());
        holder.temperature.setText(app.getApplicationContext()
                .getString(R.string.rv_grid_item_degrees, item.getTemperature()));
        holder.windSpeed.setText(app.getApplicationContext()
                .getString(R.string.rv_grid_item_wind_speed, item.getWindSpeed()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView time;
        public AppCompatTextView temperature;
        public AppCompatImageView iconForecast;
        public AppCompatImageView iconWindDirection;
        public AppCompatTextView windSpeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.text_view_rv_grid_item_time);
            temperature = itemView.findViewById(R.id.text_view_rv_grid_item_degrees);
            iconForecast = itemView.findViewById(R.id.image_view_rv_grid_item_icon_weather);
            iconWindDirection = itemView.findViewById(R.id.image_view_rv_grid_item_icon_wind_direction);
            windSpeed = itemView.findViewById(R.id.text_view_rv_grid_item_wind_speed);
        }
    }
}
