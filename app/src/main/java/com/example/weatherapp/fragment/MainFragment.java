package com.example.weatherapp.fragment;

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
import com.example.weatherapp.model.DailyForecastItem;
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    public MainFragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<DailyForecastItem> dailyForecastItems = new ArrayList<>();

        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_degrees_value)).setText("5");
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_sign)).setText("+");
        ((AppCompatTextView) view.findViewById(R.id.text_view_fragment_main_weather_status)).setText("Sunny");
        ((AppCompatButton) view.findViewById(R.id.button_fragment_main_aqi)).setText(getString(R.string.button_title_aqi,20));
        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_main_forecast_daily);

        dailyForecastItems.add(new DailyForecastItem("Today", "Sunny", +5, +2));
        dailyForecastItems.add(new DailyForecastItem("Monday", "Cloudy", 0, -1));
        dailyForecastItems.add(new DailyForecastItem("Tuesday", "Sunny", +2, 0));
        dailyForecastItems.add(new DailyForecastItem("Wednesday", "Rain", +3, -2));
        dailyForecastItems.add(new DailyForecastItem("Thursday", "Sunny", +6, +2));

        recyclerView.setAdapter(new DailyForecastRecyclerAdapter(dailyForecastItems, getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}