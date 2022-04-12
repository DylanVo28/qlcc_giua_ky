package com.nhom6.appchamcong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CHITIETCHAMCONG;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Entity.SANPHAM;
import com.nhom6.appchamcong.adapter.CTChamCongAdapter;
import com.nhom6.appchamcong.adapter.SpinnerSanPhamAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietChamCongActivity extends AppCompatActivity {

    private String maCC;
    private ArrayList<CHITIETCHAMCONG> dsCtcc = new ArrayList<>();
    private DAO dao = new DAO();
    private CONGNHAN congnhan;
    private TextView txtTenCN, txtMaCN, txtMaCC, txtTongTienCong;
    private ListView lvDsSanPhamCc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ctchamcong);

        this.maCC = this.getIntent().getStringExtra("macc");
        this.congnhan = (CONGNHAN) getIntent().getSerializableExtra("congnhan");
        dsCtcc = dao.getDsCtChamCong(ChiTietChamCongActivity.this, maCC);
        setControl();

        txtTenCN.setText(congnhan.getHoCN() + " " + congnhan.getTenCN());
        txtMaCN.setText(congnhan.getMaCN());
        txtMaCC.setText(maCC);
    }

    private void setControl() {
        txtTenCN = findViewById(R.id.txtTenCn);
        txtMaCN = findViewById(R.id.txtMaCn);
        txtMaCC = findViewById(R.id.txtMaCC);
        lvDsSanPhamCc = findViewById(R.id.lvDsSanPhamCc);
        txtTongTienCong = findViewById(R.id.txtTongTien);
        lvDsSanPhamCc.setAdapter(new CTChamCongAdapter(dsCtcc, ChiTietChamCongActivity.this, txtTongTienCong));
    }

    public void themMoi(MenuItem item) {
        BottomSheetDialog dialog = new BottomSheetDialog(ChiTietChamCongActivity.this, R.style.Theme_Design_BottomSheetDialog);
        View dialogView = LayoutInflater.from(ChiTietChamCongActivity.this).inflate(R.layout.dialog_them_ctcc, null);

        Spinner spnSanPham = dialogView.findViewById(R.id.spnSanPham);
        EditText editSoTP = dialogView.findViewById(R.id.editSoTP);
        EditText editSoPP = dialogView.findViewById(R.id.editSoPP);
        ImageView imgSanPham = dialogView.findViewById(R.id.imgSanPham);
        Button btnXacNhanThem = dialogView.findViewById(R.id.btnXacNhanThem);

        ArrayList<SANPHAM> dsSpCc = dao.getSanphamCc(ChiTietChamCongActivity.this, maCC);
        spnSanPham.setAdapter(new SpinnerSanPhamAdapter(ChiTietChamCongActivity.this, R.layout.row_spnsanpham, dsSpCc));

        spnSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SANPHAM selectedSp = (SANPHAM) adapterView.getSelectedItem();
                Picasso.get().load(selectedSp.getImg()).into(imgSanPham);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        btnXacNhanThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soTP = Integer.parseInt(String.valueOf(editSoTP.getText()));
                int soPP = Integer.parseInt(String.valueOf(editSoPP.getText()));

                SANPHAM selectedSp = (SANPHAM) spnSanPham.getSelectedItem();
                CHITIETCHAMCONG ctcc = new CHITIETCHAMCONG(maCC, selectedSp.getMaSP(), soTP, soPP);

                boolean rs = dao.themCtChamCong(ChiTietChamCongActivity.this, ctcc);
                if (rs) {
                    ((CTChamCongAdapter) lvDsSanPhamCc.getAdapter()).reload(maCC);
                    Toast.makeText(ChiTietChamCongActivity.this, "Đã thêm sản phẩm chấm công mới", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        dialog.setContentView(dialogView);
        dialog.show();
    }

    public void back(View view) {
        onBackPressed();
    }
}