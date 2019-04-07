package com.klikeat.p2p.klikeat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView lupaPassword;
    ConstraintLayout toRegister;
    Button btnLogin;
    EditText email,password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lupaPassword = findViewById(R.id.lupa_password);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        toRegister = findViewById(R.id.to_register);
        btnLogin = findViewById(R.id.btn_sign_in);
        progressBar = findViewById(R.id.progressbar_login);
        lupaPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    private void loginAccount(){
        progressBar.setVisibility(View.VISIBLE);
        String emailLogin = email.getText().toString();
        String passwordLogin = password.getText().toString();

        mAuth.signInWithEmailAndPassword(emailLogin,passwordLogin)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Berhasil masuk", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Gagal masuk", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lupa_password : {
                break;
            }
            case R.id.to_register :{
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            }
            case R.id.btn_sign_in :{
                loginAccount();
                break;
            }

        }
    }
}
