package com.nhom6.appchamcong.media;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.nhom6.appchamcong.Entity.SANPHAM;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UploadImage {
    Cloudinary cloudinary=null;
    public void initCloudinary(Context context){
        HashMap<String, String> config= new HashMap<>();
        config.put("cloud_name","dnnl3cxsn");
        config.put("api_key","252314296866242");
        config.put("api_secret","pSxK7XysZNFEoJJvQJJA6GuyyLk");
        MediaManager.init(context,config);
//        cloudinary=new Cloudinary("cloudinary://252314296866242:pSxK7XysZNFEoJJvQJJA6GuyyLk@dnnl3cxsn");

    }
     @RequiresApi(api = Build.VERSION_CODES.N)
     public void uploadImageToCloudinary(Context context, String filePath){

             MediaManager.get().upload(filePath).callback((UploadCallback)(new UploadCallback() {
                 public void onSuccess(@Nullable String requestId, @Nullable Map resultData) {
                     Log.d("onSuccess_onSuccess","onSuccess: "+resultData);
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

}
