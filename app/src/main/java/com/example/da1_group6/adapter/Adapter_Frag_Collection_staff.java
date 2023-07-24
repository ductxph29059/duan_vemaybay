package com.example.da1_group6.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.da1_group6.ui_admin.Fragment_ChuyenBay_admin;
import com.example.da1_group6.ui_admin.Fragment_HangMB_admin;
import com.example.da1_group6.ui_staff.Fragment_QLVMB_staff;
import com.example.da1_group6.ui_staff.Fragment_TCXNVMB_staff;
import com.example.da1_group6.ui_staff.Fragment_XNVMB_staff;

public class Adapter_Frag_Collection_staff extends FragmentStateAdapter {
    public Adapter_Frag_Collection_staff(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Fragment_QLVMB_staff();
            case 1: return new Fragment_TCXNVMB_staff();
            default:return new Fragment_QLVMB_staff();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
