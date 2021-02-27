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
import com.example.weatherapp.model.DailyForecastItem;

import java.util.ArrayList;

public class DailyForecastRecyclerAdapter extends RecyclerView.Adapter<DailyForecastRecyclerAdapter.ViewHolder> {

    private ArrayList<DailyForecastItem> dailyForecastItems;

    public DailyForecastRecyclerAdapter(ArrayList<DailyForecastItem> dailyForecastItems) {
        this.dailyForecastItems = dailyForecastItems;
    }

    @NonNull
    @Override
    public DailyForecastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecastItem item = dailyForecastItems.get(position);

        // add more cases for other forecasts
        if (item.getForecast().equals("Sunny")) {
            holder.icon.setImageResource(R.drawable.ic_sun);
        }

        holder.day.setText(item.getDay());
        holder.forecast.setText(item.getForecast());
        holder.temperatureDay.setText(new WeatherApp().getApplicationContext()
                .getString(R.string.rv_list_item_degrees_day, item.getTemperatureDay()));
        holder.temperatureNight.setText(new WeatherApp().getApplicationContext()
                .getString(R.string.rv_list_item_degrees_night, item.getTemperatureNight()));
    }

    @Override
    public int getItemCount() {
        return dailyForecastItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView icon;
        AppCompatTextView day;
        AppCompatTextView forecast;
        AppCompatTextView temperatureDay;
        AppCompatTextView temperatureNight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.image_view_rv_list_item_icon);
            day = itemView.findViewById(R.id.text_view_rv_list_item_day_of_week);
            forecast = itemView.findViewById(R.id.text_view_rv_list_item_forecast);
            temperatureDay = itemView.findViewById(R.id.text_view_rv_list_item_degrees_day);
            temperatureNight = itemView.findViewById(R.id.text_view_rv_list_item_degrees_night);
        }
    }

}
