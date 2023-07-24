package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.LSGD;

import java.util.ArrayList;

public class DAO_LSGD {
    SQLite sql;
    SQLiteDatabase dtb;

    public DAO_LSGD(Context context) {
        sql = new SQLite(context);
    }

    public ArrayList<LSGD> getAllLSGD(int makh) {
        ArrayList<LSGD> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from LSGD ls, KHACHHANG kh where kh.makh = ls.makh and kh.makh = ? order by id desc", new String[] {String.valueOf(makh)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LSGD ls = new LSGD();
            ls.setId(cursor.getInt(0));
            ls.setMakh(cursor.getInt(1));
            ls.setTitle(cursor.getString(2));
            ls.setSotien(cursor.getInt(3));
            ls.setTime(cursor.getString(4));
            list.add(ls);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean addLS(LSGD ls) {
        dtb = sql.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("makh", ls.getMakh());
        values.put("title", ls.getTitle());
        values.put("sotien", ls.getSotien());
        values.put("time", ls.getTime());

        if(dtb.insert("LSGD", null, values) <0) {
            return false;
        } return true;
    }
}
