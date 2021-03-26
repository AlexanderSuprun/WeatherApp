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
import com.example.weatherapp.model.DailyForecast;
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Create presenters in fragments
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

        ArrayList<DailyForecast> dailyForecastItems = new ArrayList<>();

        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_degrees_value)).setText("5");
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_sign)).setText("+");
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_weather_status)).setText(getString(R.string.weather_sunny));
        ((AppCompatButton) view.findViewById(R.id.button_fragment_main_aqi)).setText(getString(R.string.button_title_aqi,20));
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_main_forecast_daily);

//        dailyForecastItems.add(new DailyForecast(getString(R.string.day_today), getString(R.string.weather_sunny), +5, +2));
//        dailyForecastItems.add(new DailyForecast(getString(R.string.day_monday), getString(R.string.weather_cloudy), 0, -1));
//        dailyForecastItems.add(new DailyForecast(getString(R.string.day_tuesday), getString(R.string.weather_sunny), +2, 0));
//        dailyForecastItems.add(new DailyForecast(getString(R.string.day_wednesday), getString(R.string.weather_rain), +3, -2));
//        dailyForecastItems.add(new DailyForecast(getString(R.string.day_thursday), getString(R.string.weather_sunny), +6, +2));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DailyForecastRecyclerAdapter(dailyForecastItems, getContext()));

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

    public interface OnButtonMoreClickListener {
        void onButtonClick();
    }
}