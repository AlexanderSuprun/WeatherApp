package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.model.HourlyForecast;
import com.example.weatherapp.utils.adapter.HourlyForecastRecyclerAdapter;

import java.util.ArrayList;


public class MoreFragment extends Fragment {

    private final ArrayList<HourlyForecast> mHourlyForecastItems = new ArrayList<>();
    private ViewModelContract.MoreFragment mViewModel;
    private HourlyForecastRecyclerAdapter mAdapter;

    public MoreFragment() {
        // Required empty public constructor
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

        mAdapter = new HourlyForecastRecyclerAdapter(mHourlyForecastItems, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_more_forecast_hourly);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(mAdapter);

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.getHourlyForecasts().observe(requireActivity(), hourlyForecastList -> {
            mHourlyForecastItems.clear();
            mHourlyForecastItems.addAll(hourlyForecastList);
            mAdapter.notifyDataSetChanged();
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_chance_of_rain))
                    .setText(getString(R.string.fragment_more_chance_of_rain_value,
                            hourlyForecastList.get(0).getPrecipProbability()));
        });
        mViewModel.getCurrentWeather().observe(requireActivity(), currentWeather -> {
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_real_feel))
                    .setText(getString(R.string.fragment_more_real_feel_value,
                            currentWeather.getRealFeelTemperature()));
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_wind_speed))
                    .setText(getString(R.string.value_wind_speed,
                            currentWeather.getWindSpeed(), currentWeather.getWindUnit()));
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_humidity))
                    .setText(getString(R.string.fragment_more_value_humidity,
                            currentWeather.getHumidity()));
            ((AppCompatTextView) getView().findViewById(R.id.text_size_fragment_more_value_pressure))
                    .setText(getString(R.string.fragment_more_value_pressure,
                            currentWeather.getPressureValue()));
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_uv_index))
                    .setText(String.valueOf(currentWeather.getUvIndex()));
            // TODO: Replace with something else.
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_more_value_aqi))
                    .setText("38");
        });
    }
}