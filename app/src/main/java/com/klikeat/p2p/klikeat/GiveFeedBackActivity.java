package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.model.UlasanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GiveFeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton backFeedBack;
    DatabaseReference mUlasanDatabase,mUserDatabase,mProdukDetailDatabase;
    FirebaseDatabase mUlasanInstance,mUserIntansce,mProdukDetailInstance;
    FirebaseAuth mAuth;
    String userId,produkId,saveCurrentDate, saveCurrentTime,tglTransaksi
            ,idTransaksi,userName;
    RatingBar ratingBar;
    EditText etUlasan;
    Button btnSendUlasan;
    UlasanModel ulasanModel;
    ArrayList<UlasanModel> ulasanModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feed_back);

        mUlasanInstance = FirebaseDatabase.getInstance();
        mUlasanDatabase = mUlasanInstance.getReference("ulasan");
        mProdukDetailInstance = FirebaseDatabase.getInstance();
        mProdukDetailDatabase = mProdukDetailInstance.getReference().child("produk");
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        produkId = getIntent().getStringExtra("produkId");
        Log.d("TAG", "onCreate: "+produkId);
        backFeedBack = findViewById(R.id.iv_back_give_feed_back);
        ratingBar = findViewById(R.id.rating);
        etUlasan = findViewById(R.id.ed_komentar);
        btnSendUlasan = findViewById(R.id.btn_send_ulasan);
        btnSendUlasan.setOnClickListener(this);
        backFeedBack.setOnClickListener(this);
        loadUserData(userId);
    }

    private void senUlasan(){
        String ulasan = etUlasan.getText().toString();
        double rating = (double) ratingBar.getRating();
        getDate();
        getIdTransaksi();
        ulasanModel = new UlasanModel(userName,ulasan,String.valueOf(rating)
                ,idTransaksi,tglTransaksi,produkId,userId);
        mUserDatabase.child(userId).child("ulasan").child(idTransaksi).setValue(ulasanModel);
        mUlasanDatabase.child(idTransaksi).setValue(ulasanModel);
        loadDataUlasan(produkId);
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
        idTransaksi = saveCurrentDate+saveCurrentTime;
    }

    private void loadUserData(String id){
        mUserDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("nama").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDataUlasan(final String produkId){
        mUlasanDatabase.orderByChild("produk_id").equalTo(produkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ulasanModels = new ArrayList<>();
                double jumlahUlasan = 0.0;
                double totalUlasn;
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        UlasanModel ulasanModel = dataSnapshot1.getValue(UlasanModel.class);
                        ulasanModels.add(ulasanModel);
                        jumlahUlasan = jumlahUlasan + Double.parseDouble(dataSnapshot1.child("ratingProduk").getValue(String.class));
                    }
                    totalUlasn = jumlahUlasan/ulasanModels.size();
                    mProdukDetailDatabase.child(produkId).child("rating").setValue(String.valueOf(totalUlasn));
                    mProdukDetailDatabase.child(produkId).child("jumlahUlasan").setValue(String.valueOf(ulasanModels.size()));
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
            case R.id.iv_back_give_feed_back :{
                finish();
                break;
            }
            case R.id.btn_send_ulasan :{
                senUlasan();
                finish();
                break;
            }
        }
    }
}
