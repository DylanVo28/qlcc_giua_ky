package com.nhom6.appchamcong.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nhom6.appchamcong.AddCongNhanActivity;
import com.nhom6.appchamcong.Database.DAO;
import com.nhom6.appchamcong.Entity.CONGNHAN;
import com.nhom6.appchamcong.R;
import com.nhom6.appchamcong.adapter.AdapterDSCongNhan;

import java.util.ArrayList;


public class CongNhanFragment extends Fragment {

    public static ArrayList<CONGNHAN> dsCongNhan = new ArrayList<CONGNHAN>();
    ArrayList<Integer> maCN = new ArrayList<Integer>();
    private ListView lvCongNhan;
    public static DAO dao = new DAO();
    public static AdapterDSCongNhan adapterDSCongNhan;
    private static CongNhanFragment instance;

    Button btnEnter;
    EditText txtTimkiem;
    FloatingActionButton btnAddCN;
    int max;
    Integer requestCodeADD = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.frag_cong_nhan, container, false);
        instance = this;
        lvCongNhan = (ListView) view.findViewById(R.id.lvDsCongNhan);

        dsCongNhan = dao.getDSCongNhan(view.getContext());

        adapterDSCongNhan = new AdapterDSCongNhan(CongNhanFragment.this,R.layout.item_cong_nhan, dsCongNhan);
        lvCongNhan.setAdapter(adapterDSCongNhan);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setControl();
        setEvent();
    }

    private  void setEvent() {
        // Thêm sự kiện cho nút enter
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(txtTimkiem);
                ArrayList<CONGNHAN> searchDSCongNhan = dao.searchCN(getContext(), txtTimkiem.getText().toString());
                dsCongNhan = searchDSCongNhan;
                adapterDSCongNhan.changeDsCongNhan(dsCongNhan);
            }
        });

        btnAddCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCongNhanActivity.class);
                max = getMaCNMax(dsCongNhan);
                Bundle  bundle = new Bundle();
                intent.putExtra("max", max+"");
                startActivityForResult(intent, requestCodeADD);
            }
        });

        lvCongNhan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CONGNHAN cn = ((AdapterDSCongNhan) lvCongNhan.getAdapter()).getItem(i);
                Toast.makeText(requireContext(), cn.getMaCN(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setControl() {
        this.btnEnter = getActivity().findViewById(R.id.btnEnter);
        this.txtTimkiem = getActivity().findViewById(R.id.txtTimkiem);
        this.btnAddCN = getActivity().findViewById(R.id.btnAddCN);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public int getMaCNMax(ArrayList<CONGNHAN> dsCongNhan) {
        String maCNGet;
        max = 0;
        if(dsCongNhan.size()>0) {
            for (CONGNHAN congNhan : dsCongNhan) {
                maCNGet = congNhan.getMaCN();
                maCN.add(Integer.parseInt(maCNGet));
            }
            max = maCN.get(0);

            for (int i = 0; i < maCN.size(); i++) {
                if (maCN.get(i).compareTo(max) > 0) { // i = 0
                    max = maCN.get(i);
                }
            }
        }
        return max;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestCodeADD && data != null)
        {
            dsCongNhan = dao.getDSCongNhan(getActivity().getBaseContext());
            adapterDSCongNhan.changeDsCongNhan(dsCongNhan);
        }

        if (requestCode ==  2) {
            Log.d("result", "onActivityResult: ");
            dsCongNhan = dao.getDSCongNhan(getActivity().getBaseContext());
            adapterDSCongNhan.changeDsCongNhan(dsCongNhan);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}