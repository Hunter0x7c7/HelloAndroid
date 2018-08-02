package com.hunter.acp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.List;

public class AcpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不接受触摸屏事件
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        AcpManager acpManager = Acp.getInstance(this).getAcpManager();
        if (savedInstanceState == null)
            acpManager.checkRequestPermissionRationale(this);

        //如果权限列表为空时，关闭该Activity
        List<String> permissionList = acpManager.getPermissionList();
        if (!(permissionList != null && permissionList.size() > 0)) {
            finish();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Acp.getInstance(this).getAcpManager().checkRequestPermissionRationale(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Acp.getInstance(this).getAcpManager().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Acp.getInstance(this).getAcpManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Acp.getInstance(this).getAcpManager().onStop();
    }
}
