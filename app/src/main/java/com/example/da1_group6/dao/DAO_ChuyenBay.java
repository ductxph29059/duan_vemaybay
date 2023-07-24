package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.ChuyenBay;
import com.example.da1_group6.model.HangMB;

import java.util.ArrayList;

public class DAO_ChuyenBay {
    SQLite sql;
    SQLiteDatabase dtb;

    public DAO_ChuyenBay(Context context) {
        sql = new SQLite(context);
    }

    public ArrayList<ChuyenBay> getAll() {
        ArrayList<ChuyenBay> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from CHUYENBAY", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChuyenBay cb = new ChuyenBay();
            cb.setMacb(cursor.getString(0));
            cb.setDiemdi(cursor.getString(1));
            cb.setDiemden(cursor.getString(2));
            cb.setGiave(cursor.getInt(3));
            cb.setTimebay(cursor.getString(4));
            cb.setTongtime(cursor.getString(5));
            cb.setSoluongve(cursor.getInt(6));
            cb.setMamb(cursor.getString(7));
            list.add(cb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<ChuyenBay> getCB_theoMACB(String macb) {
        ArrayList<ChuyenBay> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from CHUYENBAY where macb = ?", new String[] {macb});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChuyenBay cb = new ChuyenBay();
            cb.setMacb(cursor.getString(0));
            cb.setDiemdi(cursor.getString(1));
            cb.setDiemden(cursor.getString(2));
            cb.setGiave(cursor.getInt(3));
            cb.setTimebay(cursor.getString(4));
            cb.setTongtime(cursor.getString(5));
            cb.setSoluongve(cursor.getInt(6));
            cb.setMamb(cursor.getString(7));
            list.add(cb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<ChuyenBay> getChuyenBay(String diemdi, String diemden, String mamb, String ngaybay) {
        ArrayList<ChuyenBay> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from CHUYENBAY where diemdi = ? and diemden = ? and mamb = ? and timebay like '%" + ngaybay + "%'", new String[] {diemdi, diemden, mamb});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChuyenBay cb = new ChuyenBay();
            cb.setMacb(cursor.getString(0));
            cb.setDiemdi(cursor.getString(1));
            cb.setDiemden(cursor.getString(2));
            cb.setGiave(cursor.getInt(3));
            cb.setTimebay(cursor.getString(4));
            cb.setTongtime(cursor.getString(5));
            cb.setSoluongve(cursor.getInt(6));
            cb.setMamb(cursor.getString(7));
            list.add(cb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<ChuyenBay> getAllChuyenBay(String diemdi, String diemden, String ngaybay) {
        ArrayList<ChuyenBay> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from CHUYENBAY where diemdi = ? and diemden = ? and timebay like '%" + ngaybay + "%'", new String[] {diemdi, diemden});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChuyenBay cb = new ChuyenBay();
            cb.setMacb(cursor.getString(0));
            cb.setDiemdi(cursor.getString(1));
            cb.setDiemden(cursor.getString(2));
            cb.setGiave(cursor.getInt(3));
            cb.setTimebay(cursor.getString(4));
            cb.setTongtime(cursor.getString(5));
            cb.setSoluongve(cursor.getInt(6));
            cb.setMamb(cursor.getString(7));
            list.add(cb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean updateSLVMB(ChuyenBay cb) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluongve", cb.getSoluongve());
        if(dtb.update("CHUYENBAY", values, "macb = ?", new String[] {cb.getMacb()}) <0) {
            return false;
        }
        return true;
    }

    public int getSLve(String macb) {
        int sl = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select soluongve from CHUYENBAY where macb = ?", new String[] {macb});
        if(cursor.moveToFirst()) {
            sl = cursor.getInt(0);
        }
        return sl;
    }

    public boolean addCB(ChuyenBay cb) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("macb", cb.getMacb());
        values.put("diemdi", cb.getDiemdi());
        values.put("diemden", cb.getDiemden());
        values.put("giave", cb.getGiave());
        values.put("timebay", cb.getTimebay());
        values.put("tongtime", cb.getTongtime());
        values.put("soluongve", cb.getSoluongve());
        values.put("mamb", cb.getMamb());
        if(dtb.insert("CHUYENBAY", null, values) <0) {
            return false;
        } return true;
    }

    public boolean updCB(ChuyenBay cb) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("diemdi", cb.getDiemdi());
        values.put("diemden", cb.getDiemden());
        values.put("giave", cb.getGiave());
        values.put("timebay", cb.getTimebay());
        values.put("tongtime", cb.getTongtime());
        values.put("soluongve", cb.getSoluongve());
        values.put("mamb", cb.getMamb());
        if(dtb.update("CHUYENBAY", values, "macb = ?", new String[] {cb.getMacb()}) <0) {
            return false;
        } return true;
    }

    public boolean delCB(String macb) {
        dtb = sql.getWritableDatabase();
        if(dtb.delete("CHUYENBAY", "macb = ?", new String[] {macb}) <0) {
            return false;
        } return true;
    }
}
