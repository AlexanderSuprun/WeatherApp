package com.example.weatherapp.screen.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.utils.Utils;
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View {

    private final ArrayList<DailyForecast> dailyForecastItems = new ArrayList<>();
    private HomeContract.Presenter presenter;
    private DailyForecastRecyclerAdapter adapter;

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
        presenter = new HomePresenter(this);
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_home_forecast_daily);
        adapter = new DailyForecastRecyclerAdapter(dailyForecastItems, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.button_fragment_home_more).setOnClickListener(v -> {
            if (getActivity() instanceof OnButtonMoreClickListener) {
                OnButtonMoreClickListener listener = (OnButtonMoreClickListener) getActivity();
                listener.onButtonClick();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.dropView();
    }

    @Override
    public void setCity(String city) {
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_city)).setText(city);
    }

    @Override
    public HomeContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_degrees_value))
                .setText(String.valueOf(Math.round(currentWeather.getTemperature())));
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_weather_status))
                .setText(currentWeather.getWeatherText());
        ((AppCompatButton) getView().findViewById(R.id.button_fragment_home_wind))
                .setText(getString(R.string.value_wind_speed, currentWeather.getWindSpeed(), currentWeather.getWindUnit()));
        ((AppCompatButton) getView().findViewById(R.id.button_fragment_home_wind))
                .setCompoundDrawablesWithIntrinsicBounds(Utils.getWindDirectionIcon(currentWeather.getWindDirection(), getContext()),
                        null, null, null);
    }

    @Override
    public void setDailyForecasts(List<DailyForecast> dailyForecastList) {
        dailyForecastItems.clear();
        dailyForecastItems.addAll(dailyForecastList);
        adapter.notifyDataSetChanged();
    }

    public interface OnButtonMoreClickListener {
        void onButtonClick();
    }
}