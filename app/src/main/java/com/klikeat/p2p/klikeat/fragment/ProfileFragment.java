package com.klikeat.p2p.klikeat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.klikeat.p2p.klikeat.LoginActivity;
import com.klikeat.p2p.klikeat.R;
import com.klikeat.p2p.klikeat.RegisterActivity;
import com.klikeat.p2p.klikeat.SettingActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivSetting;
    Button login,register;
    Intent intent;
    ConstraintLayout clProfileBelumLogin;
    RelativeLayout rvSudahLogin;
    FirebaseAuth mAuth;
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
        rvSudahLogin = view.findViewById(R.id.rv_sudah_login);

        ivSetting.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            clProfileBelumLogin.setVisibility(View.INVISIBLE);
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
        }
    }
}
