package com.nhom6.appchamcong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhom6.appchamcong.ChiTietChamCongActivity;
import com.nhom6.appchamcong.Entity.CHITIETCHAMCONG;
import com.nhom6.appchamcong.R;

import java.util.ArrayList;

public class CTChamCongAdapter extends BaseAdapter {

    private ArrayList<CHITIETCHAMCONG> dsCtcc = new ArrayList<>();
    private Context context;

    public CTChamCongAdapter(ArrayList<CHITIETCHAMCONG> dsCtcc, Context context) {
        this.dsCtcc = dsCtcc;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dsCtcc.size();
    }

    @Override
    public CHITIETCHAMCONG getItem(int i) {
        return dsCtcc.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.ctcc_row,null);

        return null;
    }
}
