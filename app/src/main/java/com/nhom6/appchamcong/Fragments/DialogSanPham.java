package com.nhom6.appchamcong.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.R;

public class DialogSanPham extends Fragment  implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewDialogSanPham=inflater.inflate(R.layout.dialog_create_sanpham,container,false);


        viewDialogSanPham.findViewById(R.id.btn_select_img_sanpham).setOnClickListener(this);
//        view.findViewById(R.id.btn_save_sp).setOnClickListener(this);

        return viewDialogSanPham;
    }

    void selectImage(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_select_img_sanpham:{
                selectImage();
                break;
            }
        }
    }
}
