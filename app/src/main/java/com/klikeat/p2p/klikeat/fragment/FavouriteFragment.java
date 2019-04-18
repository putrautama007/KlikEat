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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.adapter.FavoriteAdapter;
import com.klikeat.p2p.klikeat.model.FavoriteModel;
import com.klikeat.p2p.klikeat.model.MakananModel;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {

    RecyclerView rvFavorit;
    FavoriteAdapter favoriteAdapter;
    ArrayList<FavoriteModel> favoriteModel;
    ProgressBar progressBar;
    EditText etSearchFavorite;
    ImageButton btnSearchFAvorite;

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
        progressBar = view.findViewById(R.id.favorite_progressbar);
        etSearchFavorite = view.findViewById(R.id.search_editText_favorite);
        btnSearchFAvorite = view.findViewById(R.id.btnSearch_favorite);
        btnSearchFAvorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namaMakanan = etSearchFavorite.getText().toString();
                loadDataMakananBySearch(namaMakanan);
            }
        });


        mUserIntansce = FirebaseDatabase.getInstance();
        mUserDatabase = mUserIntansce.getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        if (mAuth.getCurrentUser() != null) {
            loadData(userId);
        }else {

        }
    }

    private void loadData(String userId){
        progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadDataMakananBySearch(String namaMakanan){
        progressBar.setVisibility(View.VISIBLE);
        Query searchQuerry = mUserDatabase.child(userId).child("favorite").orderByChild("namaProduk").equalTo(namaMakanan);
        searchQuerry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    favoriteModel.clear();
                    for (DataSnapshot tiapDataSnapshot : dataSnapshot.getChildren()) {
                        FavoriteModel makananModel = tiapDataSnapshot.getValue(FavoriteModel.class);
                        favoriteModel.add(makananModel);
                    }
                    favoriteAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
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
        if (mAuth.getCurrentUser() != null) {
            loadData(userId);
        }else {

        }
    }
}
