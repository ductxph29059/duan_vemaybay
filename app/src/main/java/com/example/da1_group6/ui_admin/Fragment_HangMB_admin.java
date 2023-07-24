package com.example.da1_group6.ui_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_hangmb_admin;
import com.example.da1_group6.dao.DAO_HangMB;
import com.example.da1_group6.model.HangMB;

import java.util.ArrayList;

public class Fragment_HangMB_admin extends Fragment {
    RecyclerView recyclerView;
    Adapter_Recycler_hangmb_admin adapter;
    ArrayList<HangMB> list = new ArrayList<>();
    DAO_HangMB dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hangmb_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_hangmb);
        dao = new DAO_HangMB(getContext());
        list = dao.getAll();
        adapter = new Adapter_Recycler_hangmb_admin(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}