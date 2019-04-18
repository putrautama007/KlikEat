package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnBack,btnSavePassword;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    String userId,userpassword,userEmail;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText etOldPassword,etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btnBack = findViewById(R.id.iv_back_change_password);
        btnSavePassword = findViewById(R.id.iv_save_change_password);
        progressBar = findViewById(R.id.edit_password_progressbar);
        etNewPassword = findViewById(R.id.et_new_password);
        etOldPassword = findViewById(R.id.et_old_password);

        btnSavePassword.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        loadDataUser();

    }
    private void loadDataUser(){
        mUserDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userEmail = dataSnapshot.child("email").getValue(String.class);
                userpassword = dataSnapshot.child("password").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updatePassword(){
        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(etOldPassword.getText())) {
            Toast.makeText(getApplicationContext(),"Mohon masukan kata sandi lama anda", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (TextUtils.isEmpty(etNewPassword.getText())) {
            Toast.makeText(getApplicationContext(),"Mohon masukan kata sandi baru anda", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if (etNewPassword.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), "Kata sandi baru  minimal terdiri dari 6 karakter", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(userEmail, userpassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updatePassword(etNewPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });
                    }
                });
    }

    private void updateData(){
        updatePassword();
        mUserDatabase.child(userId).child("password").setValue(etNewPassword.getText().toString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_change_password :{
                finish();
                break;
            }
            case R.id.iv_save_change_password:{
                updateData();
                finish();
                break;
            }
        }
    }
}
