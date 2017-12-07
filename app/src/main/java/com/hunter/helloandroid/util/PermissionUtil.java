package com.hunter.helloandroid.util;

import android.Manifest;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/8/4 11:26
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PermissionUtil {

    /**
     * 获取权限名字
     */
    public static String getPermissionName(@NonNull String permission) {
        String permissionName = "";
        if (!TextUtils.isEmpty(permission)) {
            switch (permission) {
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    permissionName = "定位";
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    permissionName = "读写手机存储";
                    break;
                case Manifest.permission.CAMERA:
                    permissionName = "相机";
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    permissionName = "录音";
                    break;
                case Manifest.permission.READ_PHONE_STATE:
                    permissionName = "获取手机信息";
                    break;
                case Manifest.permission.READ_CONTACTS:
                    permissionName = "读取联系人";
                    break;
            }
        }
        return permissionName;
    }
}
