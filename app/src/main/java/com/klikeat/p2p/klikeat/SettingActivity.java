package com.klikeat.p2p.klikeat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    private ImageView btnImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar_setting);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("Setting");
        btnImgBack= findViewById(getResources().getIdentifier("action_bar_back","id", getPackageName()));
    }
}
