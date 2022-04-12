package com.nhom6.appchamcong.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.ChiTietChamCongActivity;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CHITIETCHAMCONG;
import com.nhom6.appchamcong.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CTChamCongAdapter extends BaseAdapter {

    private ArrayList<CHITIETCHAMCONG> dsCtcc = new ArrayList<>();
    private Context context;

    public CTChamCongAdapter(ArrayList<CHITIETCHAMCONG> dsCtcc, Context context) {
        this.dsCtcc = dsCtcc;
        this.context = context;
    }

    private static class ViewHolder {
        public ImageView imgSanPham;
        public TextView txtTenSp;
        public TextView txtDonGia;
        public TextView txtSoTP;
        public TextView txtSoPP;
        public TextView txtTienCong;
        public ImageButton btnSuaCtcc;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        convertView = LayoutInflater.from(context).inflate(R.layout.ctcc_row, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.imgSanPham = convertView.findViewById(R.id.imgSanPham);
        viewHolder.txtTenSp = convertView.findViewById(R.id.txtTenSanPham);
        viewHolder.txtDonGia = convertView.findViewById(R.id.txtDonGia);
        viewHolder.txtSoTP = convertView.findViewById(R.id.txtSoTP);
        viewHolder.txtSoPP = convertView.findViewById(R.id.txtSoPP);
        viewHolder.txtTienCong = convertView.findViewById(R.id.txtTienCong);
        viewHolder.btnSuaCtcc = convertView.findViewById(R.id.btnSuaCtcc);
        convertView.setTag(viewHolder);
        setValues(viewHolder, dsCtcc.get(i));
        setEvent(viewHolder.btnSuaCtcc, dsCtcc.get(i));
        return convertView;
    }

    private void setEvent(ImageButton btnSuaCtcc, CHITIETCHAMCONG ctcc) {
        btnSuaCtcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.Theme_Design_BottomSheetDialog);
                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sua_ctcc, null);

                TextView txtTenSPSua = dialogView.findViewById(R.id.txtTenSPSua);
                EditText editSoTP = dialogView.findViewById(R.id.editSoTP);
                EditText editSoPP = dialogView.findViewById(R.id.editSoPP);
                ImageView imgSpSua = dialogView.findViewById(R.id.imgSanPhamSua);
                Button btnXacNhanSua = dialogView.findViewById(R.id.btnXacNhanSua);

                Picasso.get().load(ctcc.getSp().getImg()).into(imgSpSua);
                txtTenSPSua.setText(ctcc.getSp().getTenSP());
                editSoTP.setText(String.valueOf(ctcc.getSoTP()));
                editSoPP.setText(String.valueOf(ctcc.getSoPP()));

                btnXacNhanSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(context)
                                .setTitle("Sửa sản phẩm chấm công")
                                .setMessage("Lưu ý, thông tin chi tiết chấm công này sẽ bị thay đổi")
                                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dl, int which) {
                                        int soTP = Integer.parseInt(String.valueOf(editSoTP.getText()));
                                        int soPP = Integer.parseInt(String.valueOf(editSoPP.getText()));

                                        ctcc.setSoTP(soTP);
                                        ctcc.setSoPP(soPP);

                                        DAO dao = new DAO();
                                        dao.suaCtChamCong(context, ctcc);
                                        reload(ctcc.getMaCC());
                                        dialog.dismiss();
                                    }

                                })
                                .setNegativeButton("Bỏ", null)
                                .show();
                    }
                });
                dialog.setContentView(dialogView);
                dialog.show();
            }
        });
    }

    private void setValues(ViewHolder viewHolder, CHITIETCHAMCONG ctcc) {
        viewHolder.txtTenSp.setText(ctcc.getSp().getTenSP());
        viewHolder.txtTienCong.setText(String.valueOf(ctcc.getTienCong()));
        Picasso.get().load(ctcc.getSp().getImg()).into(viewHolder.imgSanPham);
        viewHolder.txtDonGia.setText(ctcc.getSp().getDonGia() + "đ");
        viewHolder.txtSoTP.setText(String.valueOf(ctcc.getSoTP()));
        viewHolder.txtSoPP.setText(String.valueOf(ctcc.getSoPP()));
    }

    public void reload(String macc) {
        DAO dao = new DAO();
        dsCtcc = dao.getDsCtChamCong(context, macc);
        notifyDataSetChanged();
    }
}
