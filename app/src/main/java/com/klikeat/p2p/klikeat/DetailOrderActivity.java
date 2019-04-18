package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.klikeat.p2p.klikeat.model.HistoryModel;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;

public class DetailOrderActivity extends AppCompatActivity implements View.OnClickListener {


    DatabaseReference mUserDatabase;
    FirebaseDatabase  mUserIntansce;
    TextView namaPenjual,namaProduk,jumlahBarang,harga,subtotal,
            jasaPengiriman,hargajasa,catatanPenjual,status;
    ImageView ivPenjual,ivProduk;
    ImageButton backOrder;
    FirebaseAuth mAuth;
    String userId,orderId,historyId;
    HistoryModel historyModel;
    Util util;
    Button btnUlasan;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        util = new Util(this);
        orderId = getIntent().getStringExtra("orderId");
        historyId = getIntent().getStringExtra("historyId");
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        initView();
        if (historyId != null) {
            loadDataHistory(historyId);
        }else{
            loadDataOrder(orderId);
        }

    }

    private void initView(){
        namaPenjual = findViewById(R.id.nama_penjual_order);
        namaProduk = findViewById(R.id.tv_nama_produk_order);
        jumlahBarang = findViewById(R.id.tv_jumlah_produk_order);
        status = findViewById(R.id.status_order);
        harga = findViewById(R.id.tv_harga_produk_order);
        subtotal = findViewById(R.id.subtotal_order);
        jasaPengiriman = findViewById(R.id.jasa_pengiriman_order);
        hargajasa = findViewById(R.id.harga_jasa_order);
        catatanPenjual = findViewById(R.id.et_catatan_order);
        ivPenjual = findViewById(R.id.iv_profile_penjual_order);
        ivProduk = findViewById(R.id.iv_produk_order);
        btnUlasan = findViewById(R.id.btn_give_feedback);
        progressBar = findViewById(R.id.progressbar_detail_order);
        backOrder = findViewById(R.id.iv_back_order);
        backOrder.setOnClickListener(this);
        btnUlasan.setOnClickListener(this);
    }

    private void loadData(final HistoryModel historyModel){
        RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(40,0);
        RoundedCornersTransformation roundedCornersTransformation1 = new RoundedCornersTransformation(100,0);
        namaPenjual.setText(historyModel.getNamaToko());
        namaProduk.setText(historyModel.getNamaProduk());
        jumlahBarang.setText(historyModel.getJumlahProduk()+ " Barang");
        status.setText(historyModel.getStatus());
        harga.setText(util.convertToIdr(Integer.parseInt(historyModel.getHargaProduk())));
        subtotal.setText(util.convertToIdr(Integer.parseInt(historyModel.getSubtotal())));
        jasaPengiriman.setText(historyModel.getJasaPengiriaman());
        hargajasa.setText(util.convertToIdr(Integer.parseInt(historyModel.getHargaPengiriman())));
        Picasso.get().load(historyModel.getFotoProduk()).fit()
                .centerCrop().transform(roundedCornersTransformation).into(ivProduk);
        Picasso.get().load(historyModel.getFotoToko()).fit()
                .centerCrop().transform(roundedCornersTransformation1).into(ivPenjual);
        if (!historyModel.getStatus().equals("Barang diterima")){
            btnUlasan.setVisibility(View.GONE);
        }
        btnUlasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailOrderActivity.this,GiveFeedBackActivity.class);
                intent.putExtra("produkId",historyModel.getProduk_id());
                startActivity(intent);
            }
        });

    }

    private void loadDataHistory(final String transaksiId){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).child("riwayat").child(transaksiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    historyModel = dataSnapshot.getValue(HistoryModel.class);
                    loadData(historyModel);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDataOrder(String transaksiId){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).child("transaksi").child(transaksiId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    historyModel = dataSnapshot.getValue(HistoryModel.class);
                    loadData(historyModel);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_order :{
                finish();
                break;
            }
            case R.id.btn_give_feedback :{
                break;
            }
        }
    }
}
