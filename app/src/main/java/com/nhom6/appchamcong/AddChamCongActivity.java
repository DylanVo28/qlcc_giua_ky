package com.nhom6.appchamcong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CHAMCONG;

import java.util.ArrayList;

public class AddChamCongActivity extends AppCompatActivity {
    private DAO dao = new DAO();
    private ListView lvChamCong;
    TextView txtMaCC;
    EditText txtHoCC, txtTenCN, txtPhanXuong;
    Button btnThemCC;
    String maCN;
    int max;

    static ArrayList<CHAMCONG> chamcongs = new ArrayList<CHAMCONG>();

    private BottomSheetDialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cham_cong);
        Bundle bundle = getIntent().getExtras();
        maCN = bundle.getString("MACNCT");

       // ruui do b
        setControl();
        setEvent();


    }

    private  void  setEvent() {
        btnThemCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.create_chamcong_btn: {

                        dialog = new BottomSheetDialog(view.getContext());
                        dialog.setContentView(R.layout.dialog_create_ngaychamcong);
                        dialog.findViewById(R.id.delete_cc_btn).setVisibility(View.GONE);

                        dialog.findViewById(R.id.btn_save_ngaycc).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EditText ngaycc = (EditText) dialog.findViewById(R.id.edit_ngaycc);
                                String maCC = java.util.UUID.randomUUID().toString();

                                CHAMCONG chamcong = new CHAMCONG();
                                chamcong.setNgayChamCong(ngaycc.getText().toString());
                                chamcong.setMaCN(maCN);
                                chamcong.setMaCC(maCC);

                                try {
                                    dao.themChamCong(getBaseContext(),chamcong);
                                    Toast.makeText(getBaseContext(), "Thêm ngày chấm công thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }catch (Exception e){
                                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.show();
                    }
                    case R.id.btn_save_ngaycc: {

                        break;
                    }

                }
            }
        });
    }
    private void setControl() {
        btnThemCC = findViewById(R.id.create_chamcong_btn);
    }
}
