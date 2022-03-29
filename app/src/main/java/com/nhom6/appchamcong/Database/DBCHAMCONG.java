package com.nhom6.appchamcong.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nhom6.appchamcong.Entity.CHAMCONG;
import com.nhom6.appchamcong.Entity.CHITIETCHAMCONG;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Entity.SANPHAM;

public class DBCHAMCONG extends SQLiteOpenHelper {

    public DBCHAMCONG(@Nullable Context context) {
        super(context, "DBCHAMCONG.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblCONGNHAN = "CREATE TABLE " + CONGNHAN.TBLCONGNHAN + " (" + CONGNHAN.MACN + " TEXT (10) PRIMARY KEY NOT NULL, " + CONGNHAN.HOCN + " TEXT (30) NOT NULL, " + CONGNHAN.TENCN + " TEXT (10) NOT NULL, " + CONGNHAN.PHANXUONG + " INTEGER NOT NULL);";
        String createTblSANPHAM = "CREATE TABLE " + SANPHAM.TBLSANPHAM + " (" + SANPHAM.MASP + " TEXT (10) PRIMARY KEY NOT NULL, " + SANPHAM.TENSP + " TEXT (50) NOT NULL UNIQUE, " + SANPHAM.DONGIA + " INTEGER NOT NULL, " + SANPHAM.IMG + " TEXT NOT NULL);";
        String createTblCHAMCONG = "CREATE TABLE " + CHAMCONG.TBLCHAMCONG + " (\n" +
                "    " + CHAMCONG.MACC + "         TEXT (10) PRIMARY KEY\n" +
                "                           NOT NULL,\n" +
                "    " + CHAMCONG.NGAYCHAMCONG + " DATETIME  NOT NULL,\n" +
                "    " + CHAMCONG.MACN + "         TEXT (10) NOT NULL,\n" +
                "    FOREIGN KEY(" + CHAMCONG.MACN + ") REFERENCES CONGNHAN (MACN)\n" +
                ");";
        String createTblCHITIETCHAMCONG = "CREATE TABLE " + CHITIETCHAMCONG.TBLCHITIETCHAMCONG + " (\n" +
                "    " + CHITIETCHAMCONG.MACC + " TEXT (10),\n" +
                "    " + CHITIETCHAMCONG.MASP + " TEXT (10),\n" +
                "    " + CHITIETCHAMCONG.SOTP + " INTEGER   NOT NULL,\n" +
                "    " + CHITIETCHAMCONG.SOPP + " INTEGER   NOT NULL,\n" +
                "    PRIMARY KEY (" + CHITIETCHAMCONG.MACC + ", " + CHITIETCHAMCONG.MASP + "),\n" +
                "    FOREIGN KEY(" + CHITIETCHAMCONG.MACC + ") REFERENCES CHAMCONG(MACC),\n" +
                "    FOREIGN KEY(" + CHITIETCHAMCONG.MASP + ") REFERENCES SANPHAM(MASP) \n" +
                ");";
        db.execSQL(createTblCONGNHAN);
        db.execSQL(createTblSANPHAM);
        db.execSQL(createTblCHAMCONG);
        db.execSQL(createTblCHITIETCHAMCONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    //Dùng cho INSERT, UPDATE, DELETE,... tóm lại là các lệnh ko cần trả về
    public void executeUpdate(String sql){
        SQLiteDatabase dbChamCong = getWritableDatabase();
        dbChamCong.execSQL(sql);
    }

    //Dùng cho SELECT
    public Cursor executeQuery(String sql){
        SQLiteDatabase dbChamCong = getReadableDatabase();
        return dbChamCong.rawQuery(sql,null);
    }
}
