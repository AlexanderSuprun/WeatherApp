package com.example.weatherapp.screen.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapp.R;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter presenter;
    private ArrayList<DailyForecast> dailyForecastItems;
    private DailyForecastRecyclerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HomePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_main_forecast_daily);
        adapter = new DailyForecastRecyclerAdapter(dailyForecastItems, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.button_fragment_main_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof OnButtonMoreClickListener) {
                    OnButtonMoreClickListener listener = (OnButtonMoreClickListener) getActivity();
                    listener.onButtonClick();
                }
            }
        });
    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_main_degrees_value))
                .setText(String.valueOf(currentWeather.getTemperature().getMetric().getValue()));
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_main_sign)).setText("+");
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_main_weather_status))
                .setText(currentWeather.getWeatherText());
        ((AppCompatButton) getView().findViewById(R.id.button_fragment_main_aqi)).setText(getString(R.string.button_title_aqi,20));
    }

    @Override
    public void setDailyForecasts(List<DailyForecast> dailyForecastList) {
        dailyForecastItems.addAll(dailyForecastList);
        adapter.notifyDataSetChanged();
    }

    public interface OnButtonMoreClickListener {
        void onButtonClick();
    }
}