package com.example.da1_group6.model;

import android.graphics.Bitmap;

public class HoaDonNapTien {
    int id;
    int makh;
    String hotenkh;
    int sotiennap;
    String timenap;
    int trangthai;
    Bitmap bill;

    public HoaDonNapTien(int id, int makh, String hotenkh, int sotiennap, String timenap, int trangthai, Bitmap bill) {
        this.id = id;
        this.makh = makh;
        this.hotenkh = hotenkh;
        this.sotiennap = sotiennap;
        this.timenap = timenap;
        this.trangthai = trangthai;
        this.bill = bill;
    }

    public HoaDonNapTien(int id, int makh, int sotiennap, String timenap, int trangthai, Bitmap bill) {
        this.id = id;
        this.makh = makh;
        this.sotiennap = sotiennap;
        this.timenap = timenap;
        this.trangthai = trangthai;
        this.bill = bill;
    }

    public HoaDonNapTien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getHotenkh() {
        return hotenkh;
    }

    public void setHotenkh(String hotenkh) {
        this.hotenkh = hotenkh;
    }

    public int getSotiennap() {
        return sotiennap;
    }

    public void setSotiennap(int sotiennap) {
        this.sotiennap = sotiennap;
    }

    public String getTimenap() {
        return timenap;
    }

    public void setTimenap(String timenap) {
        this.timenap = timenap;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public Bitmap getBill() {
        return bill;
    }

    public void setBill(Bitmap bill) {
        this.bill = bill;
    }
}
