package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.PembelianAdapter;
import com.klikeat.p2p.klikeat.model.HistoryModel;
import com.klikeat.p2p.klikeat.model.PembelianModel;
import com.klikeat.p2p.klikeat.model.PengirimanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvCheckOut;
    ImageButton backCheckout;
    TextView totalPembelian,alamatPengiriman;
    Button buatPesanan;
    PembelianAdapter pembelianAdapter;
    ArrayList<PembelianModel> pembelianModels = new ArrayList<>();
    ArrayList<PengirimanModel> pengirimanModels = new ArrayList<>();
    ArrayList<PembelianModel> pembelianModelArrayList = new ArrayList<>();
    DatabaseReference  mUserDatabase,mPengirimanDatabse;
    FirebaseDatabase  mUserIntansce, mPengirimanIntansce;
    FirebaseAuth mAuth;
    String userId,saveCurrentDate, saveCurrentTime,tglTransaksi
            ,idTransaksi;

    Util util;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        rvCheckOut = findViewById(R.id.rv_checkout);
        alamatPengiriman = findViewById(R.id.tv_address_location_checkout);
        totalPembelian = findViewById(R.id.tv_total_harga);
        buatPesanan = findViewById(R.id.btn_buat_pesanan);
        backCheckout = findViewById(R.id.iv_back_check_out);
        progressBar = findViewById(R.id.progressbar_check_out);
        buatPesanan.setOnClickListener(this);
        backCheckout.setOnClickListener(this);

        util = new Util(getApplicationContext());
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mPengirimanIntansce = FirebaseDatabase.getInstance();
        mPengirimanDatabse = mPengirimanIntansce.getReference().child("pengiriman");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        util = new Util(getApplicationContext());
        loadData(userId);
        loadAddress(userId);
        loadDataTotalPembelian(userId);

    }

    private void loadData(String id){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(id).child("BarangDiBeli").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        PembelianModel pembelianModel = dataSnapshot1.getValue(PembelianModel.class);
                        pembelianModels.add(pembelianModel);
                    }
                    mPengirimanDatabse.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                PengirimanModel pengirimanModel = dataSnapshot1.getValue(PengirimanModel.class);
                                pengirimanModels.add(pengirimanModel);
                            }
                            rvCheckOut.setLayoutManager(new LinearLayoutManager(CheckOutActivity.this, LinearLayoutManager.VERTICAL, false));
                            pembelianAdapter = new PembelianAdapter(CheckOutActivity.this, pembelianModels,pengirimanModels);
                            rvCheckOut.setAdapter(pembelianAdapter);
                            pembelianAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDataTotalPembelian(String id){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(id).child("BarangDiBeli").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int totalHarga = 0;
                if (dataSnapshot != null){
                    pembelianModelArrayList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        PembelianModel pembelianModel = dataSnapshot1.getValue(PembelianModel.class);
                        pembelianModelArrayList.add(pembelianModel);
                    }
                    for (int i =0; i<pembelianModelArrayList.size();i++){
                        totalHarga = totalHarga + (Integer.parseInt(pembelianModelArrayList.get(i).getSubtotal()));
                    }
                    totalPembelian.setText(util.convertToIdr(totalHarga));
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
        removeBeliSekrangQuerry= mUserDatabase.child(id).child("BarangDiBeli");
        removeBeliSekrangQuerry.getRef().removeValue();
    }


    private void addToHistory(){
        for (int i =0; i<pembelianModels.size();i++){
            getIdTransaksi();
            getDate();
            HistoryModel historyModel = new HistoryModel(tglTransaksi,idTransaksi,
                    totalPembelian.getText().toString()
                    ,pembelianModels.get(i).getFotoToko()
                    ,pembelianModels.get(i).getNamaToko()
                    ,pembelianModels.get(i).getNamaProduk(),
                    pembelianModels.get(i).getFotoProduk(),
                    pembelianModels.get(i).getJumlahPembelian()
                    ,pembelianModels.get(i).getHargaProduk()
                    ,pembelianModels.get(i).getHargaPengiriman(),
                    pembelianModels.get(i).getCatatan(),
                    pembelianModels.get(i).getSubtotal(),
                    "Menunggu konfirmasi pembayaran",
                    pembelianModels.get(i).getJasaPengiriaman(),
                    pembelianModels.get(i).getLamaPengiriman(),
                    pembelianModels.get(i).getProduk_id());
            mUserDatabase.child(userId).child("transaksi").child(idTransaksi).setValue(historyModel);
        }
        util.savePembelian(totalPembelian.getText().toString());
    }

    private void getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        tglTransaksi = saveCurrentDate;
    }

    private void getIdTransaksi(){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("ddmmmyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        idTransaksi = saveCurrentDate+System.currentTimeMillis();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_buat_pesanan : {
                Intent intent = new Intent(CheckOutActivity.this,PembayaranActivity.class);
                addToHistory();
                removeBeliSekarang(userId);
                intent.putExtra("transaksiId",idTransaksi);
                startActivity(intent);
                break;
            }
            case R.id.iv_back_check_out:{
                removeBeliSekarang(userId);
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        removeBeliSekarang(userId);
        finish();
    }
}
