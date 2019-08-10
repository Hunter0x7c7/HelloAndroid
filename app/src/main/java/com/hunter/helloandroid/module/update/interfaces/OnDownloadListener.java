package com.hunter.helloandroid.module.update.interfaces;

/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2017
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/3/2 9:59
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public interface OnDownloadListener<T> {
    void onProcess(int process);

    void onFinish(T object, String filePath);

    void onFailure(T object);
}
