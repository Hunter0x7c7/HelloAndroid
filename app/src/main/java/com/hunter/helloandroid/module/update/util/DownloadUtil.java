package com.hunter.helloandroid.module.update.util;

import android.os.Handler;
import android.os.Message;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/11/15 9:53
 * <p>
 * 描    述：下载文件工具类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class DownloadUtil {

    private static DownloadUtil mInstance;
    private Handler mHandler;

    private DownloadUtil(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public static DownloadUtil getInstance(Handler mHandler) {
        if (mInstance == null) {
            synchronized (DownloadUtil.class) {
                if (mInstance == null) {
                    mInstance = new DownloadUtil(mHandler);
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送Handler消息
     */
    private void sendMsg(int flag, int percent) {
        Message msg = new Message();
        msg.what = flag;
        msg.arg1 = percent;
        mHandler.sendMessage(msg);
    }

    /**
     * 用xUtils下载文件
     */
    public Callback.Cancelable download(String url, String targetFilePath) {

        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(targetFilePath);
        ProgressCallback<File> commonCallback = new ProgressCallback<File>() {

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                int percent = (int) (current * 100 / total);
                sendMsg(1, percent);
            }

            @Override
            public void onSuccess(File result) {
                sendMsg(2, 0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                sendMsg(-1, 0);
            }
        };
        return x.http().get(params, commonCallback);
    }

    /**
     * xUtils3下载带进度的Callback
     */
    public class ProgressCallback<ResultType> implements Callback.ProgressCallback<ResultType> {

        @Override
        public void onWaiting() {
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onLoading(long l, long l1, boolean b) {
        }

        @Override
        public void onSuccess(ResultType o) {
        }

        @Override
        public void onError(Throwable throwable, boolean b) {
        }

        @Override
        public void onCancelled(CancelledException e) {
        }

        @Override
        public void onFinished() {
        }
    }
}
