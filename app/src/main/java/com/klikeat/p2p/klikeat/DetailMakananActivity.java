package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.klikeat.p2p.klikeat.adapter.UlasanProdukAdapter;
import com.klikeat.p2p.klikeat.model.FavoriteModel;
import com.klikeat.p2p.klikeat.model.KeranjangBelanjaProdukModel;
import com.klikeat.p2p.klikeat.model.MakananModel;
import com.klikeat.p2p.klikeat.model.PembelianModel;
import com.klikeat.p2p.klikeat.model.UlasanModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailMakananActivity extends AppCompatActivity implements View.OnClickListener {
    MakananModel makananModel;
    ImageView ivProduk, profilePenjual, btnBackDetail;
    TextView tvNamaProduk, hargaProduk, jumlahUlasanProduk,
            deskripsiProduk, jumlahUlasanPenilain, namaPenjual, lokasiPenjual;
    RatingBar ratingBarProduk, ratingBarProdukUlasan, ratingBarPenjual;
    LinearLayout liatUlasan;
    ImageButton btnKomen, btnAddToCart, btnFavorite;
    Button btnBeliSekarang;
    RecyclerView rvUlasan;

    DatabaseReference mProdukDetailDatabase, mUserDatabase,mUlasanDatabase;
    FirebaseDatabase mProdukDetailInstance, mUserIntansce,mUlasanInstance;
    ArrayList<UlasanModel> ulasanModels;
    UlasanProdukAdapter ulasanProdukAdapter;
    FirebaseAuth mAuth;
    String userId,produkId;
    Boolean isFavorite = false;
    FavoriteModel favoriteModel;
    KeranjangBelanjaProdukModel keranjangBelanjaProdukModel;
    Snackbar snackbar;
    ConstraintLayout constraintLayout;
    PembelianModel pembelianModel;
    Util util;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        initView();
        util = new Util(this);
        mProdukDetailInstance = FirebaseDatabase.getInstance();
        mProdukDetailDatabase = mProdukDetailInstance.getReference().child("produk");
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mUlasanInstance = FirebaseDatabase.getInstance();
        mUlasanDatabase = mUlasanInstance.getReference("ulasan");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        String makananData = getIntent().getStringExtra("produkData");
        produkId = getIntent().getStringExtra("produkId");
        checkUserState(makananData);

    }
    private void checkUserState(String makananData){
        if (userId != null) {
            if (produkId != null) {
                checkFavorite(produkId);
                loadData(produkId);
                Log.d("produkId", "onCreate: " + produkId);
                loadUlasan(produkId);
            } else {
                makananModel = new Gson().fromJson(makananData, MakananModel.class);
                checkFavorite(makananModel.produk_id);
                loadData(makananModel);
                loadUlasan(makananModel.produk_id);
            }
        }else {
            if (produkId != null) {
                loadData(produkId);
                Log.d("produkId", "onCreate: " + produkId);
                loadUlasan(produkId);
            } else {
                makananModel = new Gson().fromJson(makananData, MakananModel.class);
                loadData(makananModel);
                loadUlasan(makananModel.produk_id);
            }
        }
    }

    private void initView() {
        progressBar = findViewById(R.id.progressbar_detail_makanan);
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
        constraintLayout = findViewById(R.id.constrainlayout);

        btnFavorite.setOnClickListener(this);
        btnBeliSekarang.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        btnKomen.setOnClickListener(this);
        liatUlasan.setOnClickListener(this);
        btnBackDetail.setOnClickListener(this);
    }

    private void loadData(MakananModel makananModel) {
        tvNamaProduk.setText(makananModel.nama_produk);
        hargaProduk.setText(util.convertToIdr(Integer.parseInt(makananModel.harga)));
        ratingBarProduk.setRating(Float.parseFloat(makananModel.rating));
        jumlahUlasanProduk.setText("(" + makananModel.jumlahUlasan + ")");
        deskripsiProduk.setText(makananModel.deskripsi);
        namaPenjual.setText(makananModel.penjual);
        lokasiPenjual.setText(makananModel.lokasi_penjual);
        ratingBarProdukUlasan.setRating(Float.parseFloat(makananModel.rating));
        jumlahUlasanPenilain.setText("(" + makananModel.jumlahUlasan + ")");
        RoundedCornersTransformation roundedCornersTransformation1 = new RoundedCornersTransformation(100,0);
        Picasso.get().load(makananModel.foto_penjual).fit()
                .centerCrop().transform(roundedCornersTransformation1).into(profilePenjual);
        Glide.with(this).load(makananModel.foto).into(ivProduk);

    }

    private void loadData(String produkId){
        progressBar.setVisibility(View.VISIBLE);
        mProdukDetailDatabase.child(produkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                        makananModel = dataSnapshot.getValue(MakananModel.class);
                        Log.d("isismakananModel","isi:" +makananModel);
                        loadData(makananModel);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUlasan(String produkId) {
        if (produkId != null) {
            mUlasanDatabase.orderByChild("produk_id").equalTo(produkId).addListenerForSingleValueEvent(new ValueEventListener() {
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
                        rvUlasan.smoothScrollBy(0, 100);
                        ulasanProdukAdapter.notifyDataSetChanged();
                    } else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_favorite: {
                if (mAuth.getCurrentUser() != null) {
                    if (!isFavorite) {
                        Log.d("buttonFavorite", "is click");
                        addToFavorite(makananModel.foto_penjual, makananModel.nama_produk, makananModel.penjual, makananModel.produk_id);
                    } else {
                        Log.d("buttonFavorite", "is not click");
                        removeFavorite();
                    }
                } else {
                    startActivity(new Intent(DetailMakananActivity.this, LoginActivity.class));
                }
                break;
            }
            case R.id.btn_beli: {
                if (mAuth.getCurrentUser() != null) {
                    String hargaPengiriman = "12000";
                    String catatan = "";
                    String jumlah = "1";
                    int subtotal = Integer.parseInt(hargaPengiriman)+Integer.parseInt(makananModel.harga);
                    beliSekarang(makananModel.foto_penjual,makananModel.penjual,makananModel.nama_produk
                            ,makananModel.foto,jumlah,makananModel.harga,hargaPengiriman,catatan,
                            String.valueOf(subtotal),makananModel.produk_id);
                    startActivity(new Intent(DetailMakananActivity.this, CheckOutActivity.class));
                } else {
                    startActivity(new Intent(DetailMakananActivity.this, LoginActivity.class));
                }
                break;
            }
            case R.id.btn_add_to_cart: {
                if (mAuth.getCurrentUser() != null) {

                    addToKeranjang(makananModel.produk_id,makananModel.foto,makananModel.penjual,
                            makananModel.foto,makananModel.nama_produk,makananModel.harga,"0");
                    snackbar = Snackbar.make(constraintLayout, "Ditambah ke dalam keranjang", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    Log.d("add to keranjang", "onClick: berhasil add to keranjang");
                } else {
                    startActivity(new Intent(DetailMakananActivity.this, LoginActivity.class));
                }
                break;
            }
            case R.id.btn_komen: {
                intent = new Intent(this, CommentActivity.class);
                intent.putExtra("produkId", makananModel.produk_id);
                startActivity(intent);
                break;
            }
            case R.id.ll_lihat_ulasan: {
                intent = new Intent(this, PenilaianActivity.class);
                intent.putExtra("produkId", makananModel.produk_id);
                startActivity(intent);
                break;
            }
            case R.id.iv_back_detail: {
                finish();
                break;
            }
        }
    }

    private void addToKeranjang(String produkId,String fotoPenjual, String namaToko, String fotoProduk,
                                String namaProduk, String hargaProduk,
                                String jumlahPembelian) {
        keranjangBelanjaProdukModel = new KeranjangBelanjaProdukModel(produkId,fotoPenjual,
                namaToko,fotoProduk,namaProduk,hargaProduk,jumlahPembelian,false);
        mUserDatabase.child(userId).child("keranjang").child(produkId).setValue(keranjangBelanjaProdukModel);
        snackbar = Snackbar.make(constraintLayout, "Ditambah kedalam keranjang", Snackbar.LENGTH_SHORT);
        snackbar.show();

    }

    private void beliSekarang(String fotoToko, String namaToko, String namaProduk, String fotoProduk,
                              String jumlahProduk, String hargaProduk, String hargaPengiriman,
                              String catatan, String subtotal,String produkId){
        pembelianModel = new PembelianModel(fotoToko,namaToko,namaProduk,fotoProduk,jumlahProduk,
                hargaProduk,hargaPengiriman,catatan,subtotal,"Menunggu pembayaran",produkId
                ,"JNE Reguler","Dikirim dalam 1-3 hari",makananModel.produk_id);
        mUserDatabase.child(userId).child("BarangDiBeli").child(produkId).setValue(pembelianModel);
    }

    private void addToFavorite(String fotoPrduk, String namaProduk, String namaPenjual, String produkId) {
        favoriteModel = new FavoriteModel(fotoPrduk, namaProduk, namaPenjual, produkId, "true");
        Log.d("TAG", "addToFavorite: " + favoriteModel);
        mUserDatabase.child(userId).child("favorite").child(produkId).setValue(favoriteModel);
        btnFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
        isFavorite = true;
        snackbar = Snackbar.make(constraintLayout, "Ditambahkan kedalam favorit", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void removeFavorite() {
        Query removeFavoriteQuerry;
        if (produkId != null){
            removeFavoriteQuerry= mUserDatabase.child(userId).child("favorite").child(produkId).equalTo(produkId);
            removeFavoriteQuerry.getRef().removeValue();
        }else {
           removeFavoriteQuerry= mUserDatabase.child(userId).child("favorite").child(makananModel.produk_id).equalTo(makananModel.produk_id);
           removeFavoriteQuerry.getRef().removeValue();
        }
        btnFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
        isFavorite = false;
        snackbar = Snackbar.make(constraintLayout, "Dihapus dari favorit", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void checkFavorite(String idProduk) {
        mUserDatabase.child(userId).child("favorite").orderByChild("produkId").equalTo(idProduk).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        FavoriteModel favoriteModel = dataSnapshot1.getValue(FavoriteModel.class);
                        Log.d("produkId", "onDataChange: " + favoriteModel.getProdukId());
                        if (produkId != null) {
                            if (favoriteModel.getProdukId().equals(produkId)) {
                                btnFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                                isFavorite = true;
                            } else {
                                btnFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
                                isFavorite = false;
                            }
                        }else {
                            if (favoriteModel.getProdukId().equals(makananModel.produk_id)) {
                                btnFavorite.setImageResource(R.drawable.ic_favorite_orange_24dp);
                                isFavorite = true;
                            } else {
                                btnFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
                                isFavorite = false;
                            }
                        }
                    }
                } else {
                    btnFavorite.setImageResource(R.drawable.ic_favorite_border_orange_24dp);
                    isFavorite = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
