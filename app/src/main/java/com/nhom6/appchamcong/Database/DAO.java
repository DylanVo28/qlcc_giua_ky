package com.nhom6.appchamcong.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Entity.SANPHAM;

import java.sql.Array;
import java.util.ArrayList;


public class DAO {
    private DBCHAMCONG dbCHAMCONG;

    public DAO() {
    }

    //Ví dụ truy vấn insert thêm 1 row vào table SANPHAM
    //Nếu thích làm kiểu viết hắn câu truy vấn TSQL ra string rồi execute thì dùng phương thức executeQuery và executeUpdate trong DBCHAMCONG

    //Sản phẩm DAO
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

    public boolean xoaSanPham(Context context,SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        return db.delete(SANPHAM.TBLSANPHAM,SANPHAM.MASP +"="+"'"+sp.getMaSP()+"'",null)>0;
    }

    public int suaSanPham(Context context, SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SANPHAM.TENSP,sp.getTenSP());
        cv.put(SANPHAM.DONGIA, sp.getDonGia());
        cv.put(SANPHAM.IMG,sp.getImg());
        return db.update(SANPHAM.TBLSANPHAM,cv,"MASP=?",new String[]{sp.getMaSP()});
    }

    @SuppressLint("Range")
    public ArrayList<SANPHAM> searchSP(Context context,String search){
        dbCHAMCONG = new DBCHAMCONG(context);
        ArrayList<SANPHAM> sanphams=new ArrayList<>();
        try{
            Cursor cursor=dbCHAMCONG.executeQuery("SELECT * FROM "+ SANPHAM.TBLSANPHAM + " WHERE " +
                    SANPHAM.TENSP + " LIKE  '"+search+"%'");

            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String MASP=cursor.getString(cursor.getColumnIndex("MASP"));
                    String TENSP=cursor.getString(cursor.getColumnIndex("TENSP"));
                    String DONGIA=cursor.getString(cursor.getColumnIndex("DONGIA"));
                    String IMG=cursor.getString(cursor.getColumnIndex("IMG"));
                    SANPHAM sp=new SANPHAM(MASP,TENSP,Integer.parseInt(DONGIA),IMG);

                    sanphams.add(sp);
                    cursor.moveToNext();
                }
                cursor.close();
                return sanphams;
            }
        }catch(Exception e){

        }
        return null;
    }

    @SuppressLint("Range")
    public ArrayList<SANPHAM> getSanphams(Context context){
        dbCHAMCONG=new DBCHAMCONG(context);
        Cursor db=dbCHAMCONG.executeQuery("SELECT * FROM SANPHAM");
        ArrayList<SANPHAM> sanphams= new ArrayList<SANPHAM>();
        int i = 0;
        if (db.getCount() > 0)
        {
            db.moveToFirst();
            do {

                String MASP=db.getString(db.getColumnIndex("MASP"));
                String TENSP=db.getString(db.getColumnIndex("TENSP"));
                String DONGIA=db.getString(db.getColumnIndex("DONGIA"));
                String IMG=db.getString(db.getColumnIndex("IMG"));


                sanphams.add(new SANPHAM(MASP,TENSP,Integer.parseInt(DONGIA),IMG));

                i++;
            } while (db.moveToNext());
            db.close();
        }
        return sanphams;
    }


    //Công nhân DAO
    public void themCongNhan(Context context, CONGNHAN cn) {
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONGNHAN.MACN, cn.getMaCN());
        cv.put(CONGNHAN.HOCN, cn.getHoCN());
        cv.put(CONGNHAN.TENCN, cn.getTenCN());
        cv.put(CONGNHAN.PHANXUONG, cn.getPhanXuong());

        long rs = db.insert(CONGNHAN.TBLCONGNHAN,null,cv);
    }
    public boolean xoaCongNhan(Context context,CONGNHAN cn) {
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        return db.delete(CONGNHAN.TBLCONGNHAN,CONGNHAN.MACN +"="+"'"+ cn.getMaCN()+"'",null) > 0;
    }

    public int suaCongNhan(Context context, CONGNHAN cn){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONGNHAN.HOCN, cn.getHoCN());
        cv.put(CONGNHAN.TENCN, cn.getTenCN());
        cv.put(CONGNHAN.PHANXUONG,cn.getPhanXuong());
        return db.update(CONGNHAN.TBLCONGNHAN,cv,"MACN=?",new String[]{cn.getMaCN()});
    }

    @SuppressLint("Range")
    public ArrayList<CONGNHAN> searchCN(Context context, String search) {
        dbCHAMCONG = new DBCHAMCONG(context);
        ArrayList<CONGNHAN> dsCongNhan = new ArrayList<>();
        try{
            Cursor cursor = dbCHAMCONG.executeQuery("SELECT * FROM "+ CONGNHAN.TBLCONGNHAN + " WHERE " +
                    CONGNHAN.TENCN + " LIKE  '"+search+"%'");

            if (cursor != null) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    String MACN = cursor.getString(cursor.getColumnIndex("MACN"));
                    String HOCN = cursor.getString(cursor.getColumnIndex("HOCN"));
                    String TENCN = cursor.getString(cursor.getColumnIndex("TENCN"));
                    String PHANXUONG = cursor.getString(cursor.getColumnIndex("PHANXUONG"));

                    CONGNHAN cn = new CONGNHAN(MACN,HOCN,TENCN,Integer.parseInt(PHANXUONG));

                    dsCongNhan.add(cn);
                    cursor.moveToNext();
                }
                cursor.close();
                return dsCongNhan;
            }
        }catch(Exception e){

        }
        return null;
    }

    @SuppressLint("Range")
    public ArrayList<CONGNHAN> getDSCongNhan(Context context) {
        dbCHAMCONG=new DBCHAMCONG(context);
        Cursor cursor = dbCHAMCONG.executeQuery("SELECT * FROM CONGNHAN");
        ArrayList<CONGNHAN> dsCongNhan = new ArrayList<CONGNHAN>();
        int i = 0;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do {
                String MACN = cursor.getString(cursor.getColumnIndex("MACN"));
                String HOCN = cursor.getString(cursor.getColumnIndex("HOCN"));
                String TENCN = cursor.getString(cursor.getColumnIndex("TENCN"));
                String PHANXUONG = cursor.getString(cursor.getColumnIndex("PHANXUONG"));
                dsCongNhan.add(new CONGNHAN(MACN,HOCN,TENCN,Integer.parseInt(PHANXUONG)));
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return dsCongNhan;
    }
}
