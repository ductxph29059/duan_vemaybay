package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.HoaDonNapTien;
import com.example.da1_group6.model.KhachHang;
import com.example.da1_group6.model.NhanVien;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAO_KhachHang {
    SQLite sql;
    SQLiteDatabase dtb;
    ByteArrayOutputStream outputStream;
    byte[] imageByte;

    public DAO_KhachHang(Context context) {
        sql = new SQLite(context);
    }

    public ArrayList<KhachHang> getUser(String email) {
        ArrayList<KhachHang> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from KHACHHANG where email = ?", new String[]{String.valueOf(email)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KhachHang kh = new KhachHang();
            kh.setMakh(cursor.getInt(0));
            kh.setTenkh(cursor.getString(1));
            kh.setNgaysinh(cursor.getString(2));
            kh.setEmail(cursor.getString(3));
            kh.setSdt(cursor.getString(4));
            kh.setCccd(cursor.getString(5));
            kh.setGioitinh(cursor.getInt(6));
            kh.setDiachi(cursor.getString(7));
            kh.setQuoctich(cursor.getInt(8));
            kh.setMatkhau(cursor.getString(9));

            Bitmap bitmap = null;
            byte[] imageByte = cursor.getBlob(10);
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            kh.setImage(bitmap);
            kh.setSodu(cursor.getInt(11));

            list.add(kh);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<KhachHang> getUser_ID(int makh) {
        ArrayList<KhachHang> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from KHACHHANG where makh = ?", new String[]{String.valueOf(makh)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            KhachHang kh = new KhachHang();
            kh.setMakh(cursor.getInt(0));
            kh.setTenkh(cursor.getString(1));
            kh.setNgaysinh(cursor.getString(2));
            kh.setEmail(cursor.getString(3));
            kh.setSdt(cursor.getString(4));
            kh.setCccd(cursor.getString(5));
            kh.setGioitinh(cursor.getInt(6));
            kh.setDiachi(cursor.getString(7));
            kh.setQuoctich(cursor.getInt(8));
            kh.setMatkhau(cursor.getString(9));

            Bitmap bitmap = null;
            byte[] imageByte = cursor.getBlob(10);
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            kh.setImage(bitmap);
            kh.setSodu(cursor.getInt(11));

            list.add(kh);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean add(KhachHang kh) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", kh.getEmail());
        values.put("matkhau", kh.getMatkhau());
        values.put("image", "");
        if(dtb.insert("KHACHHANG", null, values) <0) {
            return false;
        }
        return true;
    }

    public int checkEmail(String email) {
        int count = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(makh) from KHACHHANG where email = '" + email + "'", null);
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public int checkAccount(String email, String pass) {
        int count = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(*) from KHACHHANG where email = '" + email + "' and matkhau = '" + pass + "'",null);
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public boolean updatePW(String email, String oldPass, String newPass) {
        dtb = sql.getWritableDatabase();
        Cursor cursor = dtb.rawQuery("select * from KHACHHANG where email = ? and matkhau = ?", new String[] {email, oldPass});
        if(cursor.getCount() >0) {
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            if(dtb.update("KHACHHANG", values, "email = ?", new String[]{String.valueOf(email)}) <0) {
                return false;
            } return true;
        }
        return false;
    }

    public boolean update(KhachHang kh) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenkh", kh.getTenkh());
        values.put("ngaysinh", kh.getNgaysinh());
        values.put("sdt", kh.getSdt());
        values.put("cccd", kh.getCccd());
        values.put("gioitinh", kh.getGioitinh());
        values.put("diachi", kh.getDiachi());
        values.put("quoctich", kh.getQuoctich());

        if (dtb.update("KHACHHANG", values, "email = ?", new String[]{kh.getEmail()}) < 0) {
            return false;
        }
        return true;
    }

    public boolean updateImage(KhachHang kh) {
        dtb = sql.getWritableDatabase();
        Bitmap bitmap = kh.getImage();

        outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        imageByte = outputStream.toByteArray();
        ContentValues values = new ContentValues();
        values.put("image", imageByte);
        if (dtb.update("KHACHHANG", values, "email = ?", new String[]{kh.getEmail()}) < 0) {
            return false;
        }
        return true;
    }

    public boolean update_Tien(KhachHang kh) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sodu", kh.getSodu());

        if(dtb.update("KHACHHANG", values, "makh = ?", new String[] {String.valueOf(kh.getMakh())}) <0) {
            return false;
        }
        return true;
    }
}
