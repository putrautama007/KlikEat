package com.klikeat.p2p.klikeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;

public class KeranjangActivity extends AppCompatActivity {
    AppCompatCheckBox checkBoxKeranjang;
    RecyclerView rvKeranjang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        checkBoxKeranjang = findViewById(R.id.checkbox_keranjang_activity);
        rvKeranjang = findViewById(R.id.rv_keranjang);
    }

    private void loadData(String id){

    }
}
