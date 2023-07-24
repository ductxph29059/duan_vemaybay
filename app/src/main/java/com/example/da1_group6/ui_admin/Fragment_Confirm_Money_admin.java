package com.example.da1_group6.ui_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_HDNT;
import com.example.da1_group6.dao.DAO_HoaDonNapTien;
import com.example.da1_group6.model.HoaDonNapTien;

import java.util.ArrayList;

public class Fragment_Confirm_Money_admin extends Fragment {
    RecyclerView recyclerView;
    DAO_HoaDonNapTien dao;
    ArrayList<HoaDonNapTien> list;
    Adapter_Recycler_HDNT adapter;
    TextView tv_no_result;
    ImageView ic_sad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__confirm__money, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_naptien);

        tv_no_result = view.findViewById(R.id.tv_no_result_naptien);
        ic_sad = view.findViewById(R.id.img_sad_naptien);

        reload();

        if(list.isEmpty()) {
            tv_no_result.setText("Hmm...Có vẻ như không có gì ở đây ");
            ic_sad.setImageResource(R.drawable.img_sad1);
        } else {
            tv_no_result.setText("");
            ic_sad.setImageDrawable(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    private void reload() {
        dao = new DAO_HoaDonNapTien(getContext());
        list = dao.getAllHoaDon();
        adapter = new Adapter_Recycler_HDNT(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}