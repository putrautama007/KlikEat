package com.klikeat.p2p.klikeat;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView btnImgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);
        TextView title=findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        title.setText("Edit Profil");
    }
}
