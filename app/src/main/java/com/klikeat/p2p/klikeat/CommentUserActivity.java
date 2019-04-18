package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import java.util.ArrayList;

public class CommentUserActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton backComment;
    RecyclerView rvKomentar;
    ArrayList<CommentModel> commentModels;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_user);
        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();

        backComment = findViewById(R.id.iv_back_comment_user);
        rvKomentar = findViewById(R.id.rv_komentar_user);
        backComment.setOnClickListener(this);
        getComment(userId);

    }

    private void getComment(String userId){
        mUserDatabase.child(userId).child("komentar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        CommentModel commentModel1 = dataSnapshot1.getValue(CommentModel.class);
                        commentModels.add(commentModel1);
                    }
                    rvKomentar.setLayoutManager(new LinearLayoutManager(CommentUserActivity.this, LinearLayoutManager.VERTICAL, false));
                    commentAdapter = new CommentAdapter(CommentUserActivity.this, commentModels);
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
            case R.id.iv_back_comment_user:{
                finish();
                break;
            }
        }
    }
}
