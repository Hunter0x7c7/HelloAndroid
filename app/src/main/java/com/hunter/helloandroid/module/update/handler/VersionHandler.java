package com.hunter.helloandroid.module.update.handler;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;

import com.hunter.helloandroid.module.update.interfaces.OnDownloadListener;


/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/2 9:58
 * <p>
 * 描    述：处理下载线程与UI间通讯
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class VersionHandler extends Handler {
    private OnDownloadListener<Dialog> mOnDownloadListener;
    private Dialog mDialog;
    private String mFilePath;

    public VersionHandler(OnDownloadListener<Dialog> onDownloadListener, Dialog dialog, String filePath) {
        mOnDownloadListener = onDownloadListener;
        mDialog = dialog;
        mFilePath = filePath;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (!Thread.currentThread().isInterrupted()) {
            switch (msg.what) {
                case 1:
                    if (mOnDownloadListener != null) {
                        mOnDownloadListener.onProcess(msg.arg1);
                    }
                    break;
                case 2:
                    if (mOnDownloadListener != null) {
                        mOnDownloadListener.onFinish(mDialog, mFilePath);
                    }
                    break;
                case -1:
                    if (mOnDownloadListener != null) {
                        mOnDownloadListener.onFailure(mDialog);
                    }
                    break;
            }
        }
    }
}


