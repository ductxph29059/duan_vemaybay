package com.example.da1_group6.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_group6.R;
import com.example.da1_group6.model.VeMB;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Recycler_qlvmb_staff extends RecyclerView.Adapter<Adapter_Recycler_qlvmb_staff.ViewHolder> {
    ArrayList<VeMB> list;
    Context context;

    public Adapter_Recycler_qlvmb_staff(ArrayList<VeMB> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setListSearch(ArrayList<VeMB> getListSearch) {
        this.list = getListSearch;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_qlvmb_staff, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VeMB vmb = list.get(position);

        if(vmb.getMamb().equalsIgnoreCase("VNA")) {
            holder.img.setImageResource(R.drawable.logo_vnairlines);
        } else if (vmb.getMamb().equalsIgnoreCase("VJA")) {
            holder.img.setImageResource(R.drawable.logo_vietjet);
        } else if (vmb.getMamb().equalsIgnoreCase("BBA")) {
            holder.img.setImageResource(R.drawable.logo_bamboo);
        }

        holder.tvmacb.setText("Mã CB: " + vmb.getMacb());
        holder.tvfrom.setText("From: " + vmb.getDiemdi());
        holder.tvto.setText("To: " + vmb.getDiemden());
        holder.tvtimebay.setText(vmb.getTimebay());
        holder.tvtenkh.setText("Họ tên: " + vmb.getTenkh());
        holder.tvtongtime.setText(vmb.getTongtime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.layout_dialog_showinfo_vmbdxn_staff, null);

                ImageView logo_mb = view.findViewById(R.id.logo_mb_info_staff_dxn);
                TextView tv_timedatve = view.findViewById(R.id.tv_timedatve_info_staff_dxn);
                TextView tv_mavmb = view.findViewById(R.id.tv_mavmb_info_staff_dxn);
                TextView tvmacb = view.findViewById(R.id.tv_macb_info_staff_dxn);
                TextView tvfrom = view.findViewById(R.id.tv_from_info_staff_dxn);
                TextView tvto = view.findViewById(R.id.tv_to_info_staff_dxn);
                TextView tvngaybay = view.findViewById(R.id.tv_ngaybay_info_staff_dxn);
                TextView tvtongtime = view.findViewById(R.id.tv_tongtime_info_staff_dxn);
                CircleImageView avt_user = view.findViewById(R.id.avatar_user_info_staff_dxn);
                TextView tvtenkh = view.findViewById(R.id.tv_tenkh_info_staff_dxn);
                TextView tvsex_user = view.findViewById(R.id.tv_sex_user_info_staff_dxn);
                TextView tvemail_user = view.findViewById(R.id.tv_email_user_info_staff_dxn);
                TextView tvsdt_user = view.findViewById(R.id.tv_sdt_user_info_staff_dxn);
                TextView tvdiachi = view.findViewById(R.id.tv_diachi_user_info_staff_dxn);
                TextView tvstatus = view.findViewById(R.id.tv_status_staff_dxn);
                ImageView icstatus = view.findViewById(R.id.ic_status_staff_dxn);


                if (vmb.getMamb().equalsIgnoreCase("VNA")) {
                    logo_mb.setImageResource(R.drawable.logo_vnairlines);
                } else if (vmb.getMamb().equalsIgnoreCase("VJA")) {
                    logo_mb.setImageResource(R.drawable.logo_vietjet);
                } else if (vmb.getMamb().equalsIgnoreCase("BBA")) {
                    logo_mb.setImageResource(R.drawable.logo_bamboo);
                }

                tv_timedatve.setText(vmb.getTimedatve());
                tv_mavmb.setText("Mã VMB: " + vmb.getMavmb());
                tvmacb.setText("Mã chuyến bay: " + vmb.getMacb());
                tvfrom.setText(vmb.getDiemdi());
                tvto.setText(vmb.getDiemden());
                tvngaybay.setText(vmb.getTimebay());
                tvtongtime.setText(vmb.getTongtime());

                avt_user.setImageBitmap(vmb.getImage_user());

                tvtenkh.setText(vmb.getTenkh());

                int gioitinh = vmb.getSex_user();
                if(gioitinh == 0) {
                    tvsex_user.setText("GT: Nam");
                } else if(gioitinh == 1) {
                    tvsex_user.setText("GT: Nữ");
                } else {
                    tvsex_user.setText("GT: Khác");
                }

                tvemail_user.setText("Email: " + vmb.getEmail_user());
                tvsdt_user.setText("SĐT: " + vmb.getSdt_user());
                tvdiachi.setText("Địa chỉ: " + vmb.getDiachi_user());


                int status = vmb.getTrangthai();

                if (status == 0) {
                    icstatus.setImageResource(R.drawable.ic_wait);
                    tvstatus.setText("Chưa xác nhận!");
                } else if (status == 1){
                    icstatus.setImageResource(R.drawable.ic_tick);
                    tvstatus.setText("Đã xác nhận!");
                } else {
                    icstatus.setImageResource(R.drawable.ic_close);
                    tvstatus.setText("Đã từ chối!");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(view);
                builder.setPositiveButton("OKEY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
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
        ImageView img;
        TextView tvmacb, tvfrom, tvto, tvtimebay, tvtenkh, tvtongtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.logo_mb_qlvmb_staff);
            tvmacb = itemView.findViewById(R.id.tv_macb_qlvmb_staff);
            tvfrom = itemView.findViewById(R.id.tv_from_qlvmb_staff);
            tvto = itemView.findViewById(R.id.tv_to_qlvmb_staff);
            tvtimebay = itemView.findViewById(R.id.tv_timebay_qlvmb_staff);
            tvtenkh = itemView.findViewById(R.id.tv_hoten_qlvmb_staff);
            tvtongtime = itemView.findViewById(R.id.tv_tongtime_qlvmb_staff);
        }
    }
}
