package com.klikeat.p2p.klikeat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.klikeat.p2p.klikeat.KeranjangActivity;
import com.klikeat.p2p.klikeat.LoginActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.RegisterActivity;
import com.klikeat.p2p.klikeat.SettingActivity;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivSetting;
    Button login,register;
    Intent intent;
    TextView tvNama,tvPoin;
    ConstraintLayout clProfileBelumLogin;
    RelativeLayout rvSudahLogin;
    FirebaseAuth mAuth;
    DatabaseReference mUserDatabase;
    FirebaseDatabase mUserIntansce;
    String userId;
    ProgressBar progressBar;

    LinearLayout penilaianSaya,keranjang,komentarSaya,order;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ivSetting =  view.findViewById(R.id.iv_setting);
        login = view.findViewById(R.id.btn_login);
        register = view.findViewById(R.id.btn_register);
        clProfileBelumLogin = view.findViewById(R.id.rv_profile_belum_login);
        tvNama = view.findViewById(R.id.tv_Name);
        tvPoin = view.findViewById(R.id.tv_poin);
        penilaianSaya = view.findViewById(R.id.linear_penilaianSaya);
        keranjang = view.findViewById(R.id.linear_keranjang);
        komentarSaya = view.findViewById(R.id.linear_komentarSaya);
        order = view.findViewById(R.id.linear_order);
        progressBar = view.findViewById(R.id.profile_progressbar);

        rvSudahLogin = view.findViewById(R.id.rv_sudah_login);
        penilaianSaya.setOnClickListener(this);
        keranjang.setOnClickListener(this);
        komentarSaya.setOnClickListener(this);
        order.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        if (mAuth.getCurrentUser() != null) {
            clProfileBelumLogin.setVisibility(View.INVISIBLE);
            mUserIntansce = FirebaseDatabase.getInstance();
            mUserDatabase = mUserIntansce.getReference().child("user");
            loadUserData(userId);
        }else {
            clProfileBelumLogin.setVisibility(View.VISIBLE);
        }

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_setting: {
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login : {
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_register : {
                intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.linear_keranjang :{
                startActivity(new Intent(getActivity(), KeranjangActivity.class));
                break;
            }
            case R.id.linear_komentarSaya:{
                break;
            }
            case R.id.linear_order :{
                break;
            }
            case R.id.linear_penilaianSaya:{
                break;
            }
        }
    }

    private void loadUserData(String id){
        progressBar.setVisibility(View.VISIBLE);
        mUserDatabase.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String poin = dataSnapshot.child("poin").getValue(String.class);
                String nama = dataSnapshot.child("nama").getValue(String.class);
                tvNama.setText(nama);
                tvPoin.setText("Poin : "+poin);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
