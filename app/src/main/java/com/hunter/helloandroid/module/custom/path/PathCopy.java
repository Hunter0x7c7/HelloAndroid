package com.hunter.helloandroid.module.custom.path;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.helloandroid.view.CircleView;
import com.hunter.panorama.util.DensityUtil;


/**
 * ================================================================
 * <p>
 * 版    权： HunterHuang(c)2018
 * <p>
 * 作    者： Hunter_1125607007@QQ.COM
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/6/1 15:54
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class PathCopy extends ViewGroup {

    public PathCopy(Context context) {
        super(context);
        init();
    }

    public PathCopy(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathCopy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private Path mPath = new Path();
    private PathMeasure mPathMeasure;
    private int mDipSize, mInterval;
    private float[] mPosition = new float[2];

    private void init() {
        mInterval = DensityUtil.dip2px(getContext(), 6);
        mDipSize = DensityUtil.dip2px(getContext(), 4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        setPath(initPath());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count <= 0) {
            return;
        }
        float length = mPathMeasure.getLength();
        for (int i = 0; i < count; i++) {

            float distance = length * (i * 100f / count) / 100f;
            mPathMeasure.getPosTan(distance, mPosition, null);

            View child = getChildAt(i);
            int left = (int) mPosition[0];
            int top = (int) mPosition[1];
            int right = left + mDipSize;
            int bottom = top + mDipSize;
            child.layout(left, top, right, bottom);
        }

    }


    private void initChildView() {
        float length = mPathMeasure.getLength();
        float count = length / (mDipSize + mInterval);

        removeAllViews();
        CircleView circleView;
        for (int i = 0; i < count; i++) {
            circleView = new CircleView(getContext());
            circleView.setColor(Color.BLUE);
            circleView.setLayoutParams(new LayoutParams(mDipSize, mDipSize));
            addView(circleView);
        }
    }

    public int getInterval() {
        return mInterval;
    }

    public void setInterval(int mInterval) {
        this.mInterval = mInterval;
    }

    public int getDipSize() {
        return mDipSize;
    }

    public void setDipSize(int mDipSize) {
        this.mDipSize = mDipSize;
    }

    public Path getPath() {
        return mPath;
    }

    public void setPath(Path mPath) {
        this.mPath = mPath;
        mPathMeasure = new PathMeasure(mPath, false);
        initChildView();
    }

//    public   Path initPath();

}
