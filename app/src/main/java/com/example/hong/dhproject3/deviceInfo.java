package com.example.hong.dhproject3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class deviceInfo extends AppCompatActivity {
        Switch sw1,sw2,sw3,sw4;
        TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
    }
}
