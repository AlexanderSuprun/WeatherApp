package com.example.weatherapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.model.HourlyForecastItem;
import com.example.weatherapp.utils.adapter.HourlyForecastRecyclerAdapter;

import java.util.ArrayList;


public class MoreFragment extends Fragment {

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<HourlyForecastItem> hourlyForecastItems = new ArrayList<>();

        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_real_feel))
                .setText(getString(R.string.fragment_more_real_feel_value, 4.5));
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_chance_of_rain))
                .setText(getString(R.string.fragment_more_chance_of_rain_value, 5));
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_wind_speed))
                .setText(getString(R.string.fragment_more_value_wind_speed, 3.2));
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_humidity))
                .setText(getString(R.string.fragment_more_value_humidity, 78));
        ((AppCompatTextView) view.findViewById(R.id.text_size_fragment_more_value_pressure))
                .setText(getString(R.string.fragment_more_value_pressure, 1045.00));
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_uv_index))
                .setText("6");
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_more_value_aqi))
                .setText("38");
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_more_forecast_hourly);

        hourlyForecastItems.add(new HourlyForecastItem("Now", "Sunny", "SE", 5, 2.5f));
        hourlyForecastItems.add(new HourlyForecastItem("12:00", "Rain", "SE", 6, 2.8f));
        hourlyForecastItems.add(new HourlyForecastItem("13:00", "Cloudy", "S", 8, 3.0f));
        hourlyForecastItems.add(new HourlyForecastItem("14:00", "Cloudy", "SE", 10, 2.0f));
        hourlyForecastItems.add(new HourlyForecastItem("15:00", "Rain", "S", 6, 3.5f));
        hourlyForecastItems.add(new HourlyForecastItem("16:00", "Sunny", "SE", 4, 2.1f));

        recyclerView.setAdapter(new HourlyForecastRecyclerAdapter(hourlyForecastItems, getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}