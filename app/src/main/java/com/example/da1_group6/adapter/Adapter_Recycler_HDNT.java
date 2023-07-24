package com.example.da1_group6.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_HoaDonNapTien;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.dao.DAO_LSGD;
import com.example.da1_group6.model.HoaDonNapTien;
import com.example.da1_group6.model.KhachHang;
import com.example.da1_group6.model.LSGD;

import java.util.ArrayList;

public class Adapter_Recycler_HDNT extends RecyclerView.Adapter<Adapter_Recycler_HDNT.ViewHolder> {
    ArrayList<HoaDonNapTien> list;
    Context context;
    DAO_HoaDonNapTien dao;

    public Adapter_Recycler_HDNT(ArrayList<HoaDonNapTien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_hoadonnaptien, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonNapTien hd = list.get(position);

        holder.tvmakh.setText("Mã KH: " + String.valueOf(hd.getMakh()));
        holder.tvtenkh.setText(hd.getHotenkh());

        String sotiennapstr = String.valueOf(hd.getSotiennap());
        StringBuilder str = new StringBuilder(sotiennapstr);
        for (int i = str.length(); i > 0; i -= 3) {
            str.insert(i, " ");
        }

        holder.tvsotiennap.setText(str + " Đ");
        holder.tvtime_naptien.setText("Thời gian nạp tiền: " + hd.getTimenap());

        int status = hd.getTrangthai();
        int makh = hd.getMakh();

        holder.img_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = new DAO_HoaDonNapTien(v.getContext());
                dao.updHD(new HoaDonNapTien(hd.getId(), hd.getMakh(), hd.getSotiennap(), hd.getTimenap(), 1, hd.getBill()));
                reload();
                Toast.makeText(context, "Xác nhận nạp tiền thành công!", Toast.LENGTH_SHORT).show();
                DAO_KhachHang dao_khachHang = new DAO_KhachHang(context);
                ArrayList<KhachHang> list = dao_khachHang.getUser_ID(makh);
                KhachHang kh = list.get(0);

                int sodu_before = kh.getSodu();
                int sodu_after = sodu_before + hd.getSotiennap();

                dao_khachHang.update_Tien(new KhachHang(kh.getMakh(), kh.getTenkh(), kh.getNgaysinh(), kh.getEmail(), kh.getSdt(), kh.getCccd(), kh.getGioitinh(), kh.getDiachi(),
                        kh.getQuoctich(), kh.getMatkhau(), kh.getImage(), sodu_after));

                DAO_LSGD dao_lsgd = new DAO_LSGD(context);
                LSGD ls = new LSGD();
                dao_lsgd.addLS(new LSGD(ls.getId(), makh, "Nạp tiền", hd.getSotiennap(), hd.getTimenap()));

                SharedPreferences preferences = context.getSharedPreferences("NOTI_NAPTIEN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("makh", String.valueOf(kh));
                editor.putBoolean("check", true);
                editor.commit();
            }
        });

        if (status == 0) {
            holder.img_status.setImageResource(R.drawable.ic_wait);
        } else {
            holder.img_status.setImageResource(R.drawable.ic_tick);
            holder.img_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Đã xác nhận!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.layout_billck_admin, null);

                ImageView imageView = view.findViewById(R.id.img_show_bill_of_user);
                imageView.setImageBitmap(hd.getBill());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvmakh, tvtenkh, tvsotiennap, tvtime_naptien;
        ImageView img_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmakh = itemView.findViewById(R.id.tvmakh_naptien);
            tvtenkh = itemView.findViewById(R.id.tvtenkh_naptien);
            tvsotiennap = itemView.findViewById(R.id.tv_sotiennap);
            tvtime_naptien = itemView.findViewById(R.id.tv_timenaptien);
            img_status = itemView.findViewById(R.id.ic_status_naptien);

        }
    }

    void reload() {
        list.clear();
        list = dao.getAllHoaDon();
        notifyDataSetChanged();
    }
}
