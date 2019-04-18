package com.klikeat.p2p.klikeat;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangeAlamatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnImgBack, saveAlamat;
    EditText etProvinsi,etKabupaten,etAlamatDetail;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_alamat);
        btnImgBack = findViewById(R.id.iv_backEditAlamat);
        saveAlamat = findViewById(R.id.iv_saveEditAlamat);
        etProvinsi = findViewById(R.id.et_provinsi);
        etKabupaten = findViewById(R.id.et_kabupaten);
        etAlamatDetail = findViewById(R.id.et_alamat_detail);
        progressBar = findViewById(R.id.edit_alamat_progressbar);

        btnImgBack.setOnClickListener(this);
        saveAlamat.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        loadDataUser();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_backEditAlamat :{
                finish();
                break;
            }
            case R.id.iv_saveEditAlamat :{
                Intent intent = new Intent();
                intent.putExtra("alamat",etAlamatDetail.getText().toString()+","
                        +etKabupaten.getText().toString()+","+etProvinsi.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
            }
        }
    }

    private void loadDataUser(){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String alamatLengkap = dataSnapshot.child("alamat").getValue(String.class);
                String[] alamatDibagi = alamatLengkap.split(",");
                etProvinsi.setText(alamatDibagi[2]);
                etKabupaten.setText(alamatDibagi[1]);
                etAlamatDetail.setText(alamatDibagi[0]);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
