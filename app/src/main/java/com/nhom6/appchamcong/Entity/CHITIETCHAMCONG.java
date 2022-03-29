package com.nhom6.appchamcong.Entity;

public class CHITIETCHAMCONG {
    public static final String TBLCHITIETCHAMCONG = "CHITIETCHAMCONG";
    public static final String MACC = "MACC";
    public static final String MASP = "MASP";
    public static final String SOTP = "SOTP";
    public static final String SOPP = "SOPP";

    private String MaCC;
    private String MaSP;
    private int SoTP;
    private int SoPP;

    public CHITIETCHAMCONG() {
    }

    public CHITIETCHAMCONG(String maCC, String maSP, int soTP, int soPP) {
        MaCC = maCC;
        MaSP = maSP;
        SoTP = soTP;
        SoPP = soPP;
    }

    public String getMaCC() {
        return MaCC;
    }

    public void setMaCC(String maCC) {
        MaCC = maCC;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public int getSoTP() {
        return SoTP;
    }

    public void setSoTP(int soTP) {
        SoTP = soTP;
    }

    public int getSoPP() {
        return SoPP;
    }

    public void setSoPP(int soPP) {
        SoPP = soPP;
    }
}
