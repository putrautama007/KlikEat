package com.klikeat.p2p.klikeat.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.adapter.FavoriteAdapter;
import com.klikeat.p2p.klikeat.adapter.HistoryAdapter;
import com.klikeat.p2p.klikeat.model.FavoriteModel;
import com.klikeat.p2p.klikeat.model.HistoryModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    RecyclerView rvHistory;
    HistoryAdapter historyAdapter;
    ArrayList<HistoryModel> historyModels;

    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    FirebaseAuth mAuth;
    String userId;
    ProgressBar progressBar;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHistory = view.findViewById(R.id.rv_history);
        progressBar = view.findViewById(R.id.history_progressbar);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        if (mAuth.getCurrentUser() != null) {
            mUserIntansce = FirebaseDatabase.getInstance();
            mUserDatabase = mUserIntansce.getReference().child("user");
            loadData(userId);
        }else {

        }
    }

    private void loadData(String userId){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(userId).child("riwayat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                historyModels = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        HistoryModel historyModel = dataSnapshot1.getValue(HistoryModel.class);
                        historyModels.add(historyModel);
                    }
                    rvHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    historyAdapter = new HistoryAdapter(getContext(), historyModels);
                    rvHistory.setAdapter(historyAdapter);
                    historyAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() != null) {
            loadData(userId);
        }else {

        }
    }
}
