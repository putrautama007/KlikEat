package com.klikeat.p2p.klikeat;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class SnackActivity extends AppCompatActivity {
    private ImageView btnImgBack, btnImgLove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("Snack");
        btnImgBack= findViewById(getResources().getIdentifier("action_bar_back","id", getPackageName()));
        btnImgLove= findViewById(getResources().getIdentifier("action_bar_love","id", getPackageName()));
    }

}
