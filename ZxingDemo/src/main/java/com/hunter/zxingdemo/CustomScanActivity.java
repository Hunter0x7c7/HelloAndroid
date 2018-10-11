package com.hunter.zxingdemo;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hunter.zxingdemo.util.ToastUtil;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hunter on 17/12/15.
 */

public class CustomScanActivity extends AppCompatActivity
        implements DecoratedBarcodeView.TorchListener {

    // 添加一个按钮用来控制闪光灯
    @BindView(R.id.btn_switch)
    Button swichLight;
    @BindView(R.id.dbv_custom)
    DecoratedBarcodeView mDBV;

    private CaptureManager captureManager;
    private boolean isLightOn = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
        ToastUtil.showPrompt("torch on", Toast.LENGTH_LONG, -1);
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        ToastUtil.showPrompt("torch off", Toast.LENGTH_LONG, -1);
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    // 点击切换闪光灯
    @OnClick(R.id.btn_switch)
    public void swichLight() {
        if (isLightOn) {
            mDBV.setTorchOff();
        } else {
            mDBV.setTorchOn();
        }
    }


}
