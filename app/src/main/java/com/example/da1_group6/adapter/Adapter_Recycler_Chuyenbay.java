package com.example.da1_group6.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_ChuyenBay;
import com.example.da1_group6.model.ChuyenBay;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_Recycler_Chuyenbay extends RecyclerView.Adapter<Adapter_Recycler_Chuyenbay.ViewHolder> {
    ArrayList<ChuyenBay> list;
    Context context;
    DAO_ChuyenBay dao;

    Spinner spin_from, spin_to, spin_hmb;
    EditText edt_timebay, edt_tongtime, edt_giave, edt_slve, edt_macb;

    ArrayList<HashMap<String, Object>> listHM;
    int indexfrom, indexto;

    public Adapter_Recycler_Chuyenbay(ArrayList<ChuyenBay> list, Context context, ArrayList<HashMap<String, Object>> listHM, DAO_ChuyenBay dao) {
        this.list = list;
        this.context = context;
        this.listHM = listHM;
        dao = new DAO_ChuyenBay(context);
    }

    @NonNull
    @Override
    public Adapter_Recycler_Chuyenbay.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_chuyenbay_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Recycler_Chuyenbay.ViewHolder holder, int position) {
        ChuyenBay cb = list.get(position);
        dao = new DAO_ChuyenBay(context);

        if (cb.getMamb().equalsIgnoreCase("VNA")) {
            holder.img.setImageResource(R.drawable.logo_vnairlines);
        } else if (cb.getMamb().equalsIgnoreCase("VJA")) {
            holder.img.setImageResource(R.drawable.logo_vietjet);
        } else if (cb.getMamb().equalsIgnoreCase("BBA")) {
            holder.img.setImageResource(R.drawable.logo_bamboo);
        }

        holder.tvmacb.setText("Mã chuyến bay: " + cb.getMacb());
        holder.tvfrom.setText("From: " + cb.getDiemdi());
        holder.tvto.setText("To: " + cb.getDiemden());
        holder.tvtimebay.setText(cb.getTimebay());
        holder.tvtongtime.setText(cb.getTongtime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_select_update_or_delete);
                dialog.show();

                LinearLayout select_upd, select_del;
                select_upd = dialog.findViewById(R.id.select_upd);
                select_del = dialog.findViewById(R.id.select_del);

                select_upd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select_update(cb);
                        dialog.dismiss();
                    }
                });

                select_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        select_delete(cb.getMacb());
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    public void select_update(ChuyenBay cb) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_update_chuyenbay);
        dialog.show();

        spin_from = dialog.findViewById(R.id.spin_from_in_dialog_suachuyenbay);
        spin_to = dialog.findViewById(R.id.spin_to_in_dialog_suachuyenbay);
        spin_hmb = dialog.findViewById(R.id.spin_hmb_in_dialog_suachuyenbay);
        edt_giave = dialog.findViewById(R.id.edt_giave_in_dialog_suachuyenbay);
        edt_slve = dialog.findViewById(R.id.edt_slve_in_dialog_suachuyenbay);
        edt_timebay = dialog.findViewById(R.id.edt_timebay_in_dialog_suachuyenbay);
        edt_tongtime = dialog.findViewById(R.id.edt_tongtime_in_dialog_suachuyenbay);
        edt_macb = dialog.findViewById(R.id.edt_macb_in_dialog_suachuyenbay);

        SimpleAdapter simpleAdapter1 = new SimpleAdapter(context, listHM,
                android.R.layout.simple_list_item_1, new String[]{"tenmb"}, new int[]{android.R.id.text1});
        spin_hmb.setAdapter(simpleAdapter1);

        int index = 0;
        int posi = -1;
        for (HashMap<String, Object> item : listHM) {
            if (item.get("mamb").equals(cb.getMamb())) {
                posi = index;
            }
            index++;
        }
        spin_hmb.setSelection(posi);

        int posi1 = posi;

        edt_macb.setText(cb.getMacb());
        edt_timebay.setText(cb.getTimebay());
        edt_tongtime.setText(cb.getTongtime());
        edt_slve.setText(String.valueOf(cb.getSoluongve()));
        edt_giave.setText(String.valueOf(cb.getGiave()));

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel_in_dialog_suachuyenbay);
        Button btn_upd = dialog.findViewById(R.id.btn_update_in_dialog_suachuyenbay);

        String[] list = {"Hà Nội", "Hải Phòng", "Đà Nẵng", "Nha Trang", "TP. HCM", "Phú Quốc"};
        ArrayAdapter adapter_from = new ArrayAdapter(context, android.R.layout.simple_list_item_1, list);
        spin_from.setAdapter(adapter_from);
        spin_to.setAdapter(adapter_from);

        getIndexFrom(cb);
        getIndexTo(cb);

        spin_from.setSelection(indexfrom);
        spin_to.setSelection(indexto);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin_hmb.getSelectedItemPosition() != posi1) {
                    Toast.makeText(context, "Không được thay đổi hãng máy bay! Nếu muốn thay, vui lòng xoá item này và tạo mới item khác !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (check()) {
                        boolean result = dao.updCB(new ChuyenBay(cb.getMacb(), spin_from.getSelectedItem().toString(), spin_to.getSelectedItem().toString(),
                                Integer.parseInt(edt_giave.getText().toString().trim()), edt_timebay.getText().toString(), edt_tongtime.getText().toString(),
                                Integer.parseInt(edt_slve.getText().toString().trim()), cb.getMamb()));

                        if (result == true) {
                            Toast.makeText(context, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                            reload();
                        } else {
                            Toast.makeText(context, "Sửa thất bại!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                }
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void select_delete(String macb) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete_item);
        dialog.show();

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel_in_dialog_xoachuyenbay);
        Button btn_delete = dialog.findViewById(R.id.btn_delete_in_dialog_xoachuyenbay);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = dao.delCB(macb);
                if (result == true) {
                    Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                    reload();
                } else {
                    Toast.makeText(context, "Xoá thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvmacb, tvfrom, tvto, tvtimebay, tvtongtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.logo_mb_chuyenbay);
            tvmacb = itemView.findViewById(R.id.tv_macb);
            tvfrom = itemView.findViewById(R.id.tv_from_it1_cb_admin);
            tvto = itemView.findViewById(R.id.tv_to_it1_cb_admin);
            tvtimebay = itemView.findViewById(R.id.tv_timebay);
            tvtongtime = itemView.findViewById(R.id.tv_tongtime_cb_admin);
        }
    }

    void reload() {
        list.clear();
        list = dao.getAll();
        notifyDataSetChanged();
    }

    boolean check() {
        if (edt_giave.getText().toString().equals("") || edt_slve.getText().toString().equals("") ||
                edt_timebay.getText().toString().equals("") || edt_tongtime.getText().toString().equals("")) {
            Toast.makeText(context, "Điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spin_from.getSelectedItemPosition() == spin_to.getSelectedItemPosition()) {
            Toast.makeText(context, "Điểm đi và điểm đến không được trùng nhau!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void getIndexFrom(ChuyenBay cb) {
        if (cb.getDiemdi().equals("Hà Nội")) {
            indexfrom = 0;
        } else if (cb.getDiemdi().equals("Hải Phòng")) {
            indexfrom = 1;
        } else if (cb.getDiemdi().equals("Đà Nẵng")) {
            indexfrom = 2;
        } else if (cb.getDiemdi().equals("Nha Trang")) {
            indexfrom = 3;
        } else if (cb.getDiemdi().equals("TP. HCM")) {
            indexfrom = 4;
        } else {
            indexfrom = 5;
        }
    }

    void getIndexTo(ChuyenBay cb) {
        if (cb.getDiemden().equals("Hà Nội")) {
            indexto = 0;
        } else if (cb.getDiemden().equals("Hải Phòng")) {
            indexto = 1;
        } else if (cb.getDiemden().equals("Đà Nẵng")) {
            indexto = 2;
        } else if (cb.getDiemden().equals("Nha Trang")) {
            indexto = 3;
        } else if (cb.getDiemden().equals("TP. HCM")) {
            indexto = 4;
        } else {
            indexto = 5;
        }
    }
}
