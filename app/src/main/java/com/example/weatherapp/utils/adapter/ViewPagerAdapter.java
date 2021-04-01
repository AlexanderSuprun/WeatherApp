package com.example.weatherapp.utils.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.weatherapp.HomeFragment;
import com.example.weatherapp.MoreFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final HomeFragment homeFragment = new HomeFragment();
    private final MoreFragment moreFragment = new MoreFragment();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public HomeFragment getHomeFragment() {
        return this.homeFragment;
    }

    public MoreFragment getMoreFragment() {
        return this.moreFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return homeFragment;
            case 1:
                return moreFragment;
        }
        return homeFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
