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
import com.example.weatherapp.utils.adapter.DailyForecastRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeContract.View {

    private final HomeContract.Presenter presenter = new HomePresenter(this);
    private final ArrayList<DailyForecast> dailyForecastItems = new ArrayList<>();
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

        RecyclerView recyclerView = view.findViewById(R.id.rv_fragment_home_forecast_daily);
        adapter = new DailyForecastRecyclerAdapter(dailyForecastItems, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.button_fragment_home_more).setOnClickListener(new View.OnClickListener() {
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
    public void onDetach() {
        super.onDetach();
        presenter.dropView();
    }

    @Override
    public HomeContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void setCurrentWeather(CurrentWeather currentWeather) {
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_degrees_value))
                .setText(String.valueOf(Math.round(currentWeather.getTemperature())));
//        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_sign)).setText("+");
        ((AppCompatTextView) getView().findViewById(R.id.text_view_fragment_home_weather_status))
                .setText(currentWeather.getWeatherText());
        // TODO: Replace with something else
        ((AppCompatButton) getView().findViewById(R.id.button_fragment_home_aqi)).setText(getString(R.string.button_title_aqi, 20));
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