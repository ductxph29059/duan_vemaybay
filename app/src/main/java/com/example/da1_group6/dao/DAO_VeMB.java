package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.VeMB;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DAO_VeMB {
    SQLite sql;
    SQLiteDatabase dtb;

    public DAO_VeMB(Context context) {
        sql = new SQLite(context);
    }


    public boolean addVMB(VeMB vmb) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("macb", vmb.getMacb());
        values.put("manv", vmb.getManv());
        values.put("makh", vmb.getMakh());
        values.put("timedatve", vmb.getTimedatve());
        values.put("trangthai", vmb.getTrangthai());
        if(dtb.insert("VEMAYBAY", null, values) <0 ) {
            return false;
        }
        return true;
    }

    public ArrayList<VeMB> getVMBtheoNV(String manv, int status) {
        ArrayList<VeMB> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select vmb.mavmb, cb.mamb, cb.macb,nv.manv, nv.tennv,kh.makh, kh.tenkh, kh.gioitinh, kh.email, kh.sdt , kh.diachi, kh.image, cb.diemdi, cb.diemden, cb.timebay, cb.tongtime, vmb.timedatve, vmb.trangthai\n" +
                "from CHUYENBAY cb , KHACHHANG kh , NHANVIEN nv, VEMAYBAY vmb \n" +
                "where vmb.macb = cb.macb and vmb.manv = nv.manv and vmb.makh = kh.makh and nv.manv = ? and vmb.trangthai = ?", new String[] {manv, String.valueOf(status)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            VeMB vmb = new VeMB();
            vmb.setMavmb(cursor.getInt(0));
            vmb.setMamb(cursor.getString(1));
            vmb.setMacb(cursor.getString(2));
            vmb.setManv(cursor.getString(3));
            vmb.setTennv(cursor.getString(4));

            vmb.setMakh(cursor.getInt(5));
            vmb.setTenkh(cursor.getString(6));
            vmb.setSex_user(cursor.getInt(7));
            vmb.setEmail_user(cursor.getString(8));
            vmb.setSdt_user(cursor.getString(9));
            vmb.setDiachi_user(cursor.getString(10));

            Bitmap bitmap1 = null;
            byte[] imageByte1 = cursor.getBlob(11);
            bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
            vmb.setImage_user(bitmap1);

            vmb.setDiemdi(cursor.getString(12));
            vmb.setDiemden(cursor.getString(13));
            vmb.setTimebay(cursor.getString(14));
            vmb.setTongtime(cursor.getString(15));
            vmb.setTimedatve(cursor.getString(16));
            vmb.setTrangthai(cursor.getInt(17));
            list.add(vmb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<VeMB> getVMBtheoKH(String email, int status) {
        ArrayList<VeMB> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select vmb.mavmb, cb.mamb, cb.macb,nv.manv, nv.tennv,kh.makh, kh.tenkh, cb.diemdi, cb.diemden, cb.timebay, cb.tongtime, vmb.timedatve, vmb.trangthai\n" +
                "from CHUYENBAY cb , KHACHHANG kh , NHANVIEN nv, VEMAYBAY vmb \n" +
                "where vmb.macb = cb.macb and vmb.manv = nv.manv and vmb.makh = kh.makh and kh.email = ? and vmb.trangthai = ?", new String[] {email, String.valueOf(status)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            VeMB vmb = new VeMB();
            vmb.setMavmb(cursor.getInt(0));
            vmb.setMamb(cursor.getString(1));
            vmb.setMacb(cursor.getString(2));
            vmb.setManv(cursor.getString(3));
            vmb.setTennv(cursor.getString(4));
            vmb.setMakh(cursor.getInt(5));
            vmb.setTenkh(cursor.getString(6));
            vmb.setDiemdi(cursor.getString(7));
            vmb.setDiemden(cursor.getString(8));
            vmb.setTimebay(cursor.getString(9));
            vmb.setTongtime(cursor.getString(10));
            vmb.setTimedatve(cursor.getString(11));
            vmb.setTrangthai(cursor.getInt(12));
            list.add(vmb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean updateVMB(VeMB vmb){
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangthai", vmb.getTrangthai());
        if(dtb.update("VEMAYBAY", values, "mavmb = ?", new String[] {String.valueOf(vmb.getMavmb())}) < 0 ) {
            return false;
        }
        return true;
    }

    public ArrayList<VeMB> getInfoUser_Staff() {
        ArrayList<VeMB> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select vmb.mavmb, cb.mamb, cb.macb,nv.manv, nv.tennv, nv.gioitinh, nv.email, nv.image ,kh.makh, kh.tenkh, kh.gioitinh, kh.email, kh.diachi, kh.image, cb.diemdi, cb.diemden, cb.timebay, cb.tongtime , vmb.timedatve, vmb.trangthai, kh.sdt\n" +
                "from CHUYENBAY cb , KHACHHANG kh , NHANVIEN nv, VEMAYBAY vmb \n" +
                "where vmb.macb = cb.macb and vmb.manv = nv.manv and vmb.makh = kh.makh", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            VeMB vmb = new VeMB();
            vmb.setMavmb(cursor.getInt(0));
            vmb.setMamb(cursor.getString(1));
            vmb.setMacb(cursor.getString(2));
            vmb.setManv(cursor.getString(3));
            vmb.setTennv(cursor.getString(4));
            vmb.setSex_staff(cursor.getInt(5));
            vmb.setEmail_staff(cursor.getString(6));

            Bitmap bitmap1 = null;
            byte[] imageByte1 = cursor.getBlob(7);
            bitmap1 = BitmapFactory.decodeByteArray(imageByte1, 0, imageByte1.length);
            vmb.setImage_staff(bitmap1);

            vmb.setMakh(cursor.getInt(8));
            vmb.setTenkh(cursor.getString(9));
            vmb.setSex_user(cursor.getInt(10));
            vmb.setEmail_user(cursor.getString(11));

            vmb.setDiachi_user(cursor.getString(12));

            Bitmap bitmap2 = null;
            byte[] imageByte2 = cursor.getBlob(13);
            bitmap2 = BitmapFactory.decodeByteArray(imageByte2, 0, imageByte2.length);
            vmb.setImage_user(bitmap2);

            vmb.setDiemdi(cursor.getString(14));
            vmb.setDiemden(cursor.getString(15));
            vmb.setTimebay(cursor.getString(16));
            vmb.setTongtime(cursor.getString(17));
            vmb.setTimedatve(cursor.getString(18));
            vmb.setTrangthai(cursor.getInt(19));
            vmb.setSdt_user(cursor.getString(20));
            list.add(vmb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int get_tongdoanhthu_staff(String manv, String ngaybd, String ngaykt, int status) {
        int sum = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select sum(cb.giave) from VEMAYBAY vmb, CHUYENBAY cb, NHANVIEN nv where vmb.macb = cb.macb and vmb.manv = nv.manv and vmb.manv = ?" +
                "and timedatve >= '" + ngaybd + "' and timedatve <= '" + ngaykt + "' and trangthai = ?", new String[] {manv, String.valueOf(status)});
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public int get_tongveban_staff(String manv, String ngaybd , String ngaykt, int status) {
        int sum = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(*) from VEMAYBAY vmb, CHUYENBAY cb, NHANVIEN nv where vmb.macb = cb.macb and vmb.manv = nv.manv and vmb.manv = ?" +
                "and timedatve >= '" + ngaybd + "' and timedatve <= '" + ngaykt + "' and trangthai = ?", new String[] {manv, String.valueOf(status)});
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public int get_tongdoanhthu(String ngaybd, String ngaykt, int status) {
        int sum = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select sum(cb.giave) from VEMAYBAY vmb, CHUYENBAY cb, NHANVIEN nv where vmb.macb = cb.macb and vmb.manv = nv.manv " +
                "and timedatve >= '" + ngaybd + "' and timedatve <= '" + ngaykt + "' and trangthai = ?", new String[] {String.valueOf(status)});
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        return sum;
    }

    public int get_tongveban(String ngaybd , String ngaykt, int status) {
        int sum = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(*) from VEMAYBAY vmb, CHUYENBAY cb, NHANVIEN nv where vmb.macb = cb.macb and vmb.manv = nv.manv " +
                "and timedatve >= '" + ngaybd + "' and timedatve <= '" + ngaykt + "' and trangthai = ?", new String[] {String.valueOf(status)});
        if(cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        return sum;
    }
}
