package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.MetodePembayaranAdapter;
import com.klikeat.p2p.klikeat.model.MetodePembayaranModel;

import java.util.ArrayList;

public class PembayaranActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvTranferBank, rvTranferVirtual;
    MetodePembayaranAdapter adapter;
    ArrayList<MetodePembayaranModel> metodePembayaranModels;
    DatabaseReference mTranferBankDatabase ,mTransferVirtualDatabase,mUserDatabase;
    FirebaseDatabase mTransferIntansce, mTransferVirtualIntansce,mUserIntansce;
    ImageView btnBack;
    String pembelianId,userId;
    Util util;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        util = new Util(this);
        progressBar = findViewById(R.id.progressbar_pembayaran);
        rvTranferBank = findViewById(R.id.rv_transfer_bank);
        rvTranferVirtual = findViewById(R.id.rv_transfer_virtual);
        btnBack = findViewById(R.id.iv_back_pembayaran);
        btnBack.setOnClickListener(this);
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        pembelianId = getIntent().getStringExtra("transaksiId");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();

        mTransferIntansce = FirebaseDatabase.getInstance();
        mTranferBankDatabase = mTransferIntansce.getReference("transferBank");
        mTransferVirtualIntansce = FirebaseDatabase.getInstance();
        mTransferVirtualDatabase = mTransferVirtualIntansce.getReference("transferVirtual");
        loadMetodeTransferBank();
        loadMetodeTransferVirtualBank();
    }

    private void loadMetodeTransferBank(){
        progressBar.setVisibility(View.VISIBLE);
        mTranferBankDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metodePembayaranModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        MetodePembayaranModel metodePembayaranModel = dataSnapshot1.getValue(MetodePembayaranModel.class);
                        metodePembayaranModels.add(metodePembayaranModel);
                    }
                    rvTranferBank.setLayoutManager(new LinearLayoutManager(PembayaranActivity.this, LinearLayoutManager.VERTICAL, false));
                    adapter = new MetodePembayaranAdapter(PembayaranActivity.this, metodePembayaranModels);
                    rvTranferBank.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvTranferBank.setAdapter(adapter);
                    rvTranferBank.smoothScrollBy(0, 100);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void loadMetodeTransferVirtualBank(){
        mTransferVirtualDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metodePembayaranModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        MetodePembayaranModel metodePembayaranModel = dataSnapshot1.getValue(MetodePembayaranModel.class);
                        metodePembayaranModels.add(metodePembayaranModel);
                    }
                    rvTranferVirtual.setLayoutManager(new LinearLayoutManager(PembayaranActivity.this, LinearLayoutManager.VERTICAL, false));
                    adapter = new MetodePembayaranAdapter(PembayaranActivity.this, metodePembayaranModels);
                    rvTranferVirtual.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvTranferVirtual.setAdapter(adapter);
                    rvTranferVirtual.smoothScrollBy(0, 100);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeDataPembelian(String idPembelian,String idUser){
        Query removeBeliSekrangQuerry;
        removeBeliSekrangQuerry= mUserDatabase.child(idUser).child("riwayat").child(idPembelian);
        removeBeliSekrangQuerry.getRef().removeValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_pembayaran :{
                removeDataPembelian(pembelianId,userId);
                util.clearPembelian();
                finish();
                break;
            }
        }
    }
}
