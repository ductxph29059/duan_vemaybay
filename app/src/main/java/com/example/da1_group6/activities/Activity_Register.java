package com.example.da1_group6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.da1_group6.R;
import com.example.da1_group6.dao.DAO_KhachHang;
import com.example.da1_group6.model.KhachHang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Register extends AppCompatActivity {
    EditText edt_email, edt_pass, edt_retype_pass;
    Button btnReg;
    ImageView showPass1, showPass2;
    DAO_KhachHang dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnReg = findViewById(R.id.btnRegister);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        edt_retype_pass = findViewById(R.id.edt_retype_pass);
        showPass1 = findViewById(R.id.showPass1_inReg);
        showPass2 = findViewById(R.id.showPass2_inReg);
        dao = new DAO_KhachHang(this);

        edt_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        edt_retype_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        showPass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.showPass1_inReg) {
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

        showPass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.showPass2_inReg) {
                    if (edt_retype_pass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_visibility_off);
                        //Show Password
                        edt_retype_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    } else {
                        ((ImageView) (v)).setImageResource(R.drawable.ic_visibility);
                        //Hide Password
                        edt_retype_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()) {
                    if (!edt_pass.getText().toString().trim().equals(edt_retype_pass.getText().toString().trim())) {
                        Toast.makeText(Activity_Register.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (dao.checkEmail(edt_email.getText().toString().trim()) > 0) {
                            Toast.makeText(Activity_Register.this, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            KhachHang kh = new KhachHang();
                            dao.add(new KhachHang(edt_email.getText().toString().trim(), edt_pass.getText().toString().trim()));
                            Toast.makeText(Activity_Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Activity_Register.this, Activity_Login.class);
                            intent.putExtra("email", edt_email.getText().toString().trim());
                            intent.putExtra("pass", edt_pass.getText().toString().trim());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_dialog, R.anim.slide_out_dialog);
                        }
                    }
                }
            }
        });
    }

    public void backto_login(View view) {
        onBackPressed();
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean check() {
        if (edt_email.getText().toString().trim().equals("") && edt_pass.getText().toString().trim().equals("")
                && edt_retype_pass.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!emailValidator(edt_email.getText().toString().trim())) {
            Toast.makeText(Activity_Register.this, "Email sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}