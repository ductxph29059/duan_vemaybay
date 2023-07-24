package com.example.da1_group6.model;

public class ChuyenBay {
    String macb, diemdi, diemden;
    int giave;
    String timebay, tongtime;
    int soluongve;
    String mamb;

    public ChuyenBay(String macb, String diemdi, String diemden, int giave, String timebay,String tongtime,int soluongve, String mamb) {
        this.macb = macb;
        this.diemdi = diemdi;
        this.diemden = diemden;
        this.giave = giave;
        this.timebay = timebay;
        this.tongtime = tongtime;
        this.soluongve = soluongve;
        this.mamb = mamb;
    }

    public ChuyenBay() {
    }

    public String getMacb() {
        return macb;
    }

    public void setMacb(String macb) {
        this.macb = macb;
    }

    public String getDiemdi() {
        return diemdi;
    }

    public void setDiemdi(String diemdi) {
        this.diemdi = diemdi;
    }

    public String getDiemden() {
        return diemden;
    }

    public void setDiemden(String diemden) {
        this.diemden = diemden;
    }

    public int getGiave() {
        return giave;
    }

    public void setGiave(int giave) {
        this.giave = giave;
    }

    public String getTimebay() {
        return timebay;
    }

    public void setTimebay(String timebay) {
        this.timebay = timebay;
    }

    public String getMamb() {
        return mamb;
    }

    public void setMamb(String mamb) {
        this.mamb = mamb;
    }

    public String getTongtime() {
        return tongtime;
    }

    public void setTongtime(String tongtime) {
        this.tongtime = tongtime;
    }

    public int getSoluongve() {
        return soluongve;
    }

    public void setSoluongve(int soluongve) {
        this.soluongve = soluongve;
    }
}
