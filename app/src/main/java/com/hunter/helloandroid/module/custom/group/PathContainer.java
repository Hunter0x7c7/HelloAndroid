package com.hunter.helloandroid.module.custom.group;

import android.graphics.Path;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/3 11:12
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public interface PathContainer {

    Path getPath();

    float getPositionX();

    float getPositionY();

    float getTangentX();

    float getTangentY();

    void getPosTan();
}
