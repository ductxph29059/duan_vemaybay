package com.example.da1_group6.model;

import android.graphics.Bitmap;

import java.sql.Blob;

public class VeMB {
    int mavmb;
    String mamb, macb, manv, tennv, tenkh;
    int makh;
    String diemdi, diemden, timebay, tongtime, timedatve;
    int sex_user, sex_staff;
    Bitmap image_user, image_staff;
    String email_user, email_staff, diachi_user;
    int trangthai;
    String sdt_user;

    public VeMB(int mavmb, String mamb, String macb, String manv, String tennv, int makh, String tenkh, String diemdi, String diemden, String timebay, String tongtime, String timedatve, int trangthai) {
        this.mavmb = mavmb;
        this.mamb = mamb;
        this.macb = macb;
        this.manv = manv;
        this.tennv = tennv;
        this.makh = makh;
        this.tenkh = tenkh;
        this.diemdi = diemdi;
        this.diemden = diemden;
        this.timebay = timebay;
        this.tongtime = tongtime;
        this.timedatve = timedatve;
        this.trangthai = trangthai;
    }

    public VeMB(int mavmb, String mamb, String macb, String manv, int makh, String timedatve, int trangthai) {
        this.mavmb = mavmb;
        this.mamb = mamb;
        this.macb = macb;
        this.manv = manv;
        this.makh = makh;
        this.timedatve = timedatve;
        this.trangthai = trangthai;
    }

    public VeMB(int mavmb, String mamb, String macb, String manv, String tennv, int makh, String tenkh, String diemdi, String diemden, String timebay, String tongtime, String timedatve, int sex_user, int sex_staff, Bitmap image_user, Bitmap image_staff, String email_user, String email_staff, String diachi_user, int trangthai, String sdt_user) {
        this.mavmb = mavmb;
        this.mamb = mamb;
        this.macb = macb;
        this.manv = manv;
        this.tennv = tennv;
        this.makh = makh;
        this.tenkh = tenkh;
        this.diemdi = diemdi;
        this.diemden = diemden;
        this.timebay = timebay;
        this.tongtime = tongtime;
        this.timedatve = timedatve;
        this.sex_user = sex_user;
        this.sex_staff = sex_staff;
        this.image_user = image_user;
        this.image_staff = image_staff;
        this.email_user = email_user;
        this.email_staff = email_staff;
        this.diachi_user = diachi_user;
        this.trangthai = trangthai;
        this.sdt_user = sdt_user;
    }

    public VeMB() {
    }

    public int getMavmb() {
        return mavmb;
    }

    public void setMavmb(int mavmb) {
        this.mavmb = mavmb;
    }

    public String getMamb() {
        return mamb;
    }

    public void setMamb(String mamb) {
        this.mamb = mamb;
    }

    public String getMacb() {
        return macb;
    }

    public void setMacb(String macb) {
        this.macb = macb;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
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

    public String getTimebay() {
        return timebay;
    }

    public void setTimebay(String timebay) {
        this.timebay = timebay;
    }

    public String getTimedatve() {
        return timedatve;
    }

    public void setTimedatve(String timedatve) {
        this.timedatve = timedatve;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getTongtime() {
        return tongtime;
    }

    public void setTongtime(String tongtime) {
        this.tongtime = tongtime;
    }

    public int getSex_user() {
        return sex_user;
    }

    public void setSex_user(int sex_user) {
        this.sex_user = sex_user;
    }

    public int getSex_staff() {
        return sex_staff;
    }

    public void setSex_staff(int sex_staff) {
        this.sex_staff = sex_staff;
    }

    public Bitmap getImage_user() {
        return image_user;
    }

    public void setImage_user(Bitmap image_user) {
        this.image_user = image_user;
    }

    public Bitmap getImage_staff() {
        return image_staff;
    }

    public void setImage_staff(Bitmap image_staff) {
        this.image_staff = image_staff;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getEmail_staff() {
        return email_staff;
    }

    public void setEmail_staff(String email_staff) {
        this.email_staff = email_staff;
    }

    public String getDiachi_user() {
        return diachi_user;
    }

    public void setDiachi_user(String diachi_user) {
        this.diachi_user = diachi_user;
    }

    public String getSdt_user() {
        return sdt_user;
    }

    public void setSdt_user(String sdt_user) {
        this.sdt_user = sdt_user;
    }
}
