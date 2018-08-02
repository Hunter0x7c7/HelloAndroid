package com.hunter.acp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import com.hunter.acp.util.LogUtil;


class AcpService {
    private static final String TAG = "AcpService";

    /**
     * 检查权限授权状态
     */
    int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    LogUtil.i(TAG, "targetSdkVersion >= Build.VERSION_CODES.M");
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 向系统请求权限
     */
    void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        if (permissions != null && permissions.length > 0) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * 检查权限是否存在拒绝不再提示
     */
    boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        boolean shouldShowRational = false;
        if (!TextUtils.isEmpty(permission)) {
            shouldShowRational = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            LogUtil.i(TAG, "shouldShowRational = " + shouldShowRational);
        }
        return shouldShowRational;
    }
}
