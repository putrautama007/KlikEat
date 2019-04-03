package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.UlasanProdukAdapter;
import com.klikeat.p2p.klikeat.adapter.UlasanProdukDetailAdapter;
import com.klikeat.p2p.klikeat.model.UlasanModel;

import java.util.ArrayList;

public class PenilaianActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSemua,btnPenilaian1,btnPenilaian2,btnPenilaian3,btnPenilaian4,btnPenilaian5;
    ImageView btnBack;
    RecyclerView rvUlasanDetail;
    private DatabaseReference mProdukDetailDatabase;
    private FirebaseDatabase mProdukDetailInstance;
    ArrayList<UlasanModel> ulasanModels;
    UlasanProdukDetailAdapter ulasanProdukAdapter;
    String produkId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penilaian);
        produkId = getIntent().getStringExtra("produkId");

        btnSemua = findViewById(R.id.btn_semua_penilaian);
        btnPenilaian1 = findViewById(R.id.btn_penialaian_1);
        btnPenilaian2 = findViewById(R.id.btn_penialaian_2);
        btnPenilaian3 = findViewById(R.id.btn_penialaian_3);
        btnPenilaian4 = findViewById(R.id.btn_penialaian_4);
        btnPenilaian5 = findViewById(R.id.btn_penialaian_5);
        btnBack = findViewById(R.id.iv_back_penilaian);
        rvUlasanDetail = findViewById(R.id.rv_penilaian_detail);

        btnSemua.setOnClickListener(this);
        btnPenilaian1.setOnClickListener(this);
        btnPenilaian2.setOnClickListener(this);
        btnPenilaian3.setOnClickListener(this);
        btnPenilaian4.setOnClickListener(this);
        btnPenilaian5.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mProdukDetailInstance = FirebaseDatabase.getInstance();
        mProdukDetailDatabase = mProdukDetailInstance.getReference().child("produk");

        loadUlasan(produkId);
    }

    private void loadUlasan(String id){
        mProdukDetailDatabase.child(id).child("ulasan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulasanModels = new ArrayList<>();
                int penilaian1 = 0,penilaian2 =0,penilaian3 =0,penilaian4 =0,penilaian5 =0;
                if (dataSnapshot != null) {
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        UlasanModel ulasanModel = tiapDataSnapshot.getValue(UlasanModel.class);
                        ulasanModels.add(ulasanModel);
                        if (ulasanModel.ratingProduk.equals("1.0")){
                            penilaian1++;
                        }
                        if (ulasanModel.ratingProduk.equals("2.0")){
                            penilaian2++;
                        }
                        if (ulasanModel.ratingProduk.equals("3.0")){
                            penilaian3++;
                        }
                        if (ulasanModel.ratingProduk.equals("4.0")){
                            penilaian4++;
                        }
                        if (ulasanModel.ratingProduk.equals("5.0")){
                            penilaian5++;
                        }

                    }
                    btnSemua.setText("Semua (" + ulasanModels.size() +")");
                    btnPenilaian1.setText("1 Bintang ("+penilaian1+")");
                    btnPenilaian2.setText("1 Bintang ("+penilaian2+")");
                    btnPenilaian3.setText("1 Bintang ("+penilaian3+")");
                    btnPenilaian4.setText("1 Bintang ("+penilaian4+")");
                    btnPenilaian5.setText("1 Bintang ("+penilaian5+")");
                    rvUlasanDetail.setLayoutManager(new LinearLayoutManager(PenilaianActivity.this, LinearLayoutManager.VERTICAL, false));
                    ulasanProdukAdapter = new UlasanProdukDetailAdapter(PenilaianActivity.this, ulasanModels);
                    rvUlasanDetail.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvUlasanDetail.setAdapter(ulasanProdukAdapter);
                    ulasanProdukAdapter.notifyDataSetChanged();
                }else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUlasanBerdsarkanBintang(String id,String bintang){
        mProdukDetailDatabase.child(id).child("ulasan").orderByChild("ratingProduk").equalTo(bintang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulasanModels = new ArrayList<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        UlasanModel ulasanModel = tiapDataSnapshot.getValue(UlasanModel.class);
                        ulasanModels.add(ulasanModel);
                    }
                    rvUlasanDetail.setLayoutManager(new LinearLayoutManager(PenilaianActivity.this, LinearLayoutManager.VERTICAL, false));
                    ulasanProdukAdapter = new UlasanProdukDetailAdapter(PenilaianActivity.this, ulasanModels);
                    rvUlasanDetail.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvUlasanDetail.setAdapter(ulasanProdukAdapter);
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
        switch (v.getId()){
            case R.id.btn_semua_penilaian:{
                btnSemua.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasan(produkId);
                break;
            }
            case R.id.btn_penialaian_1:{
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(produkId,"1.0");
                break;
            }
            case R.id.btn_penialaian_2:{
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(produkId,"2.0");
                break;
            }
            case R.id.btn_penialaian_3:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(produkId,"3.0");
                break;
            }
            case R.id.btn_penialaian_4:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(produkId,"4.0");
                break;
            }
            case R.id.btn_penialaian_5:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(produkId,"5.0");
                break;
            }
            case R.id.iv_back_penilaian:{
                finish();
                break;
            }
        }
    }
}
