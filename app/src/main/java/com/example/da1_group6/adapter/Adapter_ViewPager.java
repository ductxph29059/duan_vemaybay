package com.example.da1_group6.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_group6.ui_onboard.Fragment1;
import com.example.da1_group6.ui_onboard.Fragment2;
import com.example.da1_group6.ui_onboard.Fragment3;

public class Adapter_ViewPager extends FragmentStatePagerAdapter {
    public Adapter_ViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            default:
                return new Fragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
