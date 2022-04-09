package com.nhom6.appchamcong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerSanPhamAdapter extends ArrayAdapter<SANPHAM> {

    public SpinnerSanPhamAdapter(@NonNull Context context, int resource, @NonNull List<SANPHAM> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spnsanpham,null);
        TextView tenSp = convertView.findViewById(R.id.txtTenSanPham);
        tenSp.setText(this.getItem(position).getTenSP());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spnsanpham_dropdown,null);
        TextView tenSp = convertView.findViewById(R.id.txtTenSanPham);
        tenSp.setText(this.getItem(position).getTenSP());
        return convertView;
    }
}
