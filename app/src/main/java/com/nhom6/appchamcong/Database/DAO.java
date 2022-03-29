package com.nhom6.appchamcong.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nhom6.appchamcong.Entity.SANPHAM;


public class DAO {
    private DBCHAMCONG dbCHAMCONG;

    public DAO() {
    }

    //Ví dụ truy vấn insert thêm 1 row vào table SANPHAM
    //Nếu thích làm kiểu viết hắn câu truy vấn TSQL ra string rồi execute thì dùng phương thức executeQuery và executeUpdate trong DBCHAMCONG
    public void themSanPham(Context context, SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SANPHAM.MASP, sp.getMaSP());
        cv.put(SANPHAM.TENSP,sp.getTenSP());
        cv.put(SANPHAM.DONGIA, sp.getDonGia());
        cv.put(SANPHAM.IMG,sp.getImg());

        long rs = db.insert(SANPHAM.TBLSANPHAM,null,cv);
    }
}
