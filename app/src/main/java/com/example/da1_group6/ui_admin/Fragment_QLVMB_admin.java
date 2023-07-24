package com.example.da1_group6.ui_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_qlvmb_admin;
import com.example.da1_group6.dao.DAO_VeMB;
import com.example.da1_group6.model.VeMB;

import java.util.ArrayList;


public class Fragment_QLVMB_admin extends Fragment {
    RecyclerView recyclerView;
    DAO_VeMB dao;
    ArrayList<VeMB> list;
    TextView tv_no_result;
    ImageView img;
    Adapter_Recycler_qlvmb_admin adapter;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qlvmb_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_qlvmb_admin);
        tv_no_result = view.findViewById(R.id.tv_no_result_qlvmb_admin);
        img = view.findViewById(R.id.img_sad_qlvmb_admin);
        searchView = view.findViewById(R.id.search_qlvmb_admin);

        reload();

        if(list.isEmpty()) {
            tv_no_result.setText("Hmm...Có vẻ như không có gì ở đây ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                finditem(newText);
                return false;
            }
        });
    }

    private void finditem(String newText) {
        ArrayList<VeMB> listVMB = new ArrayList<>();
        for (VeMB vmb : list) {
            if(vmb.getMacb().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getTenkh().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getDiemdi().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getDiemden().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            } else if(vmb.getTimebay().toLowerCase().contains(newText.toLowerCase())) {
                listVMB.add(vmb);
            }
        }

        if(listVMB.isEmpty()) {
            listVMB.clear();
            adapter.setListSearch(listVMB);
            tv_no_result.setText("Hmm...Không có dữ liệu phù hợp ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            adapter.setListSearch(listVMB);
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    void reload() {
        dao = new DAO_VeMB(getContext());
        list = dao.getInfoUser_Staff();
        adapter = new Adapter_Recycler_qlvmb_admin(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}