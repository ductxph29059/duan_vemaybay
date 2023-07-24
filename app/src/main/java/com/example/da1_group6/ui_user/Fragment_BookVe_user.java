package com.example.da1_group6.ui_user;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_BookVe_user;
import com.example.da1_group6.dao.DAO_ChuyenBay;
import com.example.da1_group6.dao.DAO_HangMB;
import com.example.da1_group6.model.ChuyenBay;
import com.example.da1_group6.model.HangMB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Fragment_BookVe_user extends Fragment {
    Spinner spin_from, spin_to, spin_hangmb;
    ImageView btn_search_chuyenbay;
    ImageView choose_day, ic_daonguoc;
    TextView tv_date, tv_no_result;
    RecyclerView recyclerView;
    DAO_ChuyenBay dao;
    ArrayList<ChuyenBay> list_cb;
    Adapter_Recycler_BookVe_user adapter;
    ArrayAdapter adapter_from;
    String diemden, diemdi, ngaybay, mamb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_ve_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spin_from = view.findViewById(R.id.spin_from);
        spin_to = view.findViewById(R.id.spin_to);
        spin_hangmb = view.findViewById(R.id.spin_hangBay);
        btn_search_chuyenbay = view.findViewById(R.id.btn_search_chuyenbay);
        choose_day = view.findViewById(R.id.img_date_bookve_user);
        tv_date = view.findViewById(R.id.tv_date_bookve_user);
        recyclerView = view.findViewById(R.id.recycleriew_chuyenbay_in_bookve);
        tv_no_result = view.findViewById(R.id.tv_no_result);
        ic_daonguoc = view.findViewById(R.id.ic_daonguoc);


        String[] list = {"Hà Nội", "Hải Phòng", "Đà Nẵng", "Nha Trang", "TP. HCM", "Phú Quốc"};
        adapter_from = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);

        spin_from.setAdapter(adapter_from);
        spin_to.setAdapter(adapter_from);

        ic_daonguoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index_spinfrom = spin_from.getSelectedItemPosition();
                int index_spinto = spin_to.getSelectedItemPosition();
                spin_from.setSelection(index_spinto);
                spin_to.setSelection(index_spinfrom);
            }
        });

        getDataHangMB(spin_hangmb);

        choose_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_from();
            }
        });

        btn_search_chuyenbay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diemden = spin_from.getSelectedItem().toString();
                diemdi = spin_to.getSelectedItem().toString();
                ngaybay = tv_date.getText().toString().trim();

                if(spin_hangmb.getSelectedItemPosition() == 0) {
                    dao = new DAO_ChuyenBay(getContext());
                    list_cb = dao.getAllChuyenBay(diemden, diemdi, ngaybay);
                    adapter = new Adapter_Recycler_BookVe_user(list_cb, getContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) spin_hangmb.getSelectedItem();
                    mamb = (String) hashMap.get("mamb");

                    reload();
                }

                if(list_cb.isEmpty()) {
                    tv_no_result.setText("Không tìm thấy kết quả phù hợp!");
                } else {
                    tv_no_result.setText("");
                }
            }
        });
    }

    public void date_from() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int nam = i;
                        int thang = i1;
                        int ngay = i2;
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        calendar.set(nam, thang, ngay);
                        tv_date.setText(format.format(calendar.getTime()));
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        dialog.show();
    }

    public void getDataHangMB(Spinner spin_hangmb) {
        DAO_HangMB dao = new DAO_HangMB(getContext());
        ArrayList<HangMB> list = dao.getAll();
        list.add(0, new HangMB(null, "Tất cả"));
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (HangMB hmb : list ) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("mamb", hmb.getMamb());
            hm.put("tenmb", hmb.getTenmb());
            listHM.add(hm);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM,
                android.R.layout.simple_list_item_1, new String[] {"tenmb"}, new int[]{android.R.id.text1});
        spin_hangmb.setAdapter(simpleAdapter);
    }

    private void reload() {
        dao = new DAO_ChuyenBay(getContext());
        list_cb = dao.getChuyenBay(diemden, diemdi, mamb, ngaybay);
        adapter = new Adapter_Recycler_BookVe_user(list_cb, getContext());
        recyclerView.setAdapter(adapter);
    }
}