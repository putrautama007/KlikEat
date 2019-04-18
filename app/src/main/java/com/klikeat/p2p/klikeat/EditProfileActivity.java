package com.klikeat.p2p.klikeat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnImgBack, saveProfile;
    RadioButton rbPria,rbWanita;
    FirebaseAuth mAuth;
    TextView address;
    EditText name,tglLahir,email,noTlp;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    String userId,userpassword,userEmail;
    LinearLayout changeAddress;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        btnImgBack = findViewById(R.id.iv_backEditProfile);
        saveProfile = findViewById(R.id.iv_saveEditProfile);
        name = findViewById(R.id.et_Name);
        tglLahir = findViewById(R.id.et_birthday);
        email = findViewById(R.id.et_email);
        noTlp = findViewById(R.id.et_phoneNumber);
        rbPria = findViewById(R.id.rb_boy);
        rbWanita = findViewById(R.id.rb_girl);
        progressBar = findViewById(R.id.edit_profile_progressbar);
        address = findViewById(R.id.tv_address_location);
        changeAddress = findViewById(R.id.ll_change_address);

        btnImgBack.setOnClickListener(this);
        saveProfile.setOnClickListener(this);
        tglLahir.setOnClickListener(this);
        changeAddress.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        loadDataUser();

    }

    private void loadDataUser(){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("nama").getValue(String.class));
                userEmail = dataSnapshot.child("email").getValue(String.class);
                userpassword = dataSnapshot.child("password").getValue(String.class);
                email.setText(userEmail);
                noTlp.setText(dataSnapshot.child("notlp").getValue(String.class));
                if (dataSnapshot.child("jenisKelamin").getValue(String.class).equals("Pria")){
                    rbPria.setChecked(true);
                }else {
                    rbWanita.setChecked(true);
                }
                tglLahir.setText(dataSnapshot.child("tglLahir").getValue(String.class));
                address.setText(dataSnapshot.child("alamat").getValue(String.class));
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateEmail(){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(userEmail, userpassword);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email.getText().toString())
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
        updateEmail();
        mUserDatabase.child(userId).child("nama").setValue(name.getText().toString());
        mUserDatabase.child(userId).child("email").setValue(email.getText().toString());
        mUserDatabase.child(userId).child("notlp").setValue(noTlp.getText().toString());
        mUserDatabase.child(userId).child("tglLahir").setValue(tglLahir.getText().toString());
        if (rbPria.isChecked()) {
            mUserDatabase.child(userId).child("jenisKelamin").setValue(rbPria.getText().toString());
        }else {
            mUserDatabase.child(userId).child("jenisKelamin").setValue(rbWanita.getText().toString());
        }
        mUserDatabase.child(userId).child("alamat").setValue(address.getText().toString());

    }

    private void showDateDialog(){

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy");
                tglLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },1990, 1, 1);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_saveEditProfile : {
                updateData();
                finish();
                break;
            }
            case R.id.iv_backEditProfile :{
                finish();
                break;
            }
            case R.id.et_birthday :{
                showDateDialog();
                break;
            }
            case R.id.ll_change_address :{
                Intent intent = new  Intent();
                intent.setClassName("com.klikeat.p2p.klikeat",
                        "com.klikeat.p2p.klikeat.ChangeAlamatActivity");
                startActivityForResult(intent,99);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK){
            address.setText(data.getStringExtra("alamat"));
        }
    }
}
