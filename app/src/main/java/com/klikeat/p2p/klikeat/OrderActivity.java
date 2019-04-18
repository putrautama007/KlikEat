package com.klikeat.p2p.klikeat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.adapter.HistoryAdapter;
import com.klikeat.p2p.klikeat.adapter.OrderAdapter;
import com.klikeat.p2p.klikeat.model.HistoryModel;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton btnBack;
    RecyclerView rvOrder;
    OrderAdapter orderAdapter;
    ArrayList<HistoryModel> historyModels;

    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        btnBack = findViewById(R.id.iv_back_order);
        progressBar = findViewById(R.id.order_progressbar);
        rvOrder = findViewById(R.id.rv_order);
        btnBack.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        if (mAuth.getCurrentUser() != null) {
            mUserIntansce = FirebaseDatabase.getInstance();
            mUserDatabase = mUserIntansce.getReference().child("user");
            loadOrderData(userId);
        }else {

        }
    }

    private void loadOrderData(String userId){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).child("transaksi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        HistoryModel historyModel = dataSnapshot1.getValue(HistoryModel.class);
                        historyModels.add(historyModel);
                    }
                    rvOrder.setLayoutManager(new LinearLayoutManager(OrderActivity.this, LinearLayoutManager.VERTICAL, false));
                    orderAdapter = new OrderAdapter(OrderActivity.this, historyModels);
                    rvOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_order :{
                finish();
                break;
            }


        }
    }
}
