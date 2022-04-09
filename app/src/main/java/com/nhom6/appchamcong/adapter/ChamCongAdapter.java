package com.nhom6.appchamcong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom6.appchamcong.Entity.CHAMCONG;
import com.nhom6.appchamcong.R;

import java.util.ArrayList;

public class ChamCongAdapter extends BaseAdapter {

    Context context;
    ArrayList<CHAMCONG> dsChamCong;

    public ChamCongAdapter(Context context, ArrayList<CHAMCONG> dsChamCong) {
        this.context = context;
        this.dsChamCong = dsChamCong;
    }

    public class  ViewHolder{
        public TextView txtMaCC;
        public TextView txtNgayCC;
    }

    @Override
    public int getCount() {
        return dsChamCong.size();
    }

    @Override
    public CHAMCONG getItem(int i) {
        return dsChamCong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_chamcong, null);
            vh = new ViewHolder();
            vh.txtMaCC = view.findViewById(R.id.txtMaCC);
            vh.txtNgayCC = view.findViewById(R.id.txtNgayCC);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.txtMaCC.setText(dsChamCong.get(i).getMaCC());
        vh.txtNgayCC.setText(dsChamCong.get(i).getNgayChamCong());
        return view;
    }

    public void refresh(ArrayList<CHAMCONG> dsChamCong){
        this.dsChamCong = dsChamCong;
        notifyDataSetChanged();
    }
}
