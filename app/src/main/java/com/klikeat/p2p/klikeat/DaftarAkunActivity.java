package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.klikeat.p2p.klikeat.model.User;

public class DaftarAkunActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    DatabaseReference mUserReference;
    FirebaseDatabase mUserDatabase;
    User user;
    EditText noTlp,tglLahir,provinsi,kabupaten,alamatDetail;
    RadioButton pria,wanita;
    ImageButton btnDaftar,btnBack;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_akun);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noTlp = findViewById(R.id.no_tlp);
        tglLahir = findViewById(R.id.tgl_lahir);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);
        alamatDetail = findViewById(R.id.alamat_detail);
        pria = findViewById(R.id.rb_pria);
        wanita = findViewById(R.id.rb_wanita);
        btnBack = findViewById(R.id.back_daftar_akun);
        btnDaftar = findViewById(R.id.konfirm_daftar_akun);
        progressBar = findViewById(R.id.progress_daftar_akun);
        btnDaftar.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void registerAccount(){
        progressBar.setVisibility(View.VISIBLE);
        final String name = getIntent().getStringExtra("nama");
        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        final String notlp = noTlp.getText().toString();
        final String ttl = tglLahir.getText().toString();
        final String alamat = alamatDetail.getText().toString()+","+
                kabupaten.getText().toString()+","+provinsi.getText().toString();
        final String jenisKelamin;
        if (pria.isChecked()){
            jenisKelamin = "Pria";
        }else {
            jenisKelamin = "Wanita";
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    saveToDatabase(name,email,password,notlp,ttl,alamat,jenisKelamin);
                    Intent intent = new Intent(DaftarAkunActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(DaftarAkunActivity.this, "Berhasil mendaftar, silahkan login", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DaftarAkunActivity.this, "Gagal mendaftar", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    private void saveToDatabase(String name, String email, String password,
                                String noTlp, String tglLahir,String alamat, String jenisKelamin ){
        mUserDatabase = FirebaseDatabase.getInstance();
        mUserReference = mUserDatabase.getReference("user");
        String userId = mAuth.getUid();
        user = new User(name,email,password,noTlp,tglLahir,alamat,jenisKelamin,"0","");
        mUserReference.child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.konfirm_daftar_akun : {
                registerAccount();
                break;
            }
            case R.id.back_daftar_akun : {
                finish();
                break;
            }
        }
    }
}
