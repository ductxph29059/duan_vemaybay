package com.example.da1_group6.ui_admin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.dao.DAO_VeMB;
import com.example.da1_group6.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Fragment_Doanhthu_admin extends Fragment {
    TextView tv_datefrom, tv_dateto, tvtongdoanhthu, tvtongveban, tvtd1, tvtd2, tvall3staff1, tvall3staff2;
    ImageView imgdatefrom, imgdateto;
    Spinner spinner;
    ImageView img_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanhthu_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_datefrom = view.findViewById(R.id.tv_datefrom_doanhthu_admin);
        tv_dateto = view.findViewById(R.id.tv_dateto_doanhthu_admin);
        imgdatefrom = view.findViewById(R.id.img_date_from_doanhthu_admin);
        imgdateto = view.findViewById(R.id.img_date_to_doanhthu_admin);
        spinner = view.findViewById(R.id.spinner_nv_in_doanhthu_admin);

        tvtongdoanhthu = view.findViewById(R.id.tv_tongdoanhthu_admin);
        tvtongveban = view.findViewById(R.id.tv_tongveban_admin);
        tvtd1 = view.findViewById(R.id.tv_trongdo1);
        tvtd2 = view.findViewById(R.id.tv_trongdo2);
        tvall3staff1 = view.findViewById(R.id.tv_all3staff_1);
        tvall3staff2 = view.findViewById(R.id.tv_all3staff_2);
        img_search = view.findViewById(R.id.ic_search_doanhthu_admin);

        imgdatefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_from();
            }
        });


        imgdateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_to();
            }
        });

        getDataHangMB(spinner);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) spinner.getSelectedItem();
                String manv = (String) hashMap.get("manv");

                DAO_VeMB dao = new DAO_VeMB(getContext());
                String date_from = tv_datefrom.getText().toString();
                String date_to = tv_dateto.getText().toString();

                String doanhthustr_staff = String.valueOf(dao.get_tongdoanhthu_staff(manv, date_from, date_to, 1));
                StringBuilder str_dt_staff = new StringBuilder(doanhthustr_staff);
                for (int i = str_dt_staff.length(); i > 0; i -= 3) {
                    str_dt_staff.insert(i, " ");
                }

                String slvestr_staff = String.valueOf(dao.get_tongveban_staff(manv, date_from, date_to, 1));
                StringBuilder str_slve_staff = new StringBuilder(slvestr_staff);
                for (int i = str_slve_staff.length(); i > 0; i -= 3) {
                    str_slve_staff.insert(i, " ");
                }

                String doanhthustr_all = String.valueOf(dao.get_tongdoanhthu(date_from, date_to, 1));
                StringBuilder str_dt_all = new StringBuilder(doanhthustr_all);
                for (int i = str_dt_all.length(); i > 0; i -= 3) {
                    str_dt_all.insert(i, " ");
                }

                String doanhthustr_staff1 = String.valueOf(dao.get_tongdoanhthu_staff("NV01", date_from, date_to, 1));
                StringBuilder str_dt_staff1 = new StringBuilder(doanhthustr_staff1);
                for (int i = str_dt_staff1.length(); i > 0; i -= 3) {
                    str_dt_staff1.insert(i, " ");
                }

                String doanhthustr_staff2 = String.valueOf(dao.get_tongdoanhthu_staff("NV02", date_from, date_to, 1));
                StringBuilder str_dt_staff2 = new StringBuilder(doanhthustr_staff2);
                for (int i = str_dt_staff2.length(); i > 0; i -= 3) {
                    str_dt_staff2.insert(i, " ");
                }

                String doanhthustr_staff3 = String.valueOf(dao.get_tongdoanhthu_staff("NV03", date_from, date_to, 1));
                StringBuilder str_dt_staff3 = new StringBuilder(doanhthustr_staff3);
                for (int i = str_dt_staff3.length(); i > 0; i -= 3) {
                    str_dt_staff3.insert(i, " ");
                }


                int soluongve = dao.get_tongveban(date_from, date_to, 1);

                if (manv.equalsIgnoreCase("Tất cả")) {
                    tvtongdoanhthu.setText(str_dt_all + " vnđ");
                    tvtd1.setText("Trong đó: ");
                    tvall3staff1.setText("- NV01: " + str_dt_staff1 + " vnđ" + "\n" +
                            "- NV02: " + str_dt_staff2 + " vnđ" + "\n"
                            + "- NV03: " + str_dt_staff3 + " vnđ" + "\n");

                    tvtongveban.setText(soluongve + " vé được bán ra");
                    tvtd2.setText("Trong đó: ");
                    tvall3staff2.setText("- NV01: " + dao.get_tongveban_staff("NV01", date_from, date_to, 1) + " vé" + "\n" +
                            "- NV02: " + dao.get_tongveban_staff("NV02", date_from, date_to, 1) + " vé" + "\n"
                            + "- NV03: " + dao.get_tongveban_staff("NV03", date_from, date_to, 1) + " vé" + "\n");
                } else {
                    tvtongdoanhthu.setText(str_dt_staff + " vnđ");
                    tvtongveban.setText( str_slve_staff + " vé được bán ra");
                    tvtd1.setText("");
                    tvtd2.setText("");
                    tvall3staff1.setText("");
                    tvall3staff2.setText("");
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
                        tv_datefrom.setText(format.format(calendar.getTime()));
                        if (!tv_datefrom.getText().toString().equals("")) {
                            //    show();
                        }
                        if (tv_datefrom.getText().toString().compareTo(tv_dateto.getText().toString()) > 0 && !tv_dateto.getText().toString().equals("")) {
                            Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc", Toast.LENGTH_SHORT).show();
                            //    tvTien.setText("");
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        dialog.show();
    }

    public void date_to() {
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
                        tv_dateto.setText(format.format(calendar.getTime()));
                        if (!tv_datefrom.getText().toString().equals("")) {
                            //    show();
                        }
                        if (tv_dateto.getText().toString().compareTo(tv_datefrom.getText().toString()) < 0) {
                            Toast.makeText(getContext(), "Ngày kết thúc phải lớn hơn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                            //    tvTien.setText("");
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        dialog.show();
    }

    public void getDataHangMB(Spinner spin_) {
        DAO_QLNV dao = new DAO_QLNV(getContext());
        ArrayList<NhanVien> list = dao.getAll();
        list.add(0, new NhanVien("Tất cả", "Tất cả"));
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (NhanVien nv : list) {
            HashMap<String, Object> hm = new HashMap<>();
            hm.put("manv", nv.getManv());
            listHM.add(hm);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM,
                android.R.layout.simple_list_item_1, new String[]{"manv"}, new int[]{android.R.id.text1});
        spin_.setAdapter(simpleAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        tv_datefrom.setText("Từ ngày");
        tv_dateto.setText("Đến ngày");
    }
}