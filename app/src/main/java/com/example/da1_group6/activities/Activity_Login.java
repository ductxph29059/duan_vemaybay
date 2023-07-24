package com.example.da1_group6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.dao.DAO_QLNV;
import com.example.da1_group6.dao.DAO_admin;

import java.util.ArrayList;
import java.util.List;

public class Activity_Login extends AppCompatActivity {
    EditText edt_email, edt_pass;
    Button btnLogin;
    ImageView showPass;
    CheckBox ckbox;
    List<Object> listT = new ArrayList<>();
    List<Object> listF = new ArrayList<>();
    DAO_admin dao_admin;
    DAO_QLNV dao_nv;
    DAO_KhachHang dao_kh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        showPass = findViewById(R.id.showPass);
        ckbox = findViewById(R.id.checkbox);

        dao_admin = new DAO_admin(this);
        dao_nv = new DAO_QLNV(this);
        dao_kh = new DAO_KhachHang(this);

        overridePendingTransition(R.anim.animation1, R.anim.animation2);

        edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        listT = readAccTrue();

        if((boolean)(listT.get(2)) == false) {
            if (listT.size() > 0) {
                edt_email.setText("");
                edt_pass.setText("");
                ckbox.setChecked((boolean) listT.get(2));
            }
        } else {
            if (listT.size() > 0) {
                edt_email.setText(listT.get(0).toString());
                edt_pass.setText(listT.get(1).toString());
                ckbox.setChecked((boolean) listT.get(2));
            }
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edt_email.getText().toString();
                String pass = edt_pass.getText().toString();

                boolean check = ckbox.isChecked();
                saveAcc(user, pass, check);

                if (check()) {
                    if (dao_admin.checkAccount(user, pass) > 0) {
                        startActivity(new Intent(Activity_Login.this, ForAdminActivity.class));
                        overridePendingTransition(R.anim.slide_in_dialog, R.anim.slide_out_dialog);
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    } else if (dao_nv.checkAccount(user, pass) > 0) {
                        startActivity(new Intent(Activity_Login.this, ForStaffActivity.class));
                        overridePendingTransition(R.anim.slide_in_dialog, R.anim.slide_out_dialog);
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    } else if (dao_kh.checkAccount(user, pass) > 0) {
                        startActivity(new Intent(Activity_Login.this, ForUserActivity.class));
                        overridePendingTransition(R.anim.slide_in_dialog, R.anim.slide_out_dialog);
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.showPass) {
                    if (edt_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off);
                        //Show Password
                        edt_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_visibility);
                        //Hide Password
                        edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });
    }

    public void from_login_to_register(View view) {
        startActivity(new Intent(Activity_Login.this, Activity_Register.class));
        overridePendingTransition(R.anim.animation1, R.anim.animation2);
    }

    public boolean check() {
        if (edt_email.getText().toString().trim().equals("") && edt_pass.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void saveAcc(String user, String pass, boolean check) {
        SharedPreferences preferences = getSharedPreferences("TB", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (check == false) {
            editor.putString("User", user);
            editor.putString("Pass", pass);
            editor.putBoolean("Check", check);
        } else {
            editor.putString("User", user);
            editor.putString("Pass", pass);
            editor.putBoolean("Check", check);
        }
        editor.commit();
    }

    List<Object> readAccTrue() {
        SharedPreferences s = getSharedPreferences("TB", MODE_PRIVATE);
        listT.add(s.getString("User", ""));
        listT.add(s.getString("Pass", ""));
        listT.add(s.getBoolean("Check", false));
        return listT;
    }

}