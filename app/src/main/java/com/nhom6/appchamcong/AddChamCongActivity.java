package com.nhom6.appchamcong;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CHAMCONG;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.adapter.ChamCongAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddChamCongActivity extends AppCompatActivity {
    private DAO dao = new DAO();
    private ListView lvChamCong;
    TextView txtMaCC, txtTenCN, txtMaCN;
    CONGNHAN congnhan;
    String maCN;
    int max;

    static ArrayList<CHAMCONG> dsChamcongs = new ArrayList<CHAMCONG>();

    private BottomSheetDialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cham_cong);
        congnhan = (CONGNHAN) getIntent().getSerializableExtra("congnhan");
        dsChamcongs = dao.getDSChamCong(AddChamCongActivity.this,congnhan.getMaCN());
        setControl();
        txtMaCN.setText(congnhan.getMaCN());
        txtTenCN.setText(congnhan.getHoCN()+" "+congnhan.getTenCN());
        maCN = congnhan.getMaCN();
        setEvent();

    }

    private  void  setEvent() {

        lvChamCong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CHAMCONG cc = ((ChamCongAdapter) lvChamCong.getAdapter()).getItem(i);
                Intent intent = new Intent(AddChamCongActivity.this, ChiTietChamCongActivity.class);
                intent.putExtra("macc",cc.getMaCC());
                intent.putExtra("congnhan",congnhan);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        txtTenCN = findViewById(R.id.txtTenCn);
        txtMaCN = findViewById(R.id.txtMaCn);
        lvChamCong = findViewById(R.id.lvchamcongs);
        lvChamCong.setAdapter(new ChamCongAdapter(AddChamCongActivity.this,dsChamcongs));
    }

    public void themMoi(MenuItem item) {
        dialog = new BottomSheetDialog(AddChamCongActivity.this);
        dialog.setContentView(R.layout.dialog_create_ngaychamcong);
        dialog.findViewById(R.id.delete_cc_btn).setVisibility(View.GONE);

        EditText ngaycc = (EditText) dialog.findViewById(R.id.edit_ngaycc);
        ngaycc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang = calendar.get(Calendar.MONTH);
                int nam = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddChamCongActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        ngaycc.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
            }
        });

        dialog.findViewById(R.id.btn_save_ngaycc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maCC = java.util.UUID.randomUUID().toString();

                CHAMCONG chamcong = new CHAMCONG();
                chamcong.setNgayChamCong(ngaycc.getText().toString());
                chamcong.setMaCN(maCN);
                chamcong.setMaCC(maCC);

                try {
                    dao.themChamCong(AddChamCongActivity.this,chamcong);
                    dsChamcongs = dao.getDSChamCong(AddChamCongActivity.this,congnhan.getMaCN());
                    ((ChamCongAdapter)lvChamCong.getAdapter()).refresh(dsChamcongs);
                    Toast.makeText(AddChamCongActivity.this, "Thêm ngày chấm công thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }catch (Exception e){
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public void back(View view) {
        onBackPressed();
    }
}
