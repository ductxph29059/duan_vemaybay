package com.example.da1_group6.ui_user;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.da1_group6.adapter.Adapter_Recycler_VeMB_cxn_user;
import com.example.da1_group6.dao.DAO_VeMB;
import com.example.da1_group6.model.VeMB;

import java.util.ArrayList;

public class Fragment_Ve_chuaXN extends Fragment {
    RecyclerView recyclerView;
    DAO_VeMB dao;
    ArrayList<VeMB> list;
    Adapter_Recycler_VeMB_cxn_user adapter;
    String email;
    TextView tv_no_result;
    ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ve_chua_xn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_vmb_cxn);
        tv_no_result = view.findViewById(R.id.tv_no_result_cxn_user);
        img = view.findViewById(R.id.img_sad_cxn_user);

        SharedPreferences preferences = getActivity().getSharedPreferences("TB", Context.MODE_PRIVATE);
        email = preferences.getString("User", "");
        reload();

        if(list.isEmpty()) {
            tv_no_result.setText("Hmm...Có vẻ như không có gì ở đây ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    public void reload() {
        dao = new DAO_VeMB(getContext());
        list = dao.getVMBtheoKH(email, 0);
        adapter = new Adapter_Recycler_VeMB_cxn_user(list, getContext());
        recyclerView.setAdapter(adapter);
    }
}