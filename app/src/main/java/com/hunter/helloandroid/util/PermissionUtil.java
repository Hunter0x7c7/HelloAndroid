package com.hunter.helloandroid.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.hunter.dialog.SimpleDialog;
import com.hunter.helloandroid.base.BaseApplication;
import com.hunter.helloandroid.module.update.interfaces.OnPermissionListener;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
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
    private static String deniedMessage = "您拒绝过权限申请，将不能正常使用，您可以去设置页面重新授权";
    private static String deniedCloseBtn = "退出";
    private static String deniedSettingBtn = "去设置权限";
    private static String rationalMessage = "接下来需要您授权，否则将不能正常使用";
    private static String rationalBtn = "好，我知道了";

    /**
     * 获取权限名字
     */
    public static String getPermissionName(@NonNull String permission) {
        String permissionName = null;
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
            }
        }
        return permissionName;
    }

    /**
     * 请求权限返回：对话框提示去打开应用程序页面设置权限
     */
    public static void doOpenPermissionDialog(final Context context, CharSequence sb) {
        if (TextUtils.isEmpty(sb)) {
            return;
        }

        String showContent = String.format("您没有授权“%s”权限。请到“设置-应用信息-权限管理”中打开", sb);
        SystemUtil.println(showContent);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                context.startActivity(intent);
            }
        };
        new SimpleDialog(context)
                .setTitleName(showContent)
                .setPositiveButton("去打开", onClickListener)
                .setNegativeButton()
                .setAnimatorSet()
                .setCanceledOnTouchOutside().show();
    }

    public static void request(final OnPermissionListener onPermissionListener, String... permissions) {
        request(null, null, null, null, null, onPermissionListener, permissions);
    }

    public static void request(String deniedMessage, String deniedCloseBtn, String deniedSettingBtn
            , final OnPermissionListener onPermissionListener, String... permissions) {
        request(deniedMessage, deniedCloseBtn, deniedSettingBtn, null, null, onPermissionListener, permissions);
    }

    public static void request(String deniedMessage, String deniedCloseBtn, String deniedSettingBtn
            , String rationalMessage, String rationalBtn
            , final OnPermissionListener onPermissionListener, String... permissions) {
        Context context = BaseApplication.getContext();
        request(context, deniedMessage, deniedCloseBtn, deniedSettingBtn, rationalMessage, rationalBtn, onPermissionListener, permissions);
    }

    public static void request(Context context, String deniedMessage, String deniedCloseBtn, String deniedSettingBtn
            , String rationalMessage, String rationalBtn
            , final OnPermissionListener onPermissionListener, String... permissions) {

        AcpListener acpListener = new AcpListener() {
            @Override
            public void onGranted() {
                if (onPermissionListener != null)
                    onPermissionListener.onGranted();
            }

            @Override
            public void onDenied(List<String> permissions) {
                if (onPermissionListener != null)
                    onPermissionListener.onDenied(permissions);
            }
        };

        AcpOptions.Builder builder = new AcpOptions.Builder().setPermissions(permissions);
        if (TextUtils.isEmpty(deniedMessage)) {
            deniedMessage = PermissionUtil.deniedMessage;
        }
        if (TextUtils.isEmpty(deniedCloseBtn)) {
            deniedCloseBtn = PermissionUtil.deniedCloseBtn;
        }
        if (TextUtils.isEmpty(deniedSettingBtn)) {
            deniedSettingBtn = PermissionUtil.deniedSettingBtn;
        }
        if (TextUtils.isEmpty(rationalMessage)) {
            rationalMessage = PermissionUtil.rationalMessage;
        }
        if (TextUtils.isEmpty(rationalBtn)) {
            rationalBtn = PermissionUtil.rationalBtn;
        }
        builder.setDeniedSettingBtn(deniedSettingBtn);
        builder.setDeniedMessage(deniedMessage);
        builder.setDeniedCloseBtn(deniedCloseBtn);
        builder.setRationalMessage(rationalMessage);
        builder.setRationalBtn(rationalBtn);
        Acp.getInstance(context).request(builder.build(), acpListener);
    }

}
