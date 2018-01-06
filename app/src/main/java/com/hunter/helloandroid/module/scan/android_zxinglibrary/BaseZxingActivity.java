package com.hunter.helloandroid.module.scan.android_zxinglibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hunter.helloandroid.R;

public class BaseZxingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_zxing);
    }

}
