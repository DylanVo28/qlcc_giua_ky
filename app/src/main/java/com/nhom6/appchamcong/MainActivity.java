package com.nhom6.appchamcong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Database.DBCHAMCONG;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.Fragments.CongNhanFragment;
import com.nhom6.appchamcong.Fragments.SanPhamFragment;
import com.nhom6.appchamcong.media.UploadImage;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botnavMain;
    CongNhanFragment congNhanFragment = new CongNhanFragment();
    SanPhamFragment sanPhamFragment = new SanPhamFragment();
     HashMap<String, String> config= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UploadImage ui=new UploadImage();
        ui.initCloudinary(this);
        DAO dao = new DAO();

        SANPHAM sp = new SANPHAM("SP02","Áo sơ mi đen",35000,"https://thuvienmuasam.com/uploads/default/original/2X/e/ebace14de8c553f414a830be6bb2b3c13a1194e6.jpeg");
        dao.themSanPham(MainActivity.this,sp);

        botnavMain = findViewById(R.id.bnavMain);
        getSupportFragmentManager().beginTransaction().replace(R.id.lyContainer,congNhanFragment).commit();

        botnavMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.itCongNhan:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.lyContainer,congNhanFragment).commit();
                        return true;
                    }
                    case R.id.itSanPham:{
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.lyContainer,sanPhamFragment).commit();
                        return true;
                    }
                }
                return true;
            }
        });
    }
}