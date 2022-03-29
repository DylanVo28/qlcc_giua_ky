package com.nhom6.appchamcong.Entity;

public class SANPHAM {

    public static final String TBLSANPHAM = "SANPHAM";
    public static final String MASP = "MASP";
    public static final String TENSP = "TENSP";
    public static final String DONGIA = "DONGIA";
    public static final String IMG = "IMG";

    private String MaSP;
    private String TenSP;
    private int DonGia;
    private String Img;

    public SANPHAM() {
    }

    public SANPHAM(String maSP, String tenSP, int donGia, String Img) {
        this.MaSP = maSP;
        this.TenSP = tenSP;
        this.DonGia = donGia;
        this.Img = Img;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }
}
