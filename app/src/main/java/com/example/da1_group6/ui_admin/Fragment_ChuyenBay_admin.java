package com.example.da1_group6.ui_admin;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_Chuyenbay;
import com.example.da1_group6.dao.DAO_ChuyenBay;
import com.example.da1_group6.dao.DAO_HangMB;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.model.ChuyenBay;
import com.example.da1_group6.model.HangMB;
import com.example.da1_group6.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class Fragment_ChuyenBay_admin extends Fragment {
    RecyclerView recyclerView;
    DAO_ChuyenBay dao;
    Adapter_Recycler_Chuyenbay adapter;
    ArrayList<ChuyenBay> list;
    FloatingActionButton btnAdd;

    Spinner spin_hmb, spin_from, spin_to;
    EditText edt_macb, edt_timebay, edt_tongtime, edt_giave, edt_slve;
    Button btn_confirm, btn_cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__chuyen_bay_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_chuyenbay);
        btnAdd = view.findViewById(R.id.btn_add_chuyenbay);

        reload();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_add_chuyenbay);
                dialog.show();

                spin_hmb = dialog.findViewById(R.id.spin_hmb_in_dialog_themchuyenbay);
                spin_from = dialog.findViewById(R.id.spin_from_in_dialog_themchuyenbay);
                spin_to = dialog.findViewById(R.id.spin_to_in_dialog_themchuyenbay);
                edt_macb = dialog.findViewById(R.id.edt_macb_in_dialog_themchuyenbay);
                edt_timebay = dialog.findViewById(R.id.edt_timebay_in_dialog_themchuyenbay);
                edt_tongtime = dialog.findViewById(R.id.edt_tongtime_in_dialog_themchuyenbay);
                edt_giave = dialog.findViewById(R.id.edt_giave_in_dialog_themchuyenbay);
                edt_slve = dialog.findViewById(R.id.edt_slve_in_dialog_themchuyenbay);
                btn_cancel = dialog.findViewById(R.id.btn_cancel_in_dialog_themchuyenbay);
                btn_confirm = dialog.findViewById(R.id.btn_add_in_dialog_themchuyenbay);

                getDataHangMB(spin_hmb);
                String[] list = {"Hà Nội", "Hải Phòng", "Đà Nẵng", "Nha Trang", "TP. HCM", "Phú Quốc"};
                ArrayAdapter adapter_from = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);

                spin_from.setAdapter(adapter_from);
                spin_to.setAdapter(adapter_from);

                HashMap<String, Object> hashMap = (HashMap<String, Object>) spin_hmb.getSelectedItem();
                String mamb = (String) hashMap.get("mamb");

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            boolean result = dao.addCB(new ChuyenBay(edt_macb.getText().toString(), spin_from.getSelectedItem().toString(), spin_to.getSelectedItem().toString(),
                                    Integer.parseInt(edt_giave.getText().toString().trim()), edt_timebay.getText().toString(), edt_tongtime.getText().toString(),
                                    Integer.parseInt(edt_slve.getText().toString().trim()), mamb));
                            if (result == true) {
                                Toast.makeText(getContext(), "Thêm chuyến bay thành công!", Toast.LENGTH_SHORT).show();
                                reload();
                            } else {
                                Toast.makeText(getContext(), "Thêm chuyến bay thất bại!", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    public void getDataHangMB(Spinner spin_) {
        DAO_HangMB dao = new DAO_HangMB(getContext());
        ArrayList<HangMB> list = dao.getAll();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (HangMB hmb : list) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("mamb", hmb.getMamb());
            hm.put("tenmb", hmb.getTenmb());
            listHM.add(hm);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM,
                android.R.layout.simple_list_item_1, new String[]{"tenmb"}, new int[]{android.R.id.text1});
        spin_.setAdapter(simpleAdapter);
    }

    void reload() {
        list = new ArrayList<>();
        dao = new DAO_ChuyenBay(getContext());
        list = dao.getAll();
        adapter = new Adapter_Recycler_Chuyenbay(list, getContext(), getData(), dao);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    boolean check() {
        if (edt_macb.getText().toString().equals("") || edt_giave.getText().toString().equals("") || edt_slve.getText().toString().equals("") ||
                edt_timebay.getText().toString().equals("") || edt_tongtime.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spin_from.getSelectedItemPosition() == spin_to.getSelectedItemPosition()) {
            Toast.makeText(getContext(), "Điểm đi và điểm đến không được trùng nhau!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public ArrayList<HashMap<String, Object>> getData() {
        DAO_HangMB dao = new DAO_HangMB(getContext());
        ArrayList<HangMB> list = dao.getAll();
        ArrayList<HashMap<String, Object>> listHashMaps = new ArrayList<>();
        for (HangMB hmb : list) {
            HashMap<String, Object > hm = new HashMap<>();
            hm.put("mamb", hmb.getMamb());
            hm.put("tenmb", hmb.getTenmb());
            listHashMaps.add(hm);
        }
        return listHashMaps;
    }

}