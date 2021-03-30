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
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DailyForecastRecyclerAdapter extends RecyclerView.Adapter<DailyForecastRecyclerAdapter.ViewHolder> {

    private final ArrayList<DailyForecast> dailyForecastItems;
    private final Context context;
    private final Calendar calendar = Calendar.getInstance(Locale.getDefault());

    public DailyForecastRecyclerAdapter(ArrayList<DailyForecast> dailyForecastItems, Context context) {
        this.dailyForecastItems = dailyForecastItems;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyForecastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_daily_forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast item = dailyForecastItems.get(position);

        holder.icon.setImageDrawable(Utils.getWeatherIcon(item.getForecast().getIconNumber(), context));
        holder.forecast.setText(item.getForecast().getForecast());
        holder.temperatureMax.setText(context.getString(R.string.rv_daily_forecast_item_degrees_max,
                Math.round(item.getTemperature().getMetricMax().getValue())));
        holder.temperatureMin.setText(context.getString(R.string.rv_daily_forecast_item_degrees_min,
                Math.round(item.getTemperature().getMetricMin().getValue())));

        if (position == 0) {
            holder.day.setText(context.getString(R.string.day_today));
        } else {
            calendar.setTimeInMillis(item.getEpochDate() * 1000);
            holder.day.setText(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        }
    }

    @Override
    public int getItemCount() {
        return dailyForecastItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView icon;
        AppCompatTextView day;
        AppCompatTextView forecast;
        AppCompatTextView temperatureMax;
        AppCompatTextView temperatureMin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.image_view_rv_daily_forecast_item_icon);
            day = itemView.findViewById(R.id.text_view_rv_daily_forecast_item_day_of_week);
            forecast = itemView.findViewById(R.id.text_view_rv_daily_forecast_item_forecast);
            temperatureMax = itemView.findViewById(R.id.text_view_rv_daily_forecast_item_degrees_max);
            temperatureMin = itemView.findViewById(R.id.text_view_rv_daily_forecast_item_degrees_min);
        }
    }

}
