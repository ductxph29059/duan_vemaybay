package com.example.da1_group6.ui_user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_Recycler_LSGD;
import com.example.da1_group6.dao.DAO_HoaDonNapTien;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.dao.DAO_LSGD;
import com.example.da1_group6.model.HoaDonNapTien;
import com.example.da1_group6.model.KhachHang;
import com.example.da1_group6.model.LSGD;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_Wallet_user extends Fragment {
    LinearLayout btn_naptien;
    TextView tvsodu;
    DAO_KhachHang dao;
    KhachHang kh;
    ArrayList<KhachHang> list;
    RecyclerView recyclerView;
    Adapter_Recycler_LSGD adapter;
    ArrayList<LSGD> list_lsgd;
    DAO_LSGD dao_lsgd;
    TextView tv_no_result;
    ImageView img;
    int makh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_naptien = view.findViewById(R.id.btn_naptien);
        tvsodu = view.findViewById(R.id.tv_sodu);
        recyclerView = view.findViewById(R.id.recyclerview_lsgd);
        tv_no_result = view.findViewById(R.id.tv_no_result_lsgd);
        img = view.findViewById(R.id.img_sad_lsgd);

        SharedPreferences preferences = getActivity().getSharedPreferences("TB", Context.MODE_PRIVATE);
        String email = preferences.getString("User", "");

        dao = new DAO_KhachHang(getContext());
        list = dao.getUser(email);
        kh = list.get(0);

        makh = kh.getMakh();
        reload();

        if (list_lsgd.isEmpty()) {
            tv_no_result.setText("Hmm...Bạn chưa có giao dịch nào !!!  ");
            img.setImageResource(R.drawable.img_sad);
        } else {
            tv_no_result.setText("");
            img.setImageDrawable(null);
        }

        String sodustr = String.valueOf(kh.getSodu());
        StringBuilder str = new StringBuilder(sodustr);
        for (int i = str.length(); i > 0; i -= 3) {
            str.insert(i, " ");
        }

        tvsodu.setText(str);

        btn_naptien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kh.getTenkh() == null || kh.getSdt() == null || kh.getDiachi() == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View custom = inflater.inflate(R.layout.layout_dialog_notice_info, null);

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setView(custom);
                            builder.setPositiveButton("OKEY", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    dialog1.dismiss();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    };

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(runnable, 500);
                    return;
                } else {
                    startActivity(new Intent(getActivity(), Activity_Naptien.class));
                    SharedPreferences preferences1 = getActivity().getSharedPreferences("INFO_USER_BANKING", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();;
                    editor.putInt("makh", makh);
                    editor.commit();

                    getActivity().overridePendingTransition(R.anim.animation1, R.anim.animation2);
                }
            }
        });
    }

    public void reload() {
        dao_lsgd = new DAO_LSGD(getContext());
        list_lsgd = dao_lsgd.getAllLSGD(makh);
        adapter = new Adapter_Recycler_LSGD(list_lsgd, getContext());
        recyclerView.setAdapter(adapter);
    }
}