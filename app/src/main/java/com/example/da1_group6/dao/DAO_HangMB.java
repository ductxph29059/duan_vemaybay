package com.example.da1_group6.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_group6.database.SQLite;
import com.example.da1_group6.model.HangMB;

import java.util.ArrayList;

public class DAO_HangMB {
    SQLite sql;
    SQLiteDatabase dtb;

    public DAO_HangMB(Context context) {
        sql = new SQLite(context);
    }

    public ArrayList<HangMB> getAll() {
        ArrayList<HangMB> list = new ArrayList<>();
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select * from HANGMAYBAY", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            HangMB hmb = new HangMB();
            hmb.setMamb(cursor.getString(0));
            hmb.setTenmb(cursor.getString(1));
            list.add(hmb);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
