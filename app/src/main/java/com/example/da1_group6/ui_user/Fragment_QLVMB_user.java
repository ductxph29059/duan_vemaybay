package com.example.da1_group6.ui_user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Frag_Collection_user;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_QLVMB_user extends Fragment {

    TabLayout tab;
    ViewPager2 viewPager2;
    Adapter_Frag_Collection_user adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlvmb_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.viewpager2);
        tab = view.findViewById(R.id.tab_user);
        adapter = new Adapter_Frag_Collection_user(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tab, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0) {
                    tab.setText("Vé đã xác nhận");
                } else if(position == 1){
                    tab.setText("Vé chờ xác nhận");
                } else {
                    tab.setText("Vé bị từ chối");
                }
            }
        });
        mediator.attach();
    }
}