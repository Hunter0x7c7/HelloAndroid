package com.hunter.helloandroid.module.coordinator.alipay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/7/23 15:45
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class Alipay2Activity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    //大布局背景，遮罩层
    @BindView(R.id.bg_content)
    View bgContent;
    //展开状态下toolbar显示的内容
    @BindView(R.id.include_toolbar_open)
    View toolbarOpen;
    //展开状态下toolbar的遮罩层
    @BindView(R.id.bg_toolbar_open)
    View bgToolbarOpen;
    //收缩状态下toolbar显示的内容
    @BindView(R.id.include_toolbar_close)
    View toolbarClose;
    //收缩状态下toolbar的遮罩层
    @BindView(R.id.bg_toolbar_close)
    View bgToolbarClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_home);

        ButterKnife.bind(this);
        appBar.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        offsetChanged(appBarLayout, verticalOffset);
    }

    private void offsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int maxOffset = appBarLayout.getTotalScrollRange();
        int topOffset = maxOffset / 5;
        if (offset <= topOffset) {//当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarOpen.setVisibility(View.VISIBLE);
            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / topOffset;
            bgToolbarOpen.setAlpha(1 - scale2);
        } else {//当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            toolbarClose.setVisibility(View.VISIBLE);
            toolbarOpen.setVisibility(View.GONE);
            float scale3 = (float) (maxOffset - offset) / (maxOffset - topOffset);
            bgToolbarClose.setAlpha(1 - scale3);
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / maxOffset;
        bgContent.setAlpha(1 - scale);
    }

    @OnClick({R.id.ll_scan, R.id.iv_scan})
    void onClickScan() {
        ToastUtil.showPrompt("........scan........");
    }

    @OnClick({R.id.ll_pay, R.id.iv_pay})
    void onClickPay() {
        ToastUtil.showPrompt("........pay........");
    }

    @OnClick({R.id.ll_charge, R.id.iv_charge})
    void onClickCharge() {
        ToastUtil.showPrompt("........charge........");
    }

    @OnClick({R.id.ll_search, R.id.iv_search})
    void onClickSearch() {
        ToastUtil.showPrompt("........search........");
    }

    @OnClick(R.id.ll_card)
    void onClickCard() {
        ToastUtil.showPrompt("........card........");
    }

    @OnClick({R.id.iv_plus, R.id.iv_close_plus})
    void onClickPlus() {
        ToastUtil.showPrompt("........plus........");
    }


}