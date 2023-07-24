package com.example.da1_group6.model;

public class LSGD {
    int id;
    int makh;
    String title;
    int sotien;
    String time;

    public LSGD() {
    }

    public LSGD(int id, int makh, String title, int sotien, String time) {
        this.id = id;
        this.makh = makh;
        this.title = title;
        this.sotien = sotien;
        this.time = time;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
