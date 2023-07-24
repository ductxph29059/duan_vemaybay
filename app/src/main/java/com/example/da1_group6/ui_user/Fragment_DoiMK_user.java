package com.example.da1_group6.ui_user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.dao.DAO_QLNV;

public class Fragment_DoiMK_user extends Fragment {
    ImageView showPass1, showPass2, showPass3;
    EditText edt_oldpass, edt_newpass, edt_retype_newpass;
    Button btnChange;
    DAO_KhachHang dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doi_mk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showPass1 = view.findViewById(R.id.showPass1);
        showPass2 = view.findViewById(R.id.showPass2);
        showPass3 = view.findViewById(R.id.showPass3);
        edt_oldpass = view.findViewById(R.id.edt_oldpass);
        edt_newpass = view.findViewById(R.id.edt_newpass);
        edt_retype_newpass = view.findViewById(R.id.edt_retype_newpass);
        btnChange = view.findViewById(R.id.btndoimatkhau);

        edt_oldpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edt_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edt_retype_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        // show/ hint old password
        showPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.showPass1){
                    if(edt_oldpass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility_off);
                        //Show Password
                        edt_oldpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility);
                        //Hide Password
                        edt_oldpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });

        // show / hint new password
        showPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.showPass2){
                    if(edt_newpass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility_off);
                        //Show Password
                        edt_newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility);
                        //Hide Password
                        edt_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });

        // show / hint retype pass
        showPass3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.showPass3){
                    if(edt_retype_newpass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility_off);
                        //Show Password
                        edt_retype_newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(v)).setImageResource(R.drawable.ic_visibility);
                        //Hide Password
                        edt_retype_newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    if (edt_newpass.getText().toString().equals(edt_retype_newpass.getText().toString())) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("TB", Context.MODE_PRIVATE);
                        String user = preferences.getString("User", "");
                        String pass = preferences.getString("Pass", "");
                        dao = new DAO_KhachHang(getContext());

                        if (dao.updatePW(user, edt_oldpass.getText().toString(), edt_newpass.getText().toString())) {
                            Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                            edt_oldpass.setText("");
                            edt_newpass.setText("");
                            edt_retype_newpass.setText("");
                        } else if (!edt_oldpass.getText().toString().equals(pass)) {
                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Mật khẩu mới không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean check() {
        if (edt_oldpass.getText().toString().equals("") && edt_newpass.getText().toString().equals("")
                && edt_retype_newpass.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ vào ô trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}