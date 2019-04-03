package com.klikeat.p2p.klikeat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.MakananAdapter;
import com.klikeat.p2p.klikeat.model.MakananModel;

import java.util.ArrayList;
import java.util.List;

public class MakananActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnImgBack, btnCart;
    TextView toolbarName;
    List<MakananModel> makananModels;
    MakananAdapter makananAdapter;
    RecyclerView rvMakanan;


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
        btnImgBack.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        makananModels = new ArrayList<>();
        mProdukInstance = FirebaseDatabase.getInstance();
        mProdukDatabase = mProdukInstance.getReference().child("produk");
        String getToolbarName = getIntent().getStringExtra("toolbarName");
        toolbarName.setText(getToolbarName);
        loadDataMakanan(getToolbarName);


    }

    private void loadDataMakanan(String kategori){
        mProdukDatabase.orderByChild("kategori").equalTo(kategori).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
