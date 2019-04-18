package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.CommentAdapter;
import com.klikeat.p2p.klikeat.model.CommentModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvKomentar;
    ImageButton ivBack;
    EditText etKomentar;
    ImageButton btnComment;
    CommentAdapter commentAdapter;
    ArrayList<CommentModel> commentModels;
    CommentModel commentModel;
    DatabaseReference mCommentDatabase,mUserDatabase;
    FirebaseDatabase mCommentInstance,mUserIntansce;
    FirebaseAuth mAuth;
    String userId,produkId,saveCurrentDate, saveCurrentTime,tglTransaksi
            ,idTransaksi,userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mCommentInstance = FirebaseDatabase.getInstance();
        mCommentDatabase = mCommentInstance.getReference().child("komentar");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        produkId = getIntent().getStringExtra("produkId");


        rvKomentar = findViewById(R.id.rv_komentar);
        etKomentar = findViewById(R.id.et_komentar);
        ivBack = findViewById(R.id.iv_back_comment);
        btnComment = findViewById(R.id.btn_comment);
        ivBack.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        loadUserData(userId);
        getComment(produkId);
    }

    private void sendComment(){
        String comment = etKomentar.getText().toString();
        getDate();
        getIdTransaksi();
        commentModel = new CommentModel(userName,userId,comment,tglTransaksi,produkId,idTransaksi);
        mUserDatabase.child(userId).child("komentar").child(idTransaksi).setValue(commentModel);
        mCommentDatabase.child(idTransaksi).setValue(commentModel);
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

    private void getComment(String produkId){
        mCommentDatabase.orderByChild("produkId").equalTo(produkId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        CommentModel commentModel1 = dataSnapshot1.getValue(CommentModel.class);
                        commentModels.add(commentModel1);
                    }
                    rvKomentar.setLayoutManager(new LinearLayoutManager(CommentActivity.this, LinearLayoutManager.VERTICAL, false));
                    commentAdapter = new CommentAdapter(CommentActivity.this, commentModels);
                    rvKomentar.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration
                            .VERTICAL));
                    rvKomentar.setAdapter(commentAdapter);
                    commentAdapter.notifyDataSetChanged();
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
            case R.id.iv_back_comment :{
                finish();
                break;
            }
            case R.id.btn_comment :{
                sendComment();
                break;
            }
        }
    }
}
