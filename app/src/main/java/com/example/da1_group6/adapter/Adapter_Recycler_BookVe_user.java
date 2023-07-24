package com.example.da1_group6.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_ChuyenBay;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.dao.DAO_LSGD;
import com.example.da1_group6.dao.DAO_VeMB;
import com.example.da1_group6.model.ChuyenBay;
import com.example.da1_group6.model.KhachHang;
import com.example.da1_group6.model.LSGD;
import com.example.da1_group6.model.VeMB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Adapter_Recycler_BookVe_user extends RecyclerView.Adapter<Adapter_Recycler_BookVe_user.ViewHolder> {
    ArrayList<ChuyenBay> list;
    Context context;
    int giave = 0;
    String mamb, macb, manv;
    int makh;
    ArrayList<KhachHang> list_kh;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.layout_item1_bookve, parent, false));
    }

    public Adapter_Recycler_BookVe_user(ArrayList<ChuyenBay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuyenBay cb = list.get(position);

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

        holder.layout_hint.setVisibility(View.GONE);
        holder.tvslvebook.setText("1");

        holder.imgdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout_hint.setVisibility(View.VISIBLE);
                holder.imgdown.setImageResource(R.drawable.ic_down);

                int sl = reloadSLve(cb.getMacb());

                holder.tvslve.setText("Còn lại: " + sl + " vé");


                holder.imgdown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.imgdown.setImageResource(R.drawable.ic_up);
                        holder.layout_hint.setVisibility(View.GONE);
                    }
                });
            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.tvslvebook.getText().toString()) > 1) {
                    int sl = Integer.parseInt(holder.tvslvebook.getText().toString()) - 1;
                    holder.tvslvebook.setText(String.valueOf(sl));
                } else {
                    return;
                }
            }
        });

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(holder.tvslvebook.getText().toString()) + 1;
                holder.tvslvebook.setText(String.valueOf(sl));
            }
        });

        String giavestr = String.valueOf(cb.getGiave());
        StringBuilder str_giave = new StringBuilder(giavestr);
        for (int i = str_giave.length(); i > 0; i -= 3) {
            str_giave.insert(i, " ");
        }

        holder.tvgiave.setText(str_giave + " Đ");


        SharedPreferences preferences = context.getSharedPreferences("TB", Context.MODE_PRIVATE);
        String email = preferences.getString("User", "");
        DAO_KhachHang dao_user = new DAO_KhachHang(context);
        list_kh = dao_user.getUser(email);
        KhachHang kh = list_kh.get(0);

        holder.btn_bookve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                macb = cb.getMacb();
                mamb = cb.getMamb();
                makh = kh.getMakh();

                if (mamb.equals("VNA")) {
                    manv = "NV01";
                } else if (mamb.equals("VJA")) {
                    manv = "NV02";
                } else if (mamb.equals("BBA")) {
                    manv = "NV03";
                }
                giave = cb.getGiave() * Integer.parseInt(holder.tvslvebook.getText().toString());

                String giave_string = String.valueOf(giave);
                StringBuilder str_giave1 = new StringBuilder(giave_string);
                for (int i = str_giave1.length(); i > 0; i -= 3) {
                    str_giave1.insert(i, " ");
                }

                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirm_bookve);
                dialog.show();

                TextView tv_confirm = dialog.findViewById(R.id.tv_notice_confirm_bookve);
                TextView btn_cancel = dialog.findViewById(R.id.btnCancel_confirm_bookve);
                TextView btn_confirm = dialog.findViewById(R.id.btn_confirm_bookve);

                tv_confirm.setText("Xác nhận thanh toán " + holder.tvslvebook.getText().toString() + " vé với tổng số tiền là " + str_giave1 + " Đ?");
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (kh.getTenkh() == null || kh.getSdt() == null || kh.getDiachi() == null) {
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    LayoutInflater inflater = LayoutInflater.from(context);
                                    View custom = inflater.inflate(R.layout.layout_dialog_notice_info, null);

                                    ImageView img1 = custom.findViewById(R.id.gif_sad_info);
                                    Glide.with(context).load(R.mipmap.gif_sad).into(img1);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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


                            dialog.dismiss();
                            return;

                        } else {
                            DAO_VeMB dao_vmb = new DAO_VeMB(context);
                            VeMB vmb = new VeMB();
                            list_kh = new ArrayList<>();
                            list_kh = dao_user.getUser(email);
                            KhachHang kh = list_kh.get(0);
                            int sodu = kh.getSodu();
                            if (sodu < giave) {
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        View custom = inflater.inflate(R.layout.layout_dialog_notice_hettien, null);

                                        ImageView img1 = custom.findViewById(R.id.gif_sad_hettien);
                                        Glide.with(context).load(R.mipmap.gif_sad).into(img1);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

                                dialog.dismiss();
                                return;
                            } else {
                                if (cb.getSoluongve() <= 0) {
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            LayoutInflater inflater = LayoutInflater.from(context);
                                            View custom = inflater.inflate(R.layout.layout_dialog_hetve, null);

                                            ImageView img1 = custom.findViewById(R.id.gif_sad_hetve);
                                            Glide.with(context).load(R.mipmap.gif_sad).into(img1);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

                                    holder.btn_bookve.setText("Hết vé");
                                    holder.btn_bookve.setEnabled(false);
                                    holder.btn_bookve.setTextColor(Color.WHITE);
                                    dialog.dismiss();
                                    return;
                                } else {
                                    if (Integer.parseInt(holder.tvslvebook.getText().toString()) > cb.getSoluongve()) {
                                        LayoutInflater inflater = LayoutInflater.from(context);
                                        View custom = inflater.inflate(R.layout.layout_dialog_notice_slve, null);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setView(custom);
                                        builder.setPositiveButton("OKEY", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog1, int which) {
                                                dialog1.dismiss();
                                            }
                                        });

                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                        dialog.dismiss();
                                        return;
                                    } else {
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                        String date = format.format(Calendar.getInstance().getTime());

                                        holder.btn_bookve.setText("Đã đặt vé");
                                        holder.btn_bookve.setEnabled(false);
                                        holder.btn_bookve.setTextColor(Color.WHITE);

                                        Toast.makeText(context, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();

                                        int in = Integer.parseInt(holder.tvslvebook.getText().toString());
                                        for (int i = 0; i < in; i++) {
                                            dao_vmb.addVMB(new VeMB(vmb.getMavmb(), mamb, macb, manv, makh, date, i - i));
                                        }

                                        int sodu_after = sodu - giave;
                                        dao_user.update_Tien(new KhachHang(kh.getMakh(), kh.getTenkh(), kh.getNgaysinh(), kh.getEmail(), kh.getSdt(), kh.getCccd(), kh.getGioitinh(),
                                                kh.getDiachi(), kh.getQuoctich(), kh.getMatkhau(), kh.getImage(), sodu_after));

                                        DAO_LSGD dao_lsgd = new DAO_LSGD(context);
                                        LSGD ls = new LSGD();
                                        dao_lsgd.addLS(new LSGD(ls.getId(), kh.getMakh(), "Mua vé máy bay", giave, date));

                                        int soluongve_now = reloadSLve(cb.getMacb()) - Integer.parseInt(holder.tvslvebook.getText().toString());

                                        DAO_ChuyenBay dao_chuyenBay = new DAO_ChuyenBay(context);
                                        dao_chuyenBay.updateSLVMB(new ChuyenBay(cb.getMacb(), cb.getDiemdi(), cb.getDiemden(), cb.getGiave(), cb.getTimebay(), cb.getTongtime(), soluongve_now, cb.getMamb()));

                                        holder.tvslve.setText("Còn lại: " + soluongve_now + " vé");

                                        SharedPreferences preferences1 = context.getSharedPreferences("NOTI_STAFF", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences1.edit();
                                        editor.putString("noti_manv", manv);
                                        editor.putBoolean("noti_check", true);
                                        editor.commit();

                                        dialog.dismiss();
                                    }
                                }
                            }
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgdown, btn_minus, btn_add;
        TextView tvmacb, tvfrom, tvto, tvtimebay, tvgiave, tvslve, tvslvebook;
        Button btn_bookve;
        LinearLayout layout_hint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.logo_mb_it1_bookve);
            tvmacb = itemView.findViewById(R.id.tv_macb_bookve);
            tvfrom = itemView.findViewById(R.id.tv_from_bookve);
            tvto = itemView.findViewById(R.id.tv_to_bookve);
            tvtimebay = itemView.findViewById(R.id.tv_timebay_bookve);
            btn_bookve = itemView.findViewById(R.id.btn_datve);
            tvgiave = itemView.findViewById(R.id.tv_giave_bookve);
            tvslve = itemView.findViewById(R.id.tv_soluongve_bookve);
            imgdown = itemView.findViewById(R.id.btn_down);
            tvslvebook = itemView.findViewById(R.id.tv_slve_book);
            btn_add = itemView.findViewById(R.id.btn_add_slve);
            btn_minus = itemView.findViewById(R.id.btn_minus_slve);
            layout_hint = itemView.findViewById(R.id.layout_hint_inbook);

        }
    }

    int reloadSLve(String macbstr) {
        DAO_ChuyenBay dao = new DAO_ChuyenBay(context);
        return dao.getSLve(macbstr);
    }
}
