package com.hunter.helloandroid.module.coordinator.splash;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hunter.helloandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/26 17:19
 * <p>
 * 描    述：程序入口
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tl_title)
    Toolbar mToolbar;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.gv_menu)
    LinearLayout mLinearLayout;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }


    public void initView() {
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbarLayout.setCollapsedTitleGravity(Gravity.START);
        mToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);
        mToolbarLayout.setTitle(getResources().getString(R.string.app_name));
        mToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        for (int i = 0; i < 20; i++) {
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            button.setText(String.valueOf(i + 1));
            button.setOnClickListener(this);
            mLinearLayout.addView(button);
        }

    }


    @Override
    public void onClick(View v) {
    }

}
