package com.klikeat.p2p.klikeat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.MakananAdapter;
import com.klikeat.p2p.klikeat.model.MakananModel;

import java.util.ArrayList;
import java.util.List;

public class MakananActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnImgBack, btnCart;
    TextView toolbarName;
    EditText searchMakana;
    ImageButton btnSearch;
    ArrayList<MakananModel> makananModels;
    MakananAdapter makananAdapter;
    RecyclerView rvMakanan;
    ProgressBar progressBar;


    private DatabaseReference mProdukDatabase;
    private FirebaseDatabase mProdukInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan);
        toolbarName= findViewById(R.id.tv_listFood);
        btnImgBack = findViewById(R.id.iv_backMakanan);
        btnCart = findViewById(R.id.iv_cart);
        rvMakanan = findViewById(R.id.rv_listFood);
        searchMakana = findViewById(R.id.search_editText);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressbar_produk);
        btnSearch.setOnClickListener(this);
        btnImgBack.setOnClickListener(this);
        btnCart.setOnClickListener(this);


        mProdukInstance = FirebaseDatabase.getInstance();
        mProdukDatabase = mProdukInstance.getReference().child("produk");
        String getToolbarName = getIntent().getStringExtra("toolbarName");
        toolbarName.setText(getToolbarName);
        loadDataMakanan(getToolbarName);


    }

    private void loadDataMakanan(String kategori){
        progressBar.setVisibility(View.VISIBLE);
        mProdukDatabase.orderByChild("kategori").equalTo(kategori).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananModels = new ArrayList<>();
                if (dataSnapshot != null) {
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        MakananModel makananModel = tiapDataSnapshot.getValue(MakananModel.class);
                        makananModels.add(makananModel);
                    }
                    rvMakanan.setLayoutManager(new LinearLayoutManager(MakananActivity.this, LinearLayoutManager.VERTICAL, false));
                    makananAdapter = new MakananAdapter(MakananActivity.this, makananModels);
                    rvMakanan.setAdapter(makananAdapter);
                    makananAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDataMakananBySearch(String namaMakanan){
        progressBar.setVisibility(View.VISIBLE);
        Query searchQuerry = mProdukDatabase.orderByChild("nama_produk").equalTo(namaMakanan);
        searchQuerry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananModels = new ArrayList<>();
                if (dataSnapshot != null) {
                    makananModels.clear();
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        MakananModel makananModel = tiapDataSnapshot.getValue(MakananModel.class);
                        makananModels.add(makananModel);
                    }
                    makananAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
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
            case R.id.iv_backMakanan :{
                finish();
                break;
            }
            case R.id.iv_cart :{
                startActivity(new Intent(this,KeranjangActivity.class));
                break;
            }
            case R.id.btnSearch:{
                loadDataMakananBySearch(searchMakana.getText().toString());
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
