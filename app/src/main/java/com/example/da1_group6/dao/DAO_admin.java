package com.example.da1_group6.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.da1_group6.database.SQLite;

import java.util.ArrayList;

public class DAO_admin {
    SQLite sql;
    SQLiteDatabase dtb;

    public DAO_admin(Context context) {
        sql = new SQLite(context);
    }

    public int checkAccount(String user, String pass) {
        int count = 0;
        dtb = sql.getReadableDatabase();
        Cursor cursor = dtb.rawQuery("select count(*) from ADMIN where user = '" + user + "' and pass = '" + pass + "'",null);
        if(cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public boolean updatePW(String user, String oldPass, String newPass) {
        dtb = sql.getWritableDatabase();
        Cursor cursor = dtb.rawQuery("select * from ADMIN where user = ? and pass = ?", new String[] {user, oldPass});
        if(cursor.getCount() >0) {
            ContentValues values = new ContentValues();
            values.put("pass", newPass);
            if(dtb.update("ADMIN", values, "user = ?", new String[]{user}) <0) {
                return false;
            } return true;
        }
        return false;
    }
}
