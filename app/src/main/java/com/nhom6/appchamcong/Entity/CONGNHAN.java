package com.nhom6.appchamcong.Entity;

public class CONGNHAN {
    public static final String TBLCONGNHAN = "CONGNHAN";
    public static final String MACN = "MACN";
    public static final String HOCN = "HOCN";
    public static final String TENCN = "TENCN";
    public static final String PHANXUONG = "PHANXUONG";

    private String MaCN;
    private String HoCN;
    private String TenCN;
    private int PhanXuong;

    public CONGNHAN() {
    }

    public CONGNHAN(String maCN, String hoCN, String tenCN, int phanXuong) {
        this.MaCN = maCN;
        this.HoCN = hoCN;
        this.TenCN = tenCN;
        this.PhanXuong = phanXuong;
    }

    public String getMaCN() {
        return MaCN;
    }

    public void setMaCN(String maCN) {
        MaCN = maCN;
    }

    public String getHoCN() {
        return HoCN;
    }

    public void setHoCN(String hoCN) {
        HoCN = hoCN;
    }

    public String getTenCN() {
        return TenCN;
    }

    public void setTenCN(String tenCN) {
        TenCN = tenCN;
    }

    public int getPhanXuong() {
        return PhanXuong;
    }

    public void setPhanXuong(int phanXuong) {
        PhanXuong = phanXuong;
    }
}
