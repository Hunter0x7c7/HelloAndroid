package com.hunter.helloandroid.module.rocket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.helloandroid.R;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/12/1 13:44
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class RocketActivity extends AppCompatActivity implements SpeechPanelView.OnToggleChangeListener {

//        private View itemRocket;
    private SpeechPanelView itemRocket;
    private SpeechPanelView hvHaul;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);

//        itemRocket = View.inflate(this, R.layout.item_rocket, null);
        itemRocket = new SpeechPanelView(this);
        itemRocket.setOnToggleChangeListener(this);

        hvHaul = (SpeechPanelView) findViewById(R.id.hv_haul);
        hvHaul.setOnToggleChangeListener(this);
    }

    public void onClickButton(View view) {
        WinManagerUtil.getInstance().start(this, itemRocket);
    }

    public void onClickButton2(View view) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        WinManagerUtil.getInstance().stop(itemRocket);
        itemRocket = null;
    }

    @Override
    public void onToggleChanged(View view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.hv_haul:
                hvHaul.updateStatus(isChecked ? "开关打开" : "关闭开关");
                break;
            default:
                itemRocket.updateStatus(isChecked ? "开关打开" : "关闭开关");
                break;
        }
    }


}
