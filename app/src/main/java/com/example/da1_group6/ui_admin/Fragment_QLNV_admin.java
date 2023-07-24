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
import com.example.da1_group6.adapter.Adapter_Recycler_qlnv_admin;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.model.NhanVien;

import java.util.ArrayList;

public class Fragment_QLNV_admin extends Fragment {
    RecyclerView recyclerView;
    Adapter_Recycler_qlnv_admin adapter;
    DAO_QLNV dao;
    ArrayList<NhanVien> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlnv_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_qlynhanvien);
        dao = new DAO_QLNV(getContext());
        list = dao.getAll();
        adapter = new Adapter_Recycler_qlnv_admin(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}