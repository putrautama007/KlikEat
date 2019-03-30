package com.klikeat.p2p.klikeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
