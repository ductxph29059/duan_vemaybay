package com.example.da1_group6.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_group6.ui_user.Fragment_Ve_BiTC;
import com.example.da1_group6.ui_user.Fragment_Ve_chuaXN;
import com.example.da1_group6.ui_user.Fragment_Ve_daXN;

public class Adapter_Frag_Collection_user extends FragmentStateAdapter {
    public Adapter_Frag_Collection_user(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment_Ve_daXN();
            case 1: return new Fragment_Ve_chuaXN();
            case 2: return new Fragment_Ve_BiTC();
            default:return new Fragment_Ve_daXN();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
