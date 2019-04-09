package com.klikeat.p2p.klikeat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.MakananActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.model.MakananModel;
import com.klikeat.p2p.klikeat.adapter.PopularAdapter;
import com.klikeat.p2p.klikeat.util.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<MakananModel> popularModels = new ArrayList<>();
    int [] sampleImages ={R.drawable.potato,R.drawable.ella_olsson_1184054_unsplash,R.drawable.ernest_ojeh_1348807_unsplash};
    ImageView snack, anekaLauk, riceBox, sambal, minuman, lainLain;
    private DatabaseReference mProdukPopulerDatabase;
    private FirebaseDatabase mProdukPopulerInstance;
    RecyclerView recyclerView;
    PopularAdapter popularAdapter;
    CarouselView carouselView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        snack = view.findViewById(R.id.iv_Snack);
        anekaLauk = view.findViewById(R.id.iv_Food);
        riceBox = view.findViewById(R.id.iv_frosen);
        sambal = view.findViewById(R.id.iv_chiliSauce);
        minuman = view.findViewById(R.id.iv_drinks);
        lainLain = view.findViewById(R.id.iv_etc);
        recyclerView = view.findViewById(R.id.rv_popular);
        carouselView = view.findViewById(R.id.carousel);

        snack.setOnClickListener(this);
        anekaLauk.setOnClickListener(this);
        riceBox.setOnClickListener(this);
        sambal.setOnClickListener(this);
        minuman.setOnClickListener(this);
        lainLain.setOnClickListener(this);
        mProdukPopulerInstance = FirebaseDatabase.getInstance();
        mProdukPopulerDatabase = mProdukPopulerInstance.getReference().child("populerProduk");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPopularProduk();
        loadCarousel();
    }

    private void loadCarousel(){
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                RoundedCornersTransformation roundedCornersTransformation = new RoundedCornersTransformation(45,0);
                Picasso.get().load(sampleImages[position]).transform(roundedCornersTransformation).fit().into(imageView);
            }
        });
        carouselView.setPageCount(sampleImages.length);


    }

    private void loadPopularProduk() {
        mProdukPopulerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MakananModel makananModel = dataSnapshot1.getValue(MakananModel.class);
                    popularModels.add(makananModel);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2,LinearLayoutManager.VERTICAL, false));
                popularAdapter = new PopularAdapter(getContext(), popularModels);
                recyclerView.setAdapter(popularAdapter);
                popularAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), MakananActivity.class);
        switch (v.getId()) {
            case R.id.iv_Snack: {
                intent.putExtra("toolbarName", "Snack");
                startActivity(intent);
                break;
            }
            case R.id.iv_Food: {
                intent.putExtra("toolbarName", "Aneka Lauk");
                startActivity(intent);
                break;
            }
            case R.id.iv_frosen: {
                intent.putExtra("toolbarName", "Frozen Food");
                startActivity(intent);
                break;
            }
            case R.id.iv_chiliSauce: {
                intent.putExtra("toolbarName", "Sambal");
                startActivity(intent);
                break;
            }
            case R.id.iv_drinks: {
                intent.putExtra("toolbarName", "Minuman");
                startActivity(intent);
                break;
            }
            case R.id.iv_etc: {
                intent.putExtra("toolbarName", "Lain Lain");
                startActivity(intent);
                break;
            }
        }
    }
}
