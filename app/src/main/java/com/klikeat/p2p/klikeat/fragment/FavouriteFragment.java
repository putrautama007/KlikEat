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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.adapter.FavoriteAdapter;
import com.klikeat.p2p.klikeat.model.FavoriteModel;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {

    RecyclerView rvFavorit;
    FavoriteAdapter favoriteAdapter;
    ArrayList<FavoriteModel> favoriteModel;

    DatabaseReference mUserDatabase;
    FirebaseDatabase  mUserIntansce;
    FirebaseAuth mAuth;
    String userId;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFavorit = view.findViewById(R.id.rv_favourite);

        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        loadData(userId);
    }

    private void loadData(String userId){
        mUserDatabase.child(userId).child("favorite").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                favoriteModel = new ArrayList<>();
                if (dataSnapshot != null){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        FavoriteModel favoriteModels = dataSnapshot1.getValue(FavoriteModel.class);
                        favoriteModel.add(favoriteModels);
                    }
                    rvFavorit.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    favoriteAdapter = new FavoriteAdapter(getContext(), favoriteModel);
                    rvFavorit.setAdapter(favoriteAdapter);
                    favoriteAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(userId);
    }
}
