package com.hunter.helloandroid.module.update.util;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.hunter.helloandroid.base.BaseApplication;
import com.hunter.helloandroid.module.update.bean.VersionBean;
import com.hunter.helloandroid.module.update.handler.VersionHandler;
import com.hunter.helloandroid.module.update.interfaces.OnDownloadListener;
import com.hunter.helloandroid.module.update.interfaces.OnPermissionListener;
import com.hunter.helloandroid.util.DirectoryUtil;
import com.hunter.helloandroid.util.PermissionUtil;
import com.hunter.helloandroid.util.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/2 11:07
 * <p>
 * 描    述：更新点击事件监听器
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class UpdateVersionUtil {
    private static final String APP_SIMPLE_NAME = "sfj";
    private static final String NO_AUTHORIZATION_SD_CARD = "您没有授权\"读写SD卡\"权限，下载失败";

    public interface OnShowDialogListener {
        Dialog onShowDialog(View.OnClickListener onClickListener);
    }

    public OnUpdateClickListener getOnUpdateClickListener(String versionName, String url
            , OnDownloadListener<Dialog> mOnDownloadListener, OnShowDialogListener mOnShowDialogListener) {
        return new OnUpdateClickListener(versionName, url, mOnDownloadListener, mOnShowDialogListener);
    }

    public interface UpdateVersion {
        void dismissLoadingLayout();

        void updateLoadProcess(int process);

        void downloadFinish(Dialog dialog);

        void setupFailure();

        void loadFailure(Dialog dialog);

        Dialog onClickUpdate(View.OnClickListener onClickListener);

        void hasNewVersion(String msg, View.OnClickListener onClickListener);

        void latestVersion();
    }

    private UpdateVersion mUpdateVersion;
    private Context mContext;

    public UpdateVersionUtil(UpdateVersion mUpdateVersion, Context mContext) {
        this.mUpdateVersion = mUpdateVersion;
        this.mContext = mContext;
    }

    public UpdateVersion getView() {
        return mUpdateVersion;
    }

    /**
     * 获取新版本返回
     */
    public void getVersionResult(VersionBean result) {
        String url = null, versionName = null, updateText = null;
        int parseCode = -1;
        try {
            String code = result.getCode();
            String ver = result.getVersionCode();
            versionName = result.getVersionName();
            updateText = result.getVersionMessage();
            url = result.getDownloadUrl();

            parseCode = getParseVersionCode(url, code, ver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getView().dismissLoadingLayout();
        }

        if (parseCode > 0 && !TextUtils.isEmpty(url)) {

            OnDownloadListener<Dialog> downloadListener = new OnDownloadListener<Dialog>() {
                @Override
                public void onProcess(int process) {
                    getView().updateLoadProcess(process);//更新下载进度
                }

                @Override
                public void onFinish(Dialog dialog, String filePath) {

                    getView().downloadFinish(dialog);//下载完成

                    File file = new File(filePath);
                    try {
                        FileInputStream fis = new FileInputStream(file);
                        fis.close();
                    } catch (Exception e) {
                        getView().setupFailure();//安装更新包失败
                        return;
                    }
                    //安装Apk
                    InstallUtil.installApk(mContext, file);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            clearGuideFlag();//新手引导标记为未完成
                            statisticSignOff();//统计退出
                        }
                    }).start();

                    BaseApplication.getInstance().exit();//下载完成，程序退出然后安装新版本
                }

                @Override
                public void onFailure(Dialog dialog) {
                    getView().loadFailure(dialog);//下载更新包失败
                }
            };

            UpdateVersionUtil.OnShowDialogListener showDialogListener = new UpdateVersionUtil.OnShowDialogListener() {
                @Override
                public Dialog onShowDialog(View.OnClickListener onClickListener) {
                    return getView().onClickUpdate(onClickListener);
                }
            };

            StringBuilder sb = new StringBuilder();
            sb.append("版本号:");
            sb.append(versionName);
//            updateText = "#增加地块类型#修复若干BUG#";
            if (!TextUtils.isEmpty(updateText) && updateText.startsWith("#") && updateText.endsWith("#")) {
                String[] strings = StringUtil.splitString(updateText, "#");
                if (!ListUtil.isEmpty(strings)) {
                    int length = strings.length;
                    for (int i = 0; i < length; i++) {
                        String string = strings[i];
                        sb.append("\n");
                        sb.append(String.valueOf(i + 1));
                        sb.append("、");
                        sb.append(string);
                    }
                }
            } else {
                sb.append("，是否更新？");
            }

            getView().hasNewVersion(sb.toString(), getOnUpdateClickListener(versionName
                    , url, downloadListener, showDialogListener));
        } else {
            getView().latestVersion();
        }
    }

    private void statisticSignOff() {
//        StatisticUtil.onProfileSignOff(); //统计退出
    }

    private void clearGuideFlag() {
//        PrefUtil.setBoolean("isGuide", false);//新手引导标记为未完成
    }

    /**
     * 解析版本号码
     */
    private int getParseVersionCode(String url, String code, String ver) {
        int parseCode = -1;
        if (ReturnCode.SUCCESS_STR.equals(code) && !TextUtils.isEmpty(ver) && !TextUtils.isEmpty(url)) {
            int versionCode = SystemUtil.ConfigUtil.getVersionCode();
            int parse = (int) Float.parseFloat(ver.replaceAll("[a-zA-Z]", ""));
            if (parse > versionCode && url.startsWith("http")) {//有新版本
                parseCode = parse;
            }
        }
        return parseCode;
    }

    /**
     * 更新点击事件监听器
     */
    public class OnUpdateClickListener implements View.OnClickListener {

        private String versionName;
        private String url;
        private Dialog mLoadDialog;
        private OnDownloadListener<Dialog> mOnDownloadListener;
        private OnShowDialogListener mOnShowDialogListener;
        private Callback.Cancelable mCancelableLoadFile = null;

        private OnUpdateClickListener(String versionName, String url
                , OnDownloadListener<Dialog> mOnDownloadListener, OnShowDialogListener mOnShowDialogListener) {
            this.versionName = versionName;
            this.url = url;
            this.mOnDownloadListener = mOnDownloadListener;
            this.mOnShowDialogListener = mOnShowDialogListener;
        }

        @Override
        public void onClick(View v) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            OnPermissionListener onPermissionListener = new OnPermissionListener() {
                @Override
                public void onGranted() {
                    if (mOnShowDialogListener != null) {
                        mLoadDialog = mOnShowDialogListener.onShowDialog(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (mCancelableLoadFile != null) {
                                    mCancelableLoadFile.cancel();
                                    mCancelableLoadFile = null;
                                    mLoadDialog = null;
                                }
                            }
                        });
                    }

                    String fileDir;
                    String apkName = APP_SIMPLE_NAME + "_" + versionName + "_Android.apk";
                    if (FileUtil.existsSdcard()) {
                        fileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                    } else {
                        fileDir = DirectoryUtil.getPrivateDir("new_apk");
                    }
                    String mLoadFilePath = fileDir + "/" + apkName;
                    File dir = new File(fileDir);
                    if (!dir.exists()) {
                        boolean mkdirs = dir.mkdirs();
                    }
//                    mCancelableLoadFile = DownloadUtil.getInstance(mDownloadHandler).download(url, mLoadFilePath);
                    VersionHandler handler = new VersionHandler(mOnDownloadListener, mLoadDialog, mLoadFilePath);
                    mCancelableLoadFile = DownloadUtil.getInstance(handler).download(url, mLoadFilePath);
                }

                @Override
                public void onDenied(List<String> permissions) {
                    ToastUtil.showPrompt(NO_AUTHORIZATION_SD_CARD);//您没有授权"读写SD卡"权限，下载失败
                }
            };
            PermissionUtil.request(onPermissionListener, permissions);
        }

    }

    private class ReturnCode {
        static final String SUCCESS_STR = "200";
    }

}
