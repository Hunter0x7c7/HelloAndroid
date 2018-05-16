package com.hunter.helloandroid.module.update.interfaces;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
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
