package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.KerangjangProdukAdapter;
import com.klikeat.p2p.klikeat.model.KeranjangBelanjaProdukModel;

import java.util.ArrayList;

public class KeranjangActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatCheckBox checkBoxKeranjang;
    RecyclerView rvKeranjang;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    ArrayList<KeranjangBelanjaProdukModel> keranjangBelanjaProdukModels;
    TextView tvSubtotal;
    Button btnCheckOut;
    ImageView btnBack;
    KerangjangProdukAdapter kerangjangProdukAdapter;
    LinearLayout constraintLayout;
    Util util;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        util = new Util(this);
        checkBoxKeranjang = findViewById(R.id.checkbox_keranjang_activity);
        tvSubtotal = findViewById(R.id.tv_subtotal);
        btnCheckOut = findViewById(R.id.btn_to_checkout);
        rvKeranjang = findViewById(R.id.rv_keranjang);
        btnBack = findViewById(R.id.iv_back_keranjang);
        btnBack.setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar_keranjang);
        constraintLayout = findViewById(R.id.constraintLayout);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            mUserIntansce = FirebaseDatabase.getInstance();
            mUserDatabase = mUserIntansce.getReference().child("user");
            userId = mAuth.getUid();
            btnCheckOut.setOnClickListener(this);
            loadDataKeranjang(userId);
            loadDataSubtotal(userId);
        }else {
            constraintLayout.setVisibility(View.INVISIBLE);
            checkBoxKeranjang.setVisibility(View.INVISIBLE);

        }

    }
    private void beliSekarang(String fotoToko, String namaToko, String namaProduk, String fotoProduk,
                              String hargaProduk, String hargaPengiriman, String catatan, String subtotal,
                              String status, String produkId){
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("fotoToko").setValue(fotoToko);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("namaToko").setValue(namaToko);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("namaProduk").setValue(namaProduk);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("fotoProduk").setValue(fotoProduk);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("hargaProduk").setValue(hargaProduk);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("hargaPengiriman").setValue(hargaPengiriman);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("catatan").setValue(catatan);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("subtotal").setValue(subtotal);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("status").setValue(status);
        mUserDatabase.child(userId).child("pembelian").child(produkId).child("produkId").setValue(produkId);
    }

    private void loadDataKeranjang(String id){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(id).child("keranjang").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keranjangBelanjaProdukModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        KeranjangBelanjaProdukModel keranjangBelanjaProdukModel = dataSnapshot1.getValue(KeranjangBelanjaProdukModel.class);
                        keranjangBelanjaProdukModels.add(keranjangBelanjaProdukModel);
                    }
                    if (keranjangBelanjaProdukModels.size() == 0){
                        constraintLayout.setVisibility(View.INVISIBLE);
                        checkBoxKeranjang.setVisibility(View.INVISIBLE);
                    }else{
                        rvKeranjang.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this, LinearLayoutManager.VERTICAL, false));
                        kerangjangProdukAdapter = new KerangjangProdukAdapter(KeranjangActivity.this, keranjangBelanjaProdukModels);
                        rvKeranjang.setAdapter(kerangjangProdukAdapter);
                        kerangjangProdukAdapter.notifyDataSetChanged();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadDataSubtotal(String id){
        mUserDatabase.child(id).child("keranjang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keranjangBelanjaProdukModels = new ArrayList<>();
                int subTotal = 0;
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        KeranjangBelanjaProdukModel keranjangBelanjaProdukModel = dataSnapshot1.getValue(KeranjangBelanjaProdukModel.class);
                        keranjangBelanjaProdukModels.add(keranjangBelanjaProdukModel);
                    }
                    for (int i = 0; i < keranjangBelanjaProdukModels.size(); i++){
                        subTotal = subTotal + (Integer.parseInt(keranjangBelanjaProdukModels.get(i).getHargaProduk())
                                * Integer.parseInt(keranjangBelanjaProdukModels.get(i).getJumlahPembelian()));
                    }
                    if (keranjangBelanjaProdukModels.size() == 0){
                        constraintLayout.setVisibility(View.INVISIBLE);
                        checkBoxKeranjang.setVisibility(View.INVISIBLE);
                    }else{
                        tvSubtotal.setText(util.convertToIdr(subTotal));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_to_checkout :{
                String hargaPengiriman = "12000";
                String catatan = "";
                for (int i = 0; i < keranjangBelanjaProdukModels.size();i++){
                    beliSekarang(keranjangBelanjaProdukModels.get(i).getFotoPenjual(),
                            keranjangBelanjaProdukModels.get(i).getNamaToko(),
                            keranjangBelanjaProdukModels.get(i).getNamaProduk(),
                            keranjangBelanjaProdukModels.get(i).getFotoProduk(),
                            keranjangBelanjaProdukModels.get(i).getHargaProduk(),
                            hargaPengiriman,
                            catatan,
                            String.valueOf(Integer.parseInt(hargaPengiriman)
                                    +Integer.parseInt(keranjangBelanjaProdukModels.get(i).getHargaProduk())),
                            "Menunggu pembayaran",
                            keranjangBelanjaProdukModels.get(i).getProdukId());
                }
                startActivity(new Intent(this,CheckOutActivity.class));
                break;
            }
            case R.id.iv_back_keranjang:{
                finish();
                break;
            }
        }
    }
}
