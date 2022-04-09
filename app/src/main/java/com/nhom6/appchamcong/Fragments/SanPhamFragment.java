package com.nhom6.appchamcong.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.R;
import com.nhom6.appchamcong.adapter.SanPhamAdapter;
import com.nhom6.appchamcong.media.UriUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

public class SanPhamFragment extends Fragment  implements View.OnClickListener {

    static ArrayList<SANPHAM> sanphams = new ArrayList<SANPHAM>();
    private ListView lvSanphams;
    private DAO dao = new DAO();
    private SanPhamAdapter aa=null;
    private BottomSheetDialog dialog=null;
    private BottomSheetDialog dialogEdit=null;
    private static SanPhamFragment instance;
    private Intent image=null;
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
//


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

            ImageView imgSanPham=dialog.findViewById(R.id.img_sanpham);
            imgSanPham.getLayoutParams().width=400;
            imgSanPham.getLayoutParams().height=400;

            Uri uri=data.getData();
            Glide.with(getActivity()).load(uri).into(imgSanPham);
            image=data;
        }
        if (requestCode == 101 && resultCode==Activity.RESULT_OK) {
            ImageView imgSanPham=dialogEdit.findViewById(R.id.img_sanpham);
            imgSanPham.getLayoutParams().width=400;
            imgSanPham.getLayoutParams().height=400;

            Uri uri=data.getData();
            Glide.with(getActivity()).load(uri).into(imgSanPham);
            image=data;
        }
    }

    public Intent getImage(){
        return image;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_san_pham_btn:{
                dialog=new BottomSheetDialog(view.getContext());

                dialog.setContentView(R.layout.dialog_create_sanpham);
                ImageView imgDefault=(ImageView) dialog.findViewById(R.id.img_sanpham);
                imgDefault.getLayoutParams().width=400;
                imgDefault.getLayoutParams().height=400;
                Glide.with(getActivity()).load("https://skillz4kidzmartialarts.com/wp-content/uploads/2017/04/default-image-620x600.jpg")
                        .into(imgDefault);

                dialog.findViewById(R.id.delete_sp_btn).setVisibility(View.GONE);
                dialog.findViewById(R.id.btn_select_img_sanpham).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        selectImage("CREATE",dialog);
                    }
                });
                dialog.findViewById(R.id.btn_save_sp).setOnClickListener(new View.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View view){
                        verifyStoragePermissions(getActivity());
                        image.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri fileUri=image.getData();
                        String filePath=UriUtils.getPathFromUri(getContext(),fileUri);

                        EditText tensp=(EditText)dialog.findViewById(R.id.edit_ten_sp);
                        EditText giasp=(EditText) dialog.findViewById(R.id.edit_gia_sp);
                        String idsp=java.util.UUID.randomUUID().toString();

                        MediaManager.get().upload(filePath).callback((UploadCallback)(new UploadCallback() {
                            public void onSuccess(@Nullable String requestId, @Nullable Map resultData) {
                                Log.d("onSuccess_onSuccess","onSuccess: "+resultData);
                                SANPHAM sp=new SANPHAM(idsp,tensp.getText().toString(),
                                        Integer.parseInt(giasp.getText().toString()), (String) resultData.get("secure_url"));
                                dao.themSanPham(getContext(),sp);
                                AbsoluteLayout al=dialog.findViewById(R.id.layout_dialog_sanpham);
                                al.setAlpha(1F);
                                LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
                                animationView.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                sanphams.add(sp);
                                aa.notifyDataSetChanged();
                                dialog.dismiss();
                            }

                            public void onProgress(@Nullable String requestId, long bytes, long totalBytes) {
                                Log.d("onProgress_onProgress","onProgress: "+totalBytes+" "+requestId+" "+bytes);

                            }

                            public void onReschedule(@Nullable String requestId, @Nullable ErrorInfo error) {
                                Log.d("reschedule_reschedule","reschedule: "+error+" "+requestId);
                            }

                            public void onError(@Nullable String requestId, @Nullable ErrorInfo error) {
                                Log.d("error_error","error: "+ error);

                            }

                            public void onStart(@Nullable String requestId) {
                                Log.d("start_start","start: "+ requestId);
                                LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
                                animationView.setVisibility(View.VISIBLE);
                                AbsoluteLayout al=dialog.findViewById(R.id.layout_dialog_sanpham);
                                al.setAlpha(0.5F);
                            }
                        })).dispatch();




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
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}