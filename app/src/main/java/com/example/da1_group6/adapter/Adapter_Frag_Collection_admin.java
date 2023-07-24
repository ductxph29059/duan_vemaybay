package com.example.da1_group6.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_group6.ui_admin.Fragment_ChuyenBay_admin;
import com.example.da1_group6.ui_admin.Fragment_HangMB_admin;
import com.example.da1_group6.ui_user.Fragment_Ve_chuaXN;
import com.example.da1_group6.ui_user.Fragment_Ve_daXN;

public class Adapter_Frag_Collection_admin extends FragmentStateAdapter {
    public Adapter_Frag_Collection_admin(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment_HangMB_admin();
            case 1: return new Fragment_ChuyenBay_admin();
            default:return new Fragment_HangMB_admin();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
