package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.klikeat.p2p.klikeat.model.MetodePembayaranModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvTransferNominal,namaBank,noRek,waktuTransfer,poinText;
    ImageView logoBank;
    Button btnHalamanAwal;
    Util util;
    Gson gson;
    Date dt;
    String saveCurrentDate;
    MetodePembayaranModel metodePembayaranModel;
    DatabaseReference mUserDatabase;
    FirebaseDatabase  mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        util = new Util(this);
        gson = new Gson();
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();

        initView();
        loadData();
    }

    private void initView(){
        progressBar = findViewById(R.id.progressbar_transfer);
        tvTransferNominal = findViewById(R.id.tv_transferNominal);
        namaBank = findViewById(R.id.nama_rek);
        noRek = findViewById(R.id.tv_norek_transfer);
        waktuTransfer = findViewById(R.id.waktu_transfer);
        poinText = findViewById(R.id.tv_transferPoin);
        logoBank = findViewById(R.id.iv_bank_logo_transfer);
        btnHalamanAwal = findViewById(R.id.btn_backFirstPage_transfer);
        btnHalamanAwal.setOnClickListener(this);
    }

    private void loadData(){
        progressBar.setVisibility(View.VISIBLE);
        getNextDay();
        metodePembayaranModel = gson.fromJson(util.preferences.getString("metode",""),MetodePembayaranModel.class);
        String newTransferNominal = util.preferences.getString("pembelian","").replaceAll("[^\\d.]+", "");
        String newTransferNominal2 = util.preferences.getString("pembelian","").replaceAll("[^\\d,]+", "");
        Random random = new Random();
        int totalTransfer = Integer.parseInt(newTransferNominal2)+random.nextInt(999);
        tvTransferNominal.setText(util.convertToIdr(totalTransfer));
        double setPoin =Double.parseDouble(newTransferNominal);
        int finalSetPoin = (int) setPoin;
        noRek.setText(metodePembayaranModel.getNomorRekeningBank());
        namaBank.setText(metodePembayaranModel.getNamaBank());
        Glide.with(this).load(metodePembayaranModel.getFotoBank()).into(logoBank);
        poinText.setText("Anda akan mendapatkan "+finalSetPoin+" poin");
        waktuTransfer.setText("Pastikan anda transfer sebelum hari,"+saveCurrentDate+" atau pembayaran Anda otomatis dibatalkan oleh sistem");
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void getNextDay(){
        dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMMM yyyy");
        saveCurrentDate = currentDate.format(dt);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_backFirstPage_transfer :{
                util.clearPembelian();
                setPoin();
                removeData(userId);
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    private void removeData(String id){
        Query removeBeliSekrangQuerry,removeKeranjangBelanja;
        removeBeliSekrangQuerry= mUserDatabase.child(id).child("pembelian");
        removeBeliSekrangQuerry.getRef().removeValue();

        removeKeranjangBelanja = mUserDatabase.child(id).child("keranjang");
        removeKeranjangBelanja.getRef().removeValue();


    }

    private void setPoin(){
        mUserDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            String poin;
            int totalPoin;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                poin = dataSnapshot.child("poin").getValue(String.class);
                String newTransferNominal = tvTransferNominal.getText().toString().replaceAll("[^\\d.]+", "");
                double setPoin =Double.parseDouble(newTransferNominal);
                int finalSetPoin = (int) setPoin;
                totalPoin = Integer.parseInt(poin)
                        + finalSetPoin;
                mUserDatabase.child(userId).child("poin").setValue(String.valueOf(totalPoin));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        util.clearPembelian();
        removeData(userId);
        setPoin();
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
