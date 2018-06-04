package com.hunter.helloandroid.module.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/5/28 14:12
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class FlyLineChild extends FrameLayout implements FlyLineLayout.FlyLine {

    private double mStartPercentX, mStartPercentY, mStopPercentX, mStopPercentY;

    public FlyLineChild(Context context) {
        super(context);
    }

    public FlyLineChild(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlyLineChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
        //稍后会重新写这个方法，能达到wrap_content的效果
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof FlyLineLayout.FlyLine) {
                FlyLineLayout.FlyLine flyLine = (FlyLineLayout.FlyLine) child;
                flyLine.setParentWidth(w);
                flyLine.setParentHeight(h);
            }
        }
    }

    public double getStopPercentX() {
        return mStopPercentX;
    }

    public void setStopPercentX(double mStopPercentX) {
        this.mStopPercentX = mStopPercentX;
    }

    public double getStopPercentY() {
        return mStopPercentY;
    }

    public void setStopPercentY(double mStopPercentY) {
        this.mStopPercentY = mStopPercentY;
    }

    private int mParentWidth, mParentHeight;

    @Override
    public int getParentWidth() {
        return mParentWidth;
    }

    @Override
    public void setParentWidth(int parentWidth) {
        mParentWidth = parentWidth;
    }

    @Override
    public int getParentHeight() {
        return mParentHeight;
    }

    @Override
    public void setParentHeight(int parentHeight) {
        mParentHeight = parentHeight;
    }
}
