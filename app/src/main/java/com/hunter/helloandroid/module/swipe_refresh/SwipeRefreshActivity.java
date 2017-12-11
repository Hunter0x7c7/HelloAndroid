package com.hunter.helloandroid.module.swipe_refresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.widget.ZHSwipeRefreshLayout;

import org.xutils.common.util.DensityUtil;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/11 10:35
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SwipeRefreshActivity extends AppCompatActivity
        implements ZHSwipeRefreshLayout.OnRefreshListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        initZHSwipeRefreshLayout();
    }

    private ZHSwipeRefreshLayout srlRefreshLayout;

    private void initZHSwipeRefreshLayout() {
        srlRefreshLayout = (ZHSwipeRefreshLayout) findViewById(R.id.srl_refresh_layout);
        srlRefreshLayout.setOnRefreshListener(this);
        //设置卷内的颜色
        srlRefreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        srlRefreshLayout.setDefaultCircleTarget(DensityUtil.dip2px(60));
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                srlRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }

}
