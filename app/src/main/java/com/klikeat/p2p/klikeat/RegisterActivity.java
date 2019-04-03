package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRegister;
    EditText nama,email,konfirmasiPassword,password;
    Intent intent;
    ConstraintLayout textToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btn_daftar);
        nama = findViewById(R.id.nama_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        konfirmasiPassword = findViewById(R.id.password_confirm_register);
        textToLogin = findViewById(R.id.to_login);
        btnRegister.setOnClickListener(this);
        textToLogin.setOnClickListener(this);
    }

    private void registerAkun(){
        String name = nama.getText().toString();
        String emailAkun = email.getText().toString();
        String passwordAkun = password.getText().toString();
        String konfirmasiPassworf = konfirmasiPassword.getText().toString();

        if (TextUtils.isEmpty(emailAkun)) {
            Toast.makeText(getApplicationContext(), "Mohon masukan email", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAkun).matches()) {
            Toast.makeText(getApplicationContext(), "Email sudah digunakan silahkan gunakan email lain", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordAkun)) {
            Toast.makeText(getApplicationContext(),"Mohon masukan kata sandi", Toast.LENGTH_LONG).show();
            return;
        }
        if (passwordAkun.length() < 6) {
            Toast.makeText(getApplicationContext(), "Kata sandi minimal terdiri dari 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }
        if (konfirmasiPassworf.length() < 6) {
            Toast.makeText(getApplicationContext(), "Kata sandi minimal terdiri dari 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Mohon masukan nama", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(konfirmasiPassworf)) {
            Toast.makeText(getApplicationContext(), "Mohon masukan konfirmasi kata sandi", Toast.LENGTH_LONG).show();
            return;
        }
        if (!konfirmasiPassworf.equals(passwordAkun)){
            Toast.makeText(getApplicationContext(), "Kata sandi konfirmasi harus sama dengan kata sandi", Toast.LENGTH_LONG).show();
        }

        intent = new Intent(this, DaftarAkunActivity.class);
        intent.putExtra("nama",name);
        intent.putExtra("email",emailAkun);
        intent.putExtra("password",passwordAkun);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_daftar : {
                registerAkun();
                break;
            }
            case R.id.to_login: {
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            }
        }
    }
}
