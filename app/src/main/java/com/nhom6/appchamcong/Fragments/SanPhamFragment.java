package com.nhom6.appchamcong.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.R;
import com.nhom6.appchamcong.adapter.SanPhamAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class SanPhamFragment extends Fragment  implements View.OnClickListener {

    static ArrayList<SANPHAM> sanphams = new ArrayList<SANPHAM>();
    private ListView lvSanphams;
    private DAO dao = new DAO();
    private SanPhamAdapter aa=null;
    private BottomSheetDialog dialog=null;
    private BottomSheetDialog dialogEdit=null;
    private static SanPhamFragment instance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View view=inflater.inflate(R.layout.frag_san_pham,container,false);
        instance=this;

        lvSanphams=(ListView) view.findViewById(R.id.sanphams);


        sanphams=dao.getSanphams(view.getContext());

        aa = new SanPhamAdapter(view.getContext(), sanphams);

        lvSanphams.setAdapter(aa);
 view.findViewById(R.id.create_san_pham_btn).setOnClickListener(this);
        EditText search_sp=(EditText) view.findViewById(R.id.search_sp_input);
        search_sp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    ArrayList<SANPHAM> searchSanPhams=dao.searchSP(view.getContext(),search_sp.getText().toString());
                    sanphams=searchSanPhams;
                    aa = new SanPhamAdapter(view.getContext(), sanphams);

                    lvSanphams.setAdapter(aa);
                    return true;
                }
                return false;
            }
        });
//        lvSanphams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//
//                SANPHAM sp=sanphams.get(position);
//            }
//        });


        return view;
    }

    public static SanPhamFragment getInstance(){
        return instance;
    }

    public void selectImage(String type,BottomSheetDialog dialog){


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if(type.equals("CREATE")){
            startActivityForResult(intent, 100);

        }
        if(type.equals("EDIT")){
            dialogEdit=dialog;
            startActivityForResult(intent, 101);
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 100 && resultCode==Activity.RESULT_OK) {
                //Get image
//                ImageView imgSanPham=dialog.findViewById(R.id.img_sanpham);
//                Bitmap imgBitmap=(Bitmap) data.getExtras().get("data");
//                imgSanPham.setImageBitmap(imgBitmap);
            ImageView imgSanPham=dialog.findViewById(R.id.img_sanpham);
            imgSanPham.getLayoutParams().width=400;
            imgSanPham.getLayoutParams().height=400;

            Uri uri=data.getData();
            Glide.with(getActivity()).load(uri).into(imgSanPham);

        }
        if (requestCode == 101 && resultCode==Activity.RESULT_OK) {
            ImageView imgSanPham=dialogEdit.findViewById(R.id.img_sanpham);
            imgSanPham.getLayoutParams().width=400;
            imgSanPham.getLayoutParams().height=400;

            Uri uri=data.getData();
            Glide.with(getActivity()).load(uri).into(imgSanPham);
        }
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_san_pham_btn:{
                dialog=new BottomSheetDialog(view.getContext());
                dialog.setContentView(R.layout.dialog_create_sanpham);
                dialog.findViewById(R.id.delete_sp_btn).setVisibility(View.GONE);
                dialog.findViewById(R.id.btn_select_img_sanpham).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        selectImage("CREATE",dialog);
                    }
                });
                dialog.findViewById(R.id.btn_save_sp).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        EditText tensp=(EditText)dialog.findViewById(R.id.edit_ten_sp);
                        EditText giasp=(EditText) dialog.findViewById(R.id.edit_gia_sp);
                        String idsp=java.util.UUID.randomUUID().toString();
                        SANPHAM sp=new SANPHAM(idsp,tensp.getText().toString(),
                                Integer.parseInt(giasp.getText().toString()),
                                "https://www.toponseek.com/blogs/wp-content/uploads/2019/06/toi-uu-hinh-anh-optimize-image-4-1200x700.jpg");
                        try {
                            dao.themSanPham(getContext(),sp);
                            Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            sanphams.add(sp);
                            aa.notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();

                break;
            }
            case R.id.btn_save_sp:{

                break;
            }

        }

    }
}