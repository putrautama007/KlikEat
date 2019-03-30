package com.klikeat.p2p.klikeat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView ivSetting;
    Button login,register;
    Intent intent;
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

        ivSetting.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
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
