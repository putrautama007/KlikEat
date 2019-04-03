package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.klikeat.p2p.klikeat.adapter.MakananAdapter;
import com.klikeat.p2p.klikeat.adapter.UlasanProdukAdapter;
import com.klikeat.p2p.klikeat.model.MakananModel;
import com.klikeat.p2p.klikeat.model.UlasanModel;

import java.util.ArrayList;

public class DetailMakananActivity extends AppCompatActivity implements View.OnClickListener {
    MakananModel makananModel;
    ImageView ivProduk,profilePenjual,btnBackDetail;
    TextView tvNamaProduk,hargaProduk,jumlahUlasanProduk,
            deskripsiProduk,jumlahUlasanPenilain, namaPenjual,lokasiPenjual;
    RatingBar ratingBarProduk, ratingBarProdukUlasan,ratingBarPenjual;
    ImageButton btnFavorite;
    LinearLayout liatUlasan;
    ImageButton btnKomen,btnAddToCart;
    Button btnBeliSekarang;
    RecyclerView rvUlasan;

    private DatabaseReference mProdukDetailDatabase;
    private FirebaseDatabase mProdukDetailInstance;
    ArrayList<UlasanModel> ulasanModels;
    UlasanProdukAdapter ulasanProdukAdapter;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        String makananData = getIntent().getStringExtra("produkData");
        makananModel = new Gson().fromJson(makananData,MakananModel.class);
        mProdukDetailInstance = FirebaseDatabase.getInstance();
        mProdukDetailDatabase = mProdukDetailInstance.getReference().child("produk");
        mAuth = FirebaseAuth.getInstance();
        initView();
        loadData(makananModel);
        loadUlasan(makananModel.produk_id);
    }

    private void initView(){
        ivProduk = findViewById(R.id.iv_makanan_detail);
        tvNamaProduk = findViewById(R.id.tv_foodName_detail);
        hargaProduk = findViewById(R.id.tv_harga_detail);
        ratingBarProduk = findViewById(R.id.rating_produk_detail);
        jumlahUlasanProduk = findViewById(R.id.tv_jumlah_ulasan_detail);
        btnFavorite = findViewById(R.id.btn_favorite);
        deskripsiProduk = findViewById(R.id.tv_deskripsi_produk);
        namaPenjual = findViewById(R.id.tv_nama_penjual_detail);
        profilePenjual = findViewById(R.id.iv_profile_penjual);
        namaPenjual = findViewById(R.id.tv_nama_penjual_detail);
        lokasiPenjual = findViewById(R.id.tv_lokasi_penjual_detail);
        ratingBarPenjual = findViewById(R.id.rating_penjual);
        ratingBarProdukUlasan = findViewById(R.id.rating_makanan_penilaian);
        jumlahUlasanPenilain = findViewById(R.id.tv_jumlah_ulasan_penilaian);
        liatUlasan = findViewById(R.id.ll_lihat_ulasan);
        btnKomen = findViewById(R.id.btn_komen);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);
        btnBeliSekarang = findViewById(R.id.btn_beli);
        btnBackDetail = findViewById(R.id.iv_back_detail);
        rvUlasan = findViewById(R.id.rv_ulasan_produk);

        btnFavorite.setOnClickListener(this);
        btnBeliSekarang.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnKomen.setOnClickListener(this);
        liatUlasan.setOnClickListener(this);
        btnBackDetail.setOnClickListener(this);
    }

    private void loadData(MakananModel makananModel){
        tvNamaProduk.setText(makananModel.nama_produk);
        hargaProduk.setText(makananModel.harga);
        ratingBarProduk.setRating(Float.parseFloat(makananModel.rating));
        jumlahUlasanProduk.setText("("+makananModel.jumlahUlasan+")");
        deskripsiProduk.setText(makananModel.deskripsi);
        namaPenjual.setText(makananModel.penjual);
        lokasiPenjual.setText(makananModel.lokasi_penjual);
        ratingBarProdukUlasan.setRating(Float.parseFloat(makananModel.rating));
        jumlahUlasanPenilain.setText("("+makananModel.jumlahUlasan+")");
    }

    private void loadUlasan(String id){
        mProdukDetailDatabase.child(id).child("ulasan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulasanModels = new ArrayList<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        UlasanModel ulasanModel = tiapDataSnapshot.getValue(UlasanModel.class);
                        ulasanModels.add(ulasanModel);
                    }
                    rvUlasan.setLayoutManager(new LinearLayoutManager(DetailMakananActivity.this, LinearLayoutManager.VERTICAL, false));
                    ulasanProdukAdapter = new UlasanProdukAdapter(DetailMakananActivity.this, ulasanModels);
                    rvUlasan.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvUlasan.setAdapter(ulasanProdukAdapter);
                    ulasanProdukAdapter.notifyDataSetChanged();
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_favorite :{
                if (mAuth.getCurrentUser() != null) {

                }else {
                    startActivity(new Intent(DetailMakananActivity.this,LoginActivity.class));
                }
                break;
            }
            case R.id.btn_beli :{
                if (mAuth.getCurrentUser() != null) {

                }else {
                    startActivity(new Intent(DetailMakananActivity.this,LoginActivity.class));
                }
                break;
            }
            case R.id.btn_add_to_cart :{
                if (mAuth.getCurrentUser() != null) {

                }else {
                    startActivity(new Intent(DetailMakananActivity.this,LoginActivity.class));
                }
                break;
            }
            case R.id.btn_komen :{
                if (mAuth.getCurrentUser() != null) {

                }else {
                    startActivity(new Intent(DetailMakananActivity.this,LoginActivity.class));
                }
                break;
            }
            case R.id.ll_lihat_ulasan :{
                intent = new Intent(this,PenilaianActivity.class);
                intent.putExtra("produkId",makananModel.produk_id);
                startActivity(intent);
                break;
            }
            case R.id.iv_back_detail :{
                finish();
                break;
            }
        }
    }

    private void addToKeranjang(){

    }
}
