package com.hunter.helloandroid.module.coordinator.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.coordinator.alipay.Alipay2Activity;
import com.hunter.helloandroid.module.coordinator.alipay.AlipayActivity;
import com.hunter.helloandroid.module.coordinator.splash.SplashActivity;
import com.hunter.helloandroid.module.coordinator.userinfo.UserInfoActivity;
import com.hunter.helloandroid.module.drawer.DrawerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2018
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/27 10:53
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class CoordinatorActivity extends AppCompatActivity {


    @BindView(R.id.main_tb_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ctl_title)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.gv_menu)
    LinearLayout mLinearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        initView();
    }

    public void initView() {
        ButterKnife.bind(this);

        try {
            setSupportActionBar(mToolbar);
            mToolbarLayout.setCollapsedTitleGravity(Gravity.START);
            mToolbarLayout.setExpandedTitleGravity(Gravity.BOTTOM);
            mToolbarLayout.setTitle(getResources().getString(R.string.app_name));
            mToolbarLayout.setExpandedTitleColor(Color.WHITE);
            mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_drawer)
    void onClickDrawer() {
        startActivity(new Intent(this, DrawerActivity.class));
    }

    @OnClick(R.id.btn_alipay_activity)
    void onClickAlipay() {
        startActivity(new Intent(this, AlipayActivity.class));
    }

    @OnClick(R.id.btn_alipay2_activity)
    void onClickAlipay2() {
        startActivity(new Intent(this, Alipay2Activity.class));
    }

    @OnClick(R.id.btn_scrolling_activity)
    void onClickScrolling() {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    @OnClick(R.id.btn_splash_activity)
    void onClickSplash() {
        startActivity(new Intent(this, SplashActivity.class));
    }

    @OnClick(R.id.btn_userinfo_activity)
    void onClickUserInfo() {
        startActivity(new Intent(this, UserInfoActivity.class));
    }

}
