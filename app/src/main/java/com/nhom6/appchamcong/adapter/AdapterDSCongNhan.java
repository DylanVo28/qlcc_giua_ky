package com.nhom6.appchamcong.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nhom6.appchamcong.EditCongNhanActiviy;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Fragments.CongNhanFragment;
import com.nhom6.appchamcong.MainActivity;
import com.nhom6.appchamcong.R;

import java.util.ArrayList;

public class AdapterDSCongNhan extends ArrayAdapter<CONGNHAN> {

    CongNhanFragment context;
    int resource;
    ArrayList<CONGNHAN> dsCongNhan;
    Integer requestCodeEdit = 2;

    public AdapterDSCongNhan(@NonNull CongNhanFragment context, int resource, @NonNull ArrayList<CONGNHAN> dsCongNhan) {
        super(context.requireContext(), resource, dsCongNhan);
        this.context = context;
        this.resource = resource;
        this.dsCongNhan = dsCongNhan;
    }

    @Nullable
    @Override
    public CONGNHAN getItem(int position) {
        return dsCongNhan.get(position);
    }

    @Override
    public int getCount() {
        return dsCongNhan.size();
    }

    private static class ViewHolder {
        public TextView maCNChiTiet;
        public TextView hoVaTenCNChiTiet;
        public TextView phanXuongCNChiTiet;
        public Button btnChiTietCN;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context.requireContext()).inflate(resource, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.maCNChiTiet = convertView.findViewById(R.id.maCNItem);
        viewHolder.hoVaTenCNChiTiet = convertView.findViewById(R.id.hoVaTenCNItem);
        viewHolder.phanXuongCNChiTiet = convertView.findViewById(R.id.phanXuongCNItem);
        viewHolder.btnChiTietCN = convertView.findViewById(R.id.btnChiTietCNItem);
        CONGNHAN congnhan = dsCongNhan.get(position);
        viewHolder.maCNChiTiet.setText(congnhan.getMaCN());
        viewHolder.hoVaTenCNChiTiet.setText(congnhan.getHoCN()+ " " +congnhan.getTenCN());
        viewHolder.phanXuongCNChiTiet.setText(String.valueOf(congnhan.getPhanXuong()));
        convertView.setTag(viewHolder);

        CONGNHAN cn = dsCongNhan.get(position);

        viewHolder.btnChiTietCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.requireContext(), EditCongNhanActiviy.class);
                CONGNHAN cn = new CONGNHAN(viewHolder.maCNChiTiet.getText().toString(),congnhan.getHoCN(),congnhan.getTenCN(),congnhan.getPhanXuong());
                intent.putExtra("CONGNHAN", cn);
             //   context.startActivity(intent);
                context.startActivityForResult(intent, requestCodeEdit);
            }
        });

        return convertView;
    }

    public void changeDsCongNhan(ArrayList<CONGNHAN> DsCongNhan){
        this.dsCongNhan = DsCongNhan;
        notifyDataSetChanged();
    }
}
