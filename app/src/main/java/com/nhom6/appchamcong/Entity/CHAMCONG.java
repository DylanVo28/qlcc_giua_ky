package com.nhom6.appchamcong.Entity;

public class CHAMCONG {
    public static final String TBLCHAMCONG = "CHAMCONG";
    public static final String MACC = "MACC";
    public static final String NGAYCHAMCONG = "NGAYCHAMCONG";
    public static final String MACN = "MACN";

    private String MaCC;
    // NgayChamCong có thể dùng simpleDateFormat để chuyển đổi qua lại giữa String và Date
    private String NgayChamCong;
    private String MaCN;

    public CHAMCONG() {
    }

    public CHAMCONG(String maCC, String ngayChamCong, String maCN) {
        MaCC = maCC;
        NgayChamCong = ngayChamCong;
        MaCN = maCN;
    }

    public String getMaCC() {
        return MaCC;
    }

    public void setMaCC(String maCC) {
        MaCC = maCC;
    }

    public String getNgayChamCong() {
        return NgayChamCong;
    }

    public void setNgayChamCong(String ngayChamCong) {
        NgayChamCong = ngayChamCong;
    }

    public String getMaCN() {
        return MaCN;
    }

    public void setMaCN(String maCN) {
        MaCN = maCN;
    }
}
