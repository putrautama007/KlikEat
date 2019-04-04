package com.klikeat.p2p.klikeat;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.PembelianAdapter;
import com.klikeat.p2p.klikeat.model.PembelianModel;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvCheckOut;
    ImageView backCheckout;
    TextView totalPembelian,alamatPengiriman;
    Button buatPesanan;
    PembelianAdapter pembelianAdapter;
    ArrayList<PembelianModel> pembelianModels;
    DatabaseReference  mUserDatabase;
    FirebaseDatabase  mUserIntansce;
    FirebaseAuth mAuth;
    String userId,produkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        rvCheckOut = findViewById(R.id.rv_checkout);
        alamatPengiriman = findViewById(R.id.tv_address_location_checkout);
        totalPembelian = findViewById(R.id.tv_total_harga);
        buatPesanan = findViewById(R.id.btn_buat_pesanan);
        backCheckout = findViewById(R.id.iv_back_check_out);
        buatPesanan.setOnClickListener(this);
        backCheckout.setOnClickListener(this);

        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        loadData(userId);
        loadAddress(userId);

    }

    private void loadData(String id){
        mUserDatabase.child(id).child("pembelian").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pembelianModels = new ArrayList<>();
                int totalHarga = 0;
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        PembelianModel pembelianModel = dataSnapshot1.getValue(PembelianModel.class);
                        pembelianModels.add(pembelianModel);
                    }
                    for (int i =0; i<pembelianModels.size();i++){
                        totalHarga = totalHarga + Integer.parseInt(pembelianModels.get(i).getSubtotal());
                    }
                    Log.d("totalPembelian", "onDataChange: "+totalHarga);
                    totalPembelian.setText(""+totalHarga);
                    rvCheckOut.setLayoutManager(new LinearLayoutManager(CheckOutActivity.this, LinearLayoutManager.VERTICAL, false));
                    pembelianAdapter = new PembelianAdapter(CheckOutActivity.this, pembelianModels);
                    rvCheckOut.setAdapter(pembelianAdapter);
                    pembelianAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void loadAddress(String id){
        mUserDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alamatPengiriman.setText(dataSnapshot.child("alamat").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeBeliSekarang(String id){
        Query removeBeliSekrangQuerry;
        removeBeliSekrangQuerry= mUserDatabase.child(id).child("pembelian");
        removeBeliSekrangQuerry.getRef().removeValue();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buat_pesanan : {
                break;
            }
            case R.id.iv_back_check_out:{
                removeBeliSekarang(userId);
                finish();
                break;
            }
        }
    }
}
