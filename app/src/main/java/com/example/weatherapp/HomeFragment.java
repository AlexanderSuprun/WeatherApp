package com.example.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.utils.Utils;
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private final ArrayList<DailyForecast> mDailyForecastItems = new ArrayList<>();
    private DailyForecastRecyclerAdapter mAdapter;
    private ViewModelContract.HomeFragment mViewModel;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_home_forecast_daily);
        mAdapter = new DailyForecastRecyclerAdapter(mDailyForecastItems, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.button_fragment_home_more).setOnClickListener(v -> {
            if (getActivity() instanceof OnButtonMoreClickListener) {
                OnButtonMoreClickListener listener = (OnButtonMoreClickListener) getActivity();
                listener.onButtonClick();
            }
        });

        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mViewModel.getCity().observe(requireActivity(), s ->
                ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_city)).setText(s));
        mViewModel.getCurrentWeather().observe(requireActivity(), currentWeather -> {
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_degrees_value))
                    .setText(String.valueOf(Math.round(currentWeather.getTemperature())));
            ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_weather_status))
                    .setText(currentWeather.getWeatherText());
            ((AppCompatButton) getView().findViewById(R.id.button_fragment_home_wind))
                    .setText(getString(R.string.value_wind_speed, currentWeather.getWindSpeed(), currentWeather.getWindUnit()));
            ((AppCompatButton) getView().findViewById(R.id.button_fragment_home_wind))
                    .setCompoundDrawablesWithIntrinsicBounds(Utils.getWindDirectionIcon(currentWeather.getWindDirection(), getContext()),
                            null, null, null);
        });
        mViewModel.getDailyForecasts().observe(requireActivity(), dailyForecastList -> {
            mDailyForecastItems.clear();
            mDailyForecastItems.addAll(dailyForecastList);
            mAdapter.notifyDataSetChanged();
        });
    }

    public interface OnButtonMoreClickListener {
        void onButtonClick();
    }
}