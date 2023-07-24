package com.example.da1_group6.ui_staff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Frag_Collection_admin;
import com.example.da1_group6.adapter.Adapter_Frag_Collection_staff;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_Collection_Staff extends Fragment {
    ViewPager2 pager2;
    TabLayout tab;
    Adapter_Frag_Collection_staff adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__collection__staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager2 = view.findViewById(R.id.viewpager2_staff);
        tab = view.findViewById(R.id.tab_staff);
        adapter = new Adapter_Frag_Collection_staff(this);
        pager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tab, pager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0) {
                    tab.setText("Vé đã xác nhận");
                } else {
                    tab.setText("Vé đã huỷ");
                }
            }
        });
        mediator.attach();
    }
}