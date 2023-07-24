package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.NhanVien;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DAO_QLNV {
    SQLite sql;
    SQLiteDatabase dtb;
    ByteArrayOutputStream outputStream;
    byte[] imageByte;

    public DAO_QLNV(Context context) {
        sql = new SQLite(context);
    }

    public ArrayList<NhanVien> getAll() {
        ArrayList<NhanVien> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from NHANVIEN", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanVien nv = new NhanVien();
            nv.setManv(cursor.getString(0));
            nv.setTennv(cursor.getString(1));
            nv.setChucvu(cursor.getString(6));

            list.add(nv);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<NhanVien> getStaff(String manv) {
        ArrayList<NhanVien> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from NHANVIEN where manv = ?", new String[]{manv});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NhanVien nv = new NhanVien();
            nv.setManv(cursor.getString(0));
            nv.setTennv(cursor.getString(1));
            nv.setEmail(cursor.getString(2));
            nv.setSdt(cursor.getString(3));
            nv.setGioitinh(cursor.getInt(4));
            nv.setQuoctich(cursor.getInt(5));
            nv.setChucvu(cursor.getString(6));
            nv.setMatkhau(cursor.getString(7));

            Bitmap bitmap = null;
            byte[] imageByte = cursor.getBlob(8);
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            nv.setImage(bitmap);

            list.add(nv);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int checkAccount(String manv, String pass) {
        int count = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(*) from NHANVIEN where manv = '" + manv + "' and matkhau = '" + pass + "'", null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public boolean updatePW(String manv, String oldPass, String newPass) {
        dtb = sql.getWritableDatabase();
        Cursor cursor = dtb.rawQuery("select * from NHANVIEN where manv = ? and matkhau = ?", new String[]{manv, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            if (dtb.update("NHANVIEN", values, "manv = ?", new String[]{manv}) < 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean update(NhanVien nv) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tennv", nv.getTennv());
        values.put("email", nv.getEmail());
        values.put("sdt", nv.getSdt());
        values.put("gioitinh", nv.getGioitinh());
        values.put("quoctich", nv.getQuoctich());

        if (dtb.update("NHANVIEN", values, "manv = ?", new String[]{nv.getManv()}) < 0) {
            return false;
        }
        return true;
    }

    public boolean updateImage(NhanVien nv) {
        dtb = sql.getWritableDatabase();
        Bitmap bitmap = nv.getImage();

        outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        imageByte = outputStream.toByteArray();
        ContentValues values = new ContentValues();
        values.put("image", imageByte);
        if (dtb.update("NHANVIEN", values, "manv = ?", new String[]{nv.getManv()}) < 0) {
            return false;
        }
        return true;
    }
}
