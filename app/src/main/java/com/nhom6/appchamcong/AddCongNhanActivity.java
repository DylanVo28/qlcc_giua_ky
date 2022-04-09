package com.nhom6.appchamcong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nhom6.appchamcong.adapter.AdapterDSCongNhan;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CONGNHAN;

public class AddCongNhanActivity extends AppCompatActivity {

    private DAO dao = new DAO();
    private AdapterDSCongNhan adapterDSCongNhan;
    private ListView lvCongNhan;
    TextView txtMaCN;
    EditText txtHoCN, txtTenCN, txtPhanXuong;
    Button btnThem;
    int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_cong_nhan);
        setControl();
        setEvent();
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("max")) {
           // String tmp = bundle.getString("max");
            max = Integer.parseInt(getIntent().getStringExtra("max"));
        }
        txtMaCN.setText("CN0"+(max+1));
    }

    private  void  setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CONGNHAN congnhan = new CONGNHAN();
                congnhan.setMaCN(txtMaCN.getText().toString());
                congnhan.setHoCN(txtHoCN.getText().toString());
                congnhan.setTenCN(txtTenCN.getText().toString());
                congnhan.setPhanXuong(Integer.parseInt(String.valueOf(txtPhanXuong.getText())));
                try {
                    dao.themCongNhan(getBaseContext(),congnhan);
                    Toast.makeText(getBaseContext(), "Thêm công nhân thành công", Toast.LENGTH_SHORT).show();

                    final Intent data = new Intent();
                    data.putExtra("maCNADD",txtMaCN.getText().toString());
                    data.putExtra("hoCNADD",txtHoCN.getText().toString());
                    data.putExtra("tenCNADD",txtTenCN.getText().toString());
                    data.putExtra("phanXuongCNADD", Integer.parseInt(txtPhanXuong.getText().toString()));
                    setResult(RESULT_OK, data);
                    finish();

                }catch (Exception e){
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setControl() {
        txtMaCN = findViewById(R.id.maCNadd);
        txtHoCN = findViewById(R.id.hoCNadd);
        txtTenCN = findViewById(R.id.tenCNadd);
        txtPhanXuong = findViewById(R.id.phanXuongCNadd);
        btnThem = findViewById(R.id.btnADD);
    }

    public void back(View view) {
        onBackPressed();
    }
}
