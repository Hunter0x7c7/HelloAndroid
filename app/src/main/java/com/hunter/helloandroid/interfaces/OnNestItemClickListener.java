package com.hunter.helloandroid.interfaces;

import android.view.View;

/**
 * ================================================================
 * <p>
 * 版    权：  (c)2017
 * <p>
 * 作    者： Huang zihang
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/6/2 15:58
 * <p>
 * 描    述：嵌套点击事件监听接口
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public interface OnNestItemClickListener {
    /**
     * 嵌套点击事件监听
     *
     * @param view     点击的View
     * @param row      第一层Position
     * @param position 嵌套的Position
     */
    void onNestItemClick(View view, int row, int position);
}
