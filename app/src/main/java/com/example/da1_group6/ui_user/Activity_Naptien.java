package com.example.da1_group6.ui_user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_HoaDonNapTien;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.model.HoaDonNapTien;
import com.example.da1_group6.model.KhachHang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_Naptien extends AppCompatActivity {
    EditText edt_sotiennap;
    ImageView ic_copy_tentk, ic_copy_stk, ic_copy_ndck, ic_copy_sotiennap;
    Button btn_continue;
    TextView tv_ndck, tvtenchutk, tvstk;
    int makh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_naptien);

        edt_sotiennap = findViewById(R.id.edt_sotiennap);
        ic_copy_sotiennap = findViewById(R.id.ic_copy_sotiennap);
        ic_copy_tentk = findViewById(R.id.ic_copy_tenchutk);
        ic_copy_stk = findViewById(R.id.ic_copy_stk);
        ic_copy_ndck = findViewById(R.id.ic_copy_ndck);
        btn_continue = findViewById(R.id.btn_continue_banking);
        tv_ndck = findViewById(R.id.tv_noidung_ck);
        tvstk = findViewById(R.id.tv_stk_chutk);
        tvtenchutk = findViewById(R.id.tv_ten_chutk);

        SharedPreferences preferences = getSharedPreferences("INFO_USER_BANKING", Context.MODE_PRIVATE);
        makh = preferences.getInt("makh", 0);
        DAO_KhachHang dao_kh = new DAO_KhachHang(this);
        ArrayList<KhachHang> list = dao_kh.getUser_ID(makh);
        KhachHang kh = list.get(0);

        tv_ndck.setText("KH" + kh.getMakh() + " " + kh.getTenkh() + " nap tien");
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                naptien();
                onBackPressed();
            }
        });

        ic_copy_sotiennap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sotien = edt_sotiennap.getText().toString().trim();
                if (sotien.isEmpty()) {
                    Toast.makeText(Activity_Naptien.this, "Vui lòng nhập số tiền cần nạp !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("data", sotien);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(Activity_Naptien.this, "Đã sao chép số tiền vào clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ic_copy_tentk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenchutk = tvtenchutk.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("data", tenchutk);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Activity_Naptien.this, "Đã sao chép tên chủ TK vào clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        ic_copy_stk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stk = tvstk.getText().toString().trim();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("data", stk);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Activity_Naptien.this, "Đã sao chép số tài khoản vào clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        ic_copy_ndck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ndck = tv_ndck.getText().toString();
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("data", ndck);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(Activity_Naptien.this, "Đã sao chép nội dung vào clipboard", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void naptien() {
        if (edt_sotiennap.getText().toString().equals("")) {
            Toast.makeText(this, "Vui lòng nhập số tiền cần nạp !", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (Integer.parseInt(edt_sotiennap.getText().toString()) > 100000000) {
                Toast.makeText(this, "Bạn chỉ được nạp tối đa 100 triệu / 1 lần. Vui lòng thử lại !!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String time = format.format(Calendar.getInstance().getTime());

                startActivity(new Intent(Activity_Naptien.this, Activity_UpBill.class));

                SharedPreferences preferences1 = getSharedPreferences("INFO_USER_BANKING", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences1.edit();
                ;
                editor.putInt("makh", makh);
                editor.putString("time", time);
                editor.putInt("sotiennap", Integer.parseInt(edt_sotiennap.getText().toString().trim()));
                editor.commit();

                overridePendingTransition(R.anim.animation1, R.anim.animation2);
            }
        }

    }
}