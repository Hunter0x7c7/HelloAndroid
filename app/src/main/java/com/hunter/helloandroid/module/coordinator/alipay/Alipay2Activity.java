package com.hunter.helloandroid.module.coordinator.alipay;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.acp.Acp;
import com.hunter.helloandroid.R;
import com.hunter.helloandroid.module.update.interfaces.OnPermissionListener;
import com.hunter.helloandroid.util.PermissionUtil;
import com.hunter.helloandroid.util.ToastUtil;
import com.hunter.text.UnilineInput;

import java.util.List;

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
    //    //展开状态下toolbar显示的内容
//    @BindView(R.id.include_toolbar_open)
//    View toolbarOpen;
    //展开状态下toolbar的遮罩层
    @BindView(R.id.bg_toolbar_open)
    View bgToolbarOpen;
    //    //收缩状态下toolbar显示的内容
//    @BindView(R.id.include_toolbar_close)
//    View toolbarClose;
    //收缩状态下toolbar的遮罩层
    @BindView(R.id.bg_toolbar_close)
    View bgToolbarClose;

//    RippleLinearLayout m;  //RippleLibrary.jar PermissionACP
    UnilineInput u;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_home);

        ButterKnife.bind(this);
        appBar.addOnOffsetChangedListener(this);

        Acp.setDebug(true);
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
        //当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
        if (offset <= topOffset) {
            ((View) bgToolbarOpen.getParent()).setVisibility(View.VISIBLE);
            ((View) bgToolbarClose.getParent()).setVisibility(View.GONE);
//            toolbarOpen.setVisibility(View.VISIBLE);
//            toolbarClose.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale = (float) offset / topOffset;
            bgToolbarOpen.setAlpha(1 - scale);
        } else {
            //当滑动超过一半，收缩状态下toolbar显示内容，根据收缩位置，改变透明值
            ((View) bgToolbarClose.getParent()).setVisibility(View.VISIBLE);
            ((View) bgToolbarOpen.getParent()).setVisibility(View.GONE);
//            toolbarClose.setVisibility(View.VISIBLE);
//            toolbarOpen.setVisibility(View.GONE);
            float scale = (float) (maxOffset - offset) / (maxOffset - topOffset);
            bgToolbarClose.setAlpha(1 - scale);
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / maxOffset;
        bgContent.setAlpha(1 - scale);
    }
    @OnClick({R.id.ll_scan, R.id.iv_scan})
    void onClickScan() {
        ToastUtil.showPrompt("........onClickScan........");

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        OnPermissionListener onPermissionListener = new OnPermissionListener() {
            @Override
            public void onGranted() {
                System.out.println(".......onGranted.....");
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtil.showPrompt("您没有授权\"读写SD卡\"权限，下载失败");//您没有授权"读写SD卡"权限，下载失败
            }
        };
        PermissionUtil.request(onPermissionListener, permissions);
    }

    @OnClick({R.id.ll_pay, R.id.iv_pay})
    void onClickPay() {
        ToastUtil.showPrompt("........onClickPay........");
    }

    @OnClick({R.id.ll_charge, R.id.iv_charge})
    void onClickCharge() {
        ToastUtil.showPrompt("........onClickCharge........");
    }

    @OnClick({R.id.ll_search, R.id.iv_search})
    void onClickSearch() {
        ToastUtil.showPrompt("........onClickSearch........");
    }

    @OnClick(R.id.ll_card)
    void onClickCard() {
        ToastUtil.showPrompt("........onClickCard........");
    }

    @OnClick({R.id.iv_plus, R.id.iv_close_plus})
    void onClickPlus() {
        ToastUtil.showPrompt("........onClickPlus........");
    }

}