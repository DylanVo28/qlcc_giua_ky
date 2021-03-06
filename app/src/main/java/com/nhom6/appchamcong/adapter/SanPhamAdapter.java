package com.nhom6.appchamcong.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.Fragments.SanPhamFragment;
import com.nhom6.appchamcong.R;
import com.nhom6.appchamcong.media.UriUtils;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class SanPhamAdapter extends BaseAdapter  {
    private Context context;
    private LayoutInflater li;
    private ArrayList<SANPHAM> sanphams;
    private DAO dao = new DAO();

    public SanPhamAdapter(Context context, ArrayList<SANPHAM> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {

        return sanphams.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder {
        public ImageView imgsp;
        public TextView masp;
        public TextView tensp;
        public TextView giasp;
        public ImageButton editsp;
    }

    public static void showLoading(BottomSheetDialog dialog){
        LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);

        AbsoluteLayout al=dialog.findViewById(R.id.layout_dialog_sanpham);
        al.setAlpha(0.5F);
    }

    public void hideLoading(BottomSheetDialog dialog){
        LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
        animationView.setVisibility(View.GONE);

        AbsoluteLayout al=dialog.findViewById(R.id.layout_dialog_sanpham);
        al.setAlpha(1F);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view == null) {
            view = li.inflate(R.layout.row_sanpham, null);
            vh = new ViewHolder();
            vh.imgsp= (ImageView) view.findViewById(R.id.img_sp);
            vh.masp = (TextView) view.findViewById(R.id.masp);
            vh.tensp = (TextView) view.findViewById(R.id.tensp);
            vh.giasp = (TextView) view.findViewById(R.id.giasp);
            vh.editsp= (ImageButton) view.findViewById(R.id.edit_sp_btn);
            view.setTag(vh);
        }
        else{
            vh = (ViewHolder) view.getTag();
        }

        SANPHAM m = sanphams.get(i);

        try {
            URL url = new URL(m.getImg());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            vh.imgsp.setImageBitmap(bmp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        vh.editsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog=new BottomSheetDialog(view.getContext());
                dialog.setContentView(R.layout.dialog_create_sanpham);
                EditText giasp=(EditText) dialog.findViewById(R.id.edit_gia_sp);
                EditText tensp=(EditText) dialog.findViewById(R.id.edit_ten_sp);
                ImageView imgsp=(ImageView) dialog.findViewById(R.id.img_sanpham);
                Button saveSpBtn=(Button) dialog.findViewById(R.id.btn_save_sp);
                Button selectImageBtn=(Button) dialog.findViewById(R.id.btn_select_img_sanpham);
                ImageButton deleteSpBtn=(ImageButton) dialog.findViewById(R.id.delete_sp_btn);

                saveSpBtn.setText("L??u S???n Ph???m");
                try {

                    imgsp.getLayoutParams().width=400;
                    imgsp.getLayoutParams().height=400;
                    imgsp.requestLayout();
                    imgsp.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    URL url = new URL(m.getImg());
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    imgsp.setImageBitmap(bmp);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                tensp.setText(m.getTenSP());
                giasp.setText(""+m.getDonGia());


                saveSpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            showLoading(dialog);
                            if(tensp.getText().toString().equals("")){
                                Toast.makeText(view.getContext(), "T??n s???n ph???m kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                                hideLoading(dialog);
                                return;
                            }
                            if(giasp.getText().toString().equals("")){
                                Toast.makeText(view.getContext(), "Gi?? s???n ph???m kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();

                                hideLoading(dialog);

                                return;
                            }
                            SanPhamFragment.getInstance().verifyStoragePermissions(
                                    SanPhamFragment.getInstance().getActivity());

                            Intent image=SanPhamFragment.getInstance().getImage();
                            if(image!=null){
                                Uri fileUri=image.getData();
                                String filePath= UriUtils.getPathFromUri(view.getContext(),fileUri);

                                MediaManager.get().upload(filePath).callback((UploadCallback)(new UploadCallback() {
                                    public void onSuccess(@Nullable String requestId, @Nullable Map resultData) {
                                        SANPHAM sp=new SANPHAM(m.getMaSP(),tensp.getText().toString(),
                                                Integer.parseInt(giasp.getText().toString()),
                                                (String) resultData.get("secure_url"));
                                        dao.suaSanPham(view.getContext(),sp);

                                        hideLoading(dialog);

                                        Toast.makeText(view.getContext(), "S???a s???n ph???m th??nh c??ng", Toast.LENGTH_SHORT).show();

                                        sanphams= dao.getSanphams(view.getContext());
                                        notifyDataSetChanged();

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

                                    }
                                })).dispatch();
                            }
                            else{



                                SANPHAM sp=new SANPHAM(m.getMaSP(),tensp.getText().toString(),
                                        Integer.parseInt(giasp.getText().toString()),
                                        m.getImg());
                                hideLoading(dialog);

                                if(!dao.suaSanPham(view.getContext(),sp)){
                                    Toast.makeText(view.getContext(), "T??n san ph???m tr??ng v???i c??c s???n ph???m kh??c", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                Toast.makeText(view.getContext(), "S???a s???n ph???m th??nh c??ng", Toast.LENGTH_SHORT).show();
                                sanphams= dao.getSanphams(view.getContext());
                                notifyDataSetChanged();
                                dialog.dismiss();

                            }





                        }catch(Exception e){
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                deleteSpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            dao.xoaSanPham(view.getContext(), m);
                            Toast.makeText(view.getContext(),  "X??a s???n ph???m th??nh c??ng", Toast.LENGTH_SHORT).show();
                            sanphams.remove(m);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }catch (Exception e){
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                selectImageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       SanPhamFragment.getInstance().selectImage("EDIT",dialog);
                    }
                });
                dialog.show();


            }
        });
        vh.masp.setText("M?? sp: "+m.getMaSP());
        vh.tensp.setText("T??n sp: "+m.getTenSP());
        vh.giasp.setText("Gi?? sp: "+m.getDonGia()+" vn??");
        return view;
    }



}
