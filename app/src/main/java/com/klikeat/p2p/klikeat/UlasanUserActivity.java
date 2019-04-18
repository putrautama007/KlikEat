package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.UlasanProdukDetailAdapter;
import com.klikeat.p2p.klikeat.model.UlasanModel;

import java.util.ArrayList;

public class UlasanUserActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSemua,btnPenilaian1,btnPenilaian2,btnPenilaian3,btnPenilaian4,btnPenilaian5;
    ImageButton btnBack;
    RecyclerView rvUlasanDetail;
    private DatabaseReference mUserDatabase;
    private FirebaseDatabase mUserInstance;
    ArrayList<UlasanModel> ulasanModels;
    UlasanProdukDetailAdapter ulasanProdukAdapter;
    String userId;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ulasan_user);


        progressBar = findViewById(R.id.progressbar_penilaian_user);
        btnSemua = findViewById(R.id.btn_semua_penilaian_user);
        btnPenilaian1 = findViewById(R.id.btn_penialaian_1_user);
        btnPenilaian2 = findViewById(R.id.btn_penialaian_2_user);
        btnPenilaian3 = findViewById(R.id.btn_penialaian_3_user);
        btnPenilaian4 = findViewById(R.id.btn_penialaian_4_user);
        btnPenilaian5 = findViewById(R.id.btn_penialaian_5_user);
        btnBack = findViewById(R.id.iv_back_penilaian_user);
        rvUlasanDetail = findViewById(R.id.rv_penilaian_user);

        btnSemua.setOnClickListener(this);
        btnPenilaian1.setOnClickListener(this);
        btnPenilaian2.setOnClickListener(this);
        btnPenilaian3.setOnClickListener(this);
        btnPenilaian4.setOnClickListener(this);
        btnPenilaian5.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mUserInstance = FirebaseDatabase.getInstance();
        mUserDatabase = mUserInstance.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        loadUlasan(userId);
    }

    private void loadUlasan(String id){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(id).child("ulasan").addValueEventListener(new ValueEventListener() {
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
                    btnPenilaian2.setText("2 Bintang ("+penilaian2+")");
                    btnPenilaian3.setText("3 Bintang ("+penilaian3+")");
                    btnPenilaian4.setText("4 Bintang ("+penilaian4+")");
                    btnPenilaian5.setText("5 Bintang ("+penilaian5+")");
                    rvUlasanDetail.setLayoutManager(new LinearLayoutManager(UlasanUserActivity.this, LinearLayoutManager.VERTICAL, false));
                    ulasanProdukAdapter = new UlasanProdukDetailAdapter(UlasanUserActivity.this, ulasanModels);
                    rvUlasanDetail.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                    rvUlasanDetail.setAdapter(ulasanProdukAdapter);
                    ulasanProdukAdapter.notifyDataSetChanged();
                }else {

                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadUlasanBerdsarkanBintang(String id,String bintang){
        mUserDatabase.child(id).child("ulasan").orderByChild("ratingProduk").equalTo(bintang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulasanModels = new ArrayList<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        UlasanModel ulasanModel = tiapDataSnapshot.getValue(UlasanModel.class);
                        ulasanModels.add(ulasanModel);
                    }
                    rvUlasanDetail.setLayoutManager(new LinearLayoutManager(UlasanUserActivity.this, LinearLayoutManager.VERTICAL, false));
                    ulasanProdukAdapter = new UlasanProdukDetailAdapter(UlasanUserActivity.this, ulasanModels);
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
            case R.id.btn_semua_penilaian_user :{
                btnSemua.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasan(userId);
                break;
            }
            case R.id.btn_penialaian_1_user:{
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(userId,"1.0");
                break;
            }
            case R.id.btn_penialaian_2_user:{
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_orange_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(userId,"2.0");
                break;
            }
            case R.id.btn_penialaian_3_user:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(userId,"3.0");
                break;
            }
            case R.id.btn_penialaian_4_user:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(userId,"4.0");
                break;
            }
            case R.id.btn_penialaian_5_user:{
                btnPenilaian1.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian2.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian3.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian4.setBackgroundResource(R.drawable.rounded_grey_background);
                btnPenilaian5.setBackgroundResource(R.drawable.rounded_orange_background);
                btnSemua.setBackgroundResource(R.drawable.rounded_grey_background);
                loadUlasanBerdsarkanBintang(userId,"5.0");
                break;
            }
            case R.id.iv_back_penilaian_user:{
                finish();
                break;
            }
        }
    }
}

