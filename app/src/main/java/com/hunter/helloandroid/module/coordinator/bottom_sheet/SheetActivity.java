package com.hunter.helloandroid.module.coordinator.bottom_sheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hunter.helloandroid.R;

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
 * 创建日期： 2018/8/2 10:11
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class SheetActivity extends AppCompatActivity {

    @BindView(R.id.btn_operate)
    Button mButton;
    @BindView(R.id.bottomSheet)
    NestedScrollView bottomSheet;

    private BottomSheetBehavior behavior;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        //通过在xml中配置的string/bottom_sheet_behavior，拿到BottomSheetBehavior 实例
        behavior = BottomSheetBehavior.from(bottomSheet);
        //默认设置为隐藏
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        //是否跳过STATE_COLLAPSED状态
        behavior.setSkipCollapsed(true);
        //是否开启STATE_HIDDEN状态
        behavior.setHideable(false);
        //设置STATE_COLLAPSED状态的高度
        behavior.setPeekHeight(200);
        //设置状态改变时的回调
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                setState(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }


    @OnClick(R.id.btn_operate)
    void onClickBn() {
        showBottomSheet(behavior);
    }

    private void showBottomSheet(BottomSheetBehavior behavior) {
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        setState(behavior.getState());
    }

    private void setState(int newState) {
        switch (newState) {
            case BottomSheetBehavior.STATE_EXPANDED:
                mButton.setText("关闭");
                break;
            case BottomSheetBehavior.STATE_COLLAPSED:
                mButton.setText("打开");
                break;
        }
    }
}
