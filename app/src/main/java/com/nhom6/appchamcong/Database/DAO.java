package com.nhom6.appchamcong.Database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.nhom6.appchamcong.Entity.CHAMCONG;
import com.nhom6.appchamcong.Entity.CHITIETCHAMCONG;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Entity.SANPHAM;

import java.util.ArrayList;


public class DAO {
    private DBCHAMCONG dbCHAMCONG;

    public DAO() {
    }

    //Ví dụ truy vấn insert thêm 1 row vào table SANPHAM
    //Nếu thích làm kiểu viết hắn câu truy vấn TSQL ra string rồi execute thì dùng phương thức executeQuery và executeUpdate trong DBCHAMCONG

    //Sản phẩm DAO
    public boolean themSanPham(Context context, SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SANPHAM.MASP, sp.getMaSP());
        cv.put(SANPHAM.TENSP,sp.getTenSP());
        cv.put(SANPHAM.DONGIA, sp.getDonGia());
        cv.put(SANPHAM.IMG,sp.getImg());
        long rs = db.insert(SANPHAM.TBLSANPHAM,null,cv);

        return rs>0? true:false;
    }



    public boolean xoaSanPham(Context context,SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        return db.delete(SANPHAM.TBLSANPHAM,SANPHAM.MASP +"="+"'"+sp.getMaSP()+"'",null)>0;
    }

    public boolean suaSanPham(Context context, SANPHAM sp){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SANPHAM.TENSP,sp.getTenSP());
        cv.put(SANPHAM.DONGIA, sp.getDonGia());
        cv.put(SANPHAM.IMG,sp.getImg());
        try{
            int rs= db.update(SANPHAM.TBLSANPHAM,cv,"MASP=?",new String[]{sp.getMaSP()});

        }catch (SQLiteConstraintException e){
            return false;
        }
        return true;
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

    public boolean xoaCongNhan (Context context,CONGNHAN cn) throws SQLiteConstraintException {
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        try {
            db.delete(CONGNHAN.TBLCONGNHAN,CONGNHAN.MACN +"="+"'"+ cn.getMaCN()+"'",null);
            return true;
        }catch (SQLiteConstraintException e){
            new AlertDialog.Builder(context)
                    .setTitle("Không thể xóa công nhân "+cn.getMaCN())
                    .setMessage("Công nhân này đã có chấm công, không thể xóa")
                    .setPositiveButton("Đã hiểu",null)
                    .show();
            return false;
        }
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
                    CONGNHAN.TENCN + " LIKE  '"+search+"%'"+" OR "+CONGNHAN.HOCN+" LIKE '"+search+"%'");

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

    @SuppressLint("Range")
    public ArrayList<CHAMCONG> getDSChamCong(Context context, String macn) {
        dbCHAMCONG = new DBCHAMCONG(context);
        Cursor cursor = dbCHAMCONG.executeQuery("SELECT * FROM CHAMCONG WHERE MACN = '" + macn+"'");
        ArrayList<CHAMCONG> dsChamCong = new ArrayList<CHAMCONG>();
        int i = 0;
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            do {
                String MACC = cursor.getString(cursor.getColumnIndex("MACC"));
                String NGAYCHAMCONG = cursor.getString(cursor.getColumnIndex("NGAYCHAMCONG"));
                String MACN = cursor.getString(cursor.getColumnIndex("MACN"));
                dsChamCong.add(new CHAMCONG(MACC,NGAYCHAMCONG,MACN));
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return dsChamCong;
    }

    public ArrayList<CHITIETCHAMCONG> getDsCtChamCong(Context context, String macc){
        dbCHAMCONG = new DBCHAMCONG(context);
        ArrayList<CHITIETCHAMCONG> dsCtChamCong = new ArrayList<>();
        String sql = "SELECT SANPHAM.MASP,SOTP,SOPP,TENSP,DONGIA,IMG FROM CHITIETCHAMCONG, SANPHAM "
                +"WHERE CHITIETCHAMCONG.MASP = SANPHAM.MASP " +
                "AND MACC = '"+macc+"'";
        Cursor cursor = dbCHAMCONG.executeQuery(sql);
        if (cursor.moveToFirst()){
            do {
                SANPHAM sp = new SANPHAM(
                        cursor.getString(0),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5));
                CHITIETCHAMCONG ctcc = new CHITIETCHAMCONG(macc,sp,
                        cursor.getInt(1),
                        cursor.getInt(2));
                dsCtChamCong.add(ctcc);
            }while (cursor.moveToNext());
            cursor.close();
        }
        return dsCtChamCong;
    }

    public boolean themCtChamCong(Context context, CHITIETCHAMCONG ctcc){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHITIETCHAMCONG.MACC,ctcc.getMaCC());
        cv.put(CHITIETCHAMCONG.MASP, ctcc.getMaSP());
        cv.put(CHITIETCHAMCONG.SOTP, ctcc.getSoTP());
        cv.put(CHITIETCHAMCONG.SOPP, ctcc.getSoPP());
        long rs = db.insert(CHITIETCHAMCONG.TBLCHITIETCHAMCONG, null, cv);

        return rs>0? true:false;
    }

    public boolean xoaCtChamCong(Context context,CHITIETCHAMCONG ctcc) {
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        return db.delete(CHITIETCHAMCONG.TBLCHITIETCHAMCONG,
                "MACC=? AND MASP=?",new String[]{ctcc.getMaCC(),ctcc.getMaSP()}) > 0;
    }

    public int suaCtChamCong(Context context, CHITIETCHAMCONG ctcc){
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHITIETCHAMCONG.SOTP, ctcc.getSoTP());
        cv.put(CHITIETCHAMCONG.SOPP, ctcc.getSoPP());
        return db.update(CHITIETCHAMCONG.TBLCHITIETCHAMCONG,cv,
                "MACC=? AND MASP=?",new String[]{ctcc.getMaCC(),ctcc.getMaSP()});
    }

    @SuppressLint("Range")
    public ArrayList<SANPHAM> getSanphamCc(Context context, String macc){
        dbCHAMCONG=new DBCHAMCONG(context);
        Cursor db=dbCHAMCONG.executeQuery("SELECT * FROM SANPHAM\n" +
                "WHERE MASP NOT IN (SELECT MASP \n" +
                "                   FROM CHITIETCHAMCONG \n" +
                "                   WHERE MACC = '"+macc+"')");
        ArrayList<SANPHAM> sanphams= new ArrayList<SANPHAM>();
        if (db.getCount() > 0)
        {
            db.moveToFirst();
            do {
                String MASP=db.getString(db.getColumnIndex("MASP"));
                String TENSP=db.getString(db.getColumnIndex("TENSP"));
                String DONGIA=db.getString(db.getColumnIndex("DONGIA"));
                String IMG=db.getString(db.getColumnIndex("IMG"));

                sanphams.add(new SANPHAM(MASP,TENSP,Integer.parseInt(DONGIA),IMG));
            } while (db.moveToNext());
            db.close();
        }
        return sanphams;
    }

    //Chấm công DAO
    public void themChamCong(Context context, CHAMCONG cc) {
        dbCHAMCONG = new DBCHAMCONG(context);
        SQLiteDatabase db = dbCHAMCONG.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CHAMCONG.MACN, cc.getMaCN());
        cv.put(CHAMCONG.MACC, cc.getMaCC());
        cv.put(CHAMCONG.NGAYCHAMCONG, cc.getNgayChamCong());

        long rs = db.insert(CHAMCONG.TBLCHAMCONG,null,cv);
    }
}
