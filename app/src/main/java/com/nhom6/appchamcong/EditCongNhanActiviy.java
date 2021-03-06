package com.nhom6.appchamcong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.Fragments.CongNhanFragment;

public class EditCongNhanActiviy extends AppCompatActivity {

    private DAO dao = new DAO();
    TextView title;
    EditText hoCNEdit, tenCNEdit, phanXuongCNEdit;
    Button btnSua, btnXoa;
    private CONGNHAN cn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_cong_nhan_activiy);

        title = this.findViewById(R.id.titleCNEdit);
        hoCNEdit = this.findViewById(R.id.hoCNChitiet);
        tenCNEdit = this.findViewById(R.id.tenCNChitiet);
        phanXuongCNEdit = this.findViewById(R.id.phanXuongCNChitiet);
        btnSua = this.findViewById(R.id.btnSuaChiTiet);
        btnXoa = this.findViewById(R.id.btnXoaChiTiet);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("CONGNHAN")) {
            cn = (CONGNHAN) bundle.getSerializable("CONGNHAN");
            title.setText(cn.getMaCN());
            hoCNEdit.setText(cn.getHoCN());
            tenCNEdit.setText(cn.getTenCN());
            phanXuongCNEdit.setText(cn.getPhanXuong() + "");
        }

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(EditCongNhanActiviy.this, CongNhanFragment.class);
                    cn.setMaCN(title.getText().toString());
                    cn.setTenCN(tenCNEdit.getText().toString());
                    cn.setHoCN(hoCNEdit.getText().toString());
                    cn.setPhanXuong(Integer.parseInt(phanXuongCNEdit.getText().toString()));
                    dao.suaCongNhan(getBaseContext(), cn);
                    setResult(RESULT_OK);
                    finish();
                } catch (Exception ex) {
                    new AlertDialog.Builder(EditCongNhanActiviy.this)
                            .setTitle("L???i nh???p li???u ")
                            .setMessage("M?? ph??n x?????ng l?? s???")
                            .setPositiveButton("???? hi???u", null).show();
                }
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Intent intent = new Intent(EditCongNhanActiviy.this, MainActivity.class);
                Intent intent = new Intent();
                cn.setMaCN(title.getText().toString());
                cn.setTenCN(tenCNEdit.getText().toString());
                cn.setHoCN(hoCNEdit.getText().toString());
                cn.setPhanXuong(Integer.parseInt(phanXuongCNEdit.getText().toString()));

                new AlertDialog.Builder(EditCongNhanActiviy.this)
                        .setTitle("B???n c?? ch???c mu???n x??a c??ng nh??n " + cn.getMaCN())
                        .setMessage("M???i th??ng tin v??? c??ng nh??n n??y s??? m???t v??nh vi???n")
                        .setPositiveButton("X??c nh???n", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean rs = dao.xoaCongNhan(EditCongNhanActiviy.this, cn);
                                if (rs) {
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            }
                        }).setNegativeButton("H???y", null).show();
            }
        });
    }

    public void back(View view) {
        onBackPressed();
    }
}